package mathilda.rule

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal class ActionRule(
    override val id: Id,
    override val domains: ImmutableList<String> = persistentListOf(),
    override val domainRegex: String? = null,
    override val tests: ImmutableList<Rule.Test> = persistentListOf(),
    private val action: suspend (String) -> String,
) : BaseRule() {

    override suspend fun execute(url: String): String = action(url)
}

/**
 * Used for testing
 */
internal fun noOpRule(
    id: Id,
    domains: ImmutableList<String> = persistentListOf(),
    domainRegex: String? = null,
) = ActionRule(id, domains, domainRegex) { it }
