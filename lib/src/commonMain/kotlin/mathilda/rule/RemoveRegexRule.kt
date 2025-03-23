package mathilda.rule

internal class RemoveRegexRule(
    override val id: String,
    private val regex: String,
) : Rule {

    override fun invoke(input: String): String {
        TODO()
    }
}
