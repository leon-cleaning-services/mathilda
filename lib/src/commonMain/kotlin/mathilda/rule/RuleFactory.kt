package mathilda.rule

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import mathilda.json.JsonRule
import mathilda.rule.impl.KeepParamsRule
import mathilda.rule.impl.RemoveParamsRule
import mathilda.rule.impl.RemoveRegexRule
import mathilda.rule.impl.TransformRule

internal object RuleFactory {

    fun fromJsonRule(entry: Map.Entry<String, JsonRule>) = fromJsonRule(entry.key, entry.value)

    private fun fromJsonRule(id: String, rule: JsonRule): Pair<Rule, Boolean> = (when (rule) {
        is JsonRule.JsonRemoveParamsRule -> RemoveParamsRule(
            id = id,
            domains = rule.domains.toImmutableList(),
            domainRegex = rule.domainRegex?.toNullIfBlank(),
            tests = rule.tests.map(),
            parameters = rule.parameters.toImmutableList(),
        )

        is JsonRule.JsonKeepParamsRule -> KeepParamsRule(
            id = id,
            domains = rule.domains.toImmutableList(),
            domainRegex = rule.domainRegex?.toNullIfBlank(),
            tests = rule.tests.map(),
            parameters = rule.parameters.toImmutableList(),
        )

        is JsonRule.JsonRemoveRegexRule -> RemoveRegexRule(
            id = id,
            domains = rule.domains.toImmutableList(),
            domainRegex = rule.domainRegex?.toNullIfBlank(),
            tests = rule.tests.map(),
            regex = rule.regex,
        )

        is JsonRule.JsonTransformRule -> TransformRule(
            id = id,
            domains = rule.domains.toImmutableList(),
            domainRegex = rule.domainRegex?.toNullIfBlank(),
            tests = rule.tests.map(),
            input = rule.input,
            output = rule.output?.toNullIfBlank(),
        )
    }) to rule.enabled

    private fun List<JsonRule.Test>.map(): ImmutableList<Rule.Test> =
        map { Rule.Test(input = it.input, expected = it.expected) }.toImmutableList()
}

private fun String?.toNullIfBlank(): String? = if (isNullOrBlank()) null else this
