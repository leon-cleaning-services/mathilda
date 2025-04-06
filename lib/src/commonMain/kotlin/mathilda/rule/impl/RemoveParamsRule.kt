package mathilda.rule.impl

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import mathilda.rule.BaseRule
import mathilda.rule.Rule

internal data class RemoveParamsRule(
    override val id: String,
    override val domains: ImmutableList<String> = persistentListOf(),
    override val domainRegex: String? = null,
    override val tests: ImmutableList<Rule.Test> = persistentListOf(),
    private val parameters: ImmutableList<String>,
) : BaseRule() {

    override suspend fun execute(url: String) =
        parameters.fold(url) { curr, parameter ->
            val p = parameter
                .replace("?", ".?")
                .replace("*", ".+?")

            val regex = Regex("[?&]$p=([^&#]*)")
            curr.replace(regex, "")
        }
}
