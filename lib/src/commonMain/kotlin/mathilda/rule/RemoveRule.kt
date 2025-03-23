package mathilda.rule

import kotlinx.collections.immutable.ImmutableList

internal class RemoveRule(
    override val id: String,
    private val parameters: ImmutableList<String>,
) : Rule {

    override fun invoke(input: String): String {
        TODO()
    }
}
