package mathilda

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.collections.immutable.toImmutableSet
import mathilda.Mathilda.Builder
import mathilda.json.Deserializer
import mathilda.json.JsonRoot
import mathilda.rule.Id
import mathilda.rule.Rule
import mathilda.rule.RuleFactory

public fun builder(rules: List<Rule>): Builder = Builder(
    rules = rules.associate { it.id to RuleState(it) }
)

public fun builder(rules: ImmutableList<Rule>): Builder = Builder(
    rules = rules.associate { it.id to RuleState(it) }
)

public fun builder(json: String): Builder {
    val root = Deserializer.decodeFromString(json)
    return Builder(root)
}

internal data class RuleState(
    val rule: Rule,
    val enabled: Boolean = true,
)

public class Mathilda private constructor(
    private val _rules: ImmutableMap<Id, RuleState>,
) {

    public val rules: ImmutableSet<Rule>
        get() = _rules
            .values
            .map { it.rule }
            .toImmutableSet()

    public val enabledRules: ImmutableSet<Rule>
        get() = _rules
            .values
            .filter { it.enabled }
            .map { it.rule }
            .toImmutableSet()

    /**
     * @see [builder]
     */
    public class Builder internal constructor(private var rules: Map<Id, RuleState>) {

        internal constructor(root: JsonRoot) : this(
            root.rules
                .map(RuleFactory::fromJsonRule)
                .associate { (rule, enabled) -> rule.id to RuleState(rule, enabled) }
                .toMutableMap()
        )

        /**
         * Enables rule with specified `id`, disables all other rules
         *
         * @throws NoSuchElementException
         */
        public fun only(id: Id): Builder {
            if (!rules.containsKey(id)) throw NoSuchElementException()
            rules = rules.mapValues { it.value.copy(enabled = it.key == id) }
            return this
        }

        /**
         * Disables rule with specified `id`
         *
         * @throws NoSuchElementException
         */
        public fun without(id: Id): Builder {
            val state = rules.getValue(id)
            rules = rules + (state.rule.id to state.copy(enabled = false))
            return this
        }

        /**
         * @throws IllegalStateException
         */
        public fun build(): Mathilda {
            check(rules.isNotEmpty()) { "No rules available" }
            return Mathilda(rules.toImmutableMap())
        }
    }

    /**
     * Creates a new [Builder] based on this instance of [Mathilda]
     */
    public fun newBuilder(): Builder = Builder(_rules)

    /**
     * Returns `true` when rule with [id] is enabled
     *
     * @throws NoSuchElementException
     */
    public fun isEnabled(id: String): Boolean = _rules.getValue(id).enabled

    // TODO
    public suspend fun clean(input: String): Result<String> {
        var state = input.trim()

        enabledRules.forEach { rule ->
            try {
                state = rule(state).value
            } catch (e: Throwable) {
                return Result.failure(e)
            }
        }

        return Result.success(state)
    }
}
