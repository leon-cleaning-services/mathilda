package mathilda

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import mathilda.json.Deserializer
import mathilda.json.JsonRoot
import mathilda.rule.Rule
import mathilda.rule.RuleFactory

public fun builder(json: String): Mathilda.Builder {
    val root = Deserializer.decodeFromString(json)
    return Mathilda.Builder(root)
}

public class Mathilda internal constructor(
    public val rules: ImmutableSet<Rule>,
) {

    public class Builder internal constructor(private val rules: MutableSet<Rule>) {

        internal constructor(root: JsonRoot) : this(
            root.rules
                .map(RuleFactory::fromJsonRule)
                .toMutableSet()
        )

        /**
         * Only keeps rule with specified `id`
         */
        public fun only(id: String): Builder {
            rules.removeAll { it.id != id }
            if (rules.isEmpty()) throw IllegalArgumentException("Rule with id \"$id\" not found")
            return this
        }

        /**
         * Removes rule with specified `id`
         */
        public fun without(id: String): Builder {
            if (!rules.removeAll { it.id == id }) throw IllegalArgumentException("Rule with id \"$id\" not found")
            return this
        }

        public fun build(): Mathilda {
            if (rules.isEmpty()) throw IllegalStateException("No rules available")
            return Mathilda(rules.toImmutableSet())
        }
    }

    public fun newBuilder(): Builder = Builder(rules.toMutableSet())

    public fun clean(input: String): String {
        // TODO
        return input
    }
}
