package mathilda.rule

import kotlinx.collections.immutable.ImmutableList

public typealias Id = String

public sealed interface Rule {

    public data class Test(
        val input: String,
        val expected: String,
        val skip: Boolean = false
    )

    public sealed interface Result {

        public val value: String

        /**
         * The rule was applied and modified the input value
         */
        public data class Success(
            override val value: String,
        ) : Result

        /**
         * The rule was applied but didn't modify the input value
         */
        public data class Unmodified(
            override val value: String,
        ) : Result

        /**
         * Based on domain parameters the rule was not applied
         */
        public data class NoMatch(
            override val value: String,
        ) : Result
    }

    /**
     * Unique ID
     */
    public val id: Id

    /**
     * Optional list of domains where the rule is applied to.
     * "https://" and "www." should be left out.
     *
     * If both [domains] and [domainRegex] are specified, they will be applied in an OR-fashion.
     * The rule is applied to all domains when both [domains] and [domainRegex] are empty.
     */
    public val domains: ImmutableList<String>

    /**
     * Optional regular expression of domain(s) where the rule is applied to.
     * "https://" and "www." should be left out.
     *
     * If both [domains] and [domainRegex] are specified, they will be applied in an OR-fashion.
     * The rule is applied to all domains when both [domains] and [domainRegex] are empty.
     */
    public val domainRegex: String?

    /**
     * Optional list of tests.
     *
     * When defined, all tests must pass or else Mathilda will fail at importing this rule.
     */
    public val tests: ImmutableList<Test>

    /**
     * Invokes rule on input string
     */
    public suspend operator fun invoke(url: String): Result
}
