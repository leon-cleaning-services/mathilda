package mathilda.rule

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class RemoveRule(
    override val id: String,
    override val domains: ImmutableList<String> = persistentListOf(),
    override val domainRegex: String? = null,
    private val parameters: ImmutableList<String>,
) : Rule {

    override fun invoke(input: String): String {
        TODO()
    }
}
