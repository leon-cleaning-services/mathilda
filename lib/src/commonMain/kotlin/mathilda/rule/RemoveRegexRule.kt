package mathilda.rule

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class RemoveRegexRule(
    override val id: String,
    override val domains: ImmutableList<String> = persistentListOf(),
    override val domainRegex: String? = null,
    private val regex: String,
) : Rule {

    override fun invoke(input: String): String {
        TODO()
    }
}
