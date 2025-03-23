package mathilda.rule

import kotlinx.collections.immutable.toImmutableList
import mathilda.json.JsonRule

internal object RuleFactory {

    fun fromJsonRule(entry: Map.Entry<String, JsonRule>) = fromJsonRule(entry.key, entry.value)

    private fun fromJsonRule(id: String, rule: JsonRule): Rule = when (rule) {
        is JsonRule.JsonRemoveRule -> RemoveRule(
            id = id,
            parameters = rule.parameters.toImmutableList(),
        )

        is JsonRule.JsonRemoveRegexRule -> RemoveRegexRule(
            id = id,
            regex = rule.regex,
        )
    }
}
