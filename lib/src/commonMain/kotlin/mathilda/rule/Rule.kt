package mathilda.rule

import kotlinx.collections.immutable.ImmutableList

public typealias Id = String

public sealed interface Rule {

    /**
     * Unique ID
     */
    public val id: Id

    /**
     * Optional list of domains where the rule is applied to.
     * "https://" and "www." should be left out.
     *
     * If both `domains` and `domainRegex` are specified, they will be applied in an OR-fashion.
     * The rule is applied to all domains when both `domains` and `domainRegex` are empty.
     */
    public val domains: ImmutableList<String>

    /**
     * Optional regular expression of domain(s) where the rule is applied to.
     * "https://" and "www." should be left out.
     *
     * If both `domains` and `domainRegex` are specified, they will be applied in an OR-fashion.
     * The rule is applied to all domains when both `domains` and `domainRegex` are empty.
     */
    public val domainRegex: String?

    public suspend operator fun invoke(input: String): String
}
