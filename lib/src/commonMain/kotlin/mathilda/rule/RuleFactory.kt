package mathilda.rule

import kotlinx.collections.immutable.toImmutableList
import mathilda.json.JsonRule
import mathilda.rule.impl.RemoveParamsRule
import mathilda.rule.impl.RemoveRegexRule
import mathilda.rule.impl.TransformRule

internal object RuleFactory {

    fun fromJsonRule(entry: Map.Entry<String, JsonRule>) = fromJsonRule(entry.key, entry.value)

    private fun fromJsonRule(id: String, rule: JsonRule): Rule = when (rule) {
        is JsonRule.JsonRemoveParamsRule -> RemoveParamsRule(
            id = id,
            domains = rule.domains.toImmutableList(),
            domainRegex = rule.domainRegex?.toNullIfBlank(),
            parameters = rule.parameters.toImmutableList(),
        )

        is JsonRule.JsonRemoveRegexRule -> RemoveRegexRule(
            id = id,
            domains = rule.domains.toImmutableList(),
            domainRegex = rule.domainRegex?.toNullIfBlank(),
            regex = rule.regex,
        )

        is JsonRule.JsonTransformRule -> TransformRule(
            id = id,
            domains = rule.domains.toImmutableList(),
            domainRegex = rule.domainRegex?.toNullIfBlank(),
            input = rule.input,
            output = rule.output?.toNullIfBlank(),
            decode = rule.decode,
        )
    }
}

private fun String?.toNullIfBlank(): String? =
    if (isNullOrBlank()) null else this
