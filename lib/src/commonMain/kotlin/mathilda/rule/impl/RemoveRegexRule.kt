package mathilda.rule.impl

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import mathilda.rule.BaseRule

internal data class RemoveRegexRule(
    override val id: String,
    override val domains: ImmutableList<String> = persistentListOf(),
    override val domainRegex: String? = null,
    private val regex: String,
) : BaseRule() {

    override suspend fun execute(input: String) = input.replace(Regex(regex), "")
}
