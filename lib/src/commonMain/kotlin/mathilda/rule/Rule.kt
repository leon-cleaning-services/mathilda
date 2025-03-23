package mathilda.rule

public sealed interface Rule {
    public val id: String

    public operator fun invoke(input: String): String
}
