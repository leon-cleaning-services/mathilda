package mathilda.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface JsonRule {

    @Serializable
    data class Test(
        val input: String,
        val expected: String,
        val skip: Boolean = false
    )

    /**
     * Optional list of domains where the rule is applied to.
     * "https://" and "www." should be left out.
     *
     * If both [domains] and [domainRegex] are specified, they will be applied in an OR-fashion.
     * The rule is applied to all domains when both [domains] and [domainRegex] are empty.
     */
    val domains: List<String>

    /**
     * Optional regular expression of domain(s) where the rule is applied to.
     * "https://" and "www." should be left out.
     *
     * If both [domains] and [domainRegex] are specified, they will be applied in an OR-fashion.
     * The rule is applied to all domains when both [domains] and [domainRegex] are empty.
     */
    val domainRegex: String?

    /**
     * Optional description of rule.
     */
    val description: String?

    /**
     * By default all rules are enabled.
     *
     * This property allows creating a disabled rule which needs to be enabled explicitly.
     */
    val enabled: Boolean

    /**
     * Optional list of tests.
     *
     * When defined, all tests must pass or else Mathilda will fail at importing this rule.
     */
    val tests: List<Test>

    @Serializable
    @SerialName("remove_params")
    data class JsonRemoveParamsRule(
        override val domains: List<String> = emptyList(),
        @SerialName("domain_regex")
        override val domainRegex: String? = null,
        override val description: String? = null,
        override val enabled: Boolean = true,
        override val tests: List<Test> = emptyList(),
        val parameters: List<String>,
    ) : JsonRule

    @Serializable
    @SerialName("keep_params")
    data class JsonKeepParamsRule(
        override val domains: List<String> = emptyList(),
        @SerialName("domain_regex")
        override val domainRegex: String? = null,
        override val description: String? = null,
        override val enabled: Boolean = true,
        override val tests: List<Test> = emptyList(),
        val parameters: List<String>,
    ) : JsonRule

    @Serializable
    @SerialName("remove_regex")
    data class JsonRemoveRegexRule(
        override val domains: List<String> = emptyList(),
        @SerialName("domain_regex")
        override val domainRegex: String? = null,
        override val description: String? = null,
        override val enabled: Boolean = true,
        override val tests: List<Test> = emptyList(),
        val regex: String,
    ) : JsonRule

    @Serializable
    @SerialName("transform")
    data class JsonTransformRule(
        override val domains: List<String> = emptyList(),
        @SerialName("domain_regex")
        override val domainRegex: String? = null,
        override val description: String? = null,
        override val enabled: Boolean = true,
        override val tests: List<Test> = emptyList(),
        val input: String,
        val output: String? = null,
    ) : JsonRule
}
