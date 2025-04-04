package mathilda.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface JsonRule {

    /**
     * Optional list of domains where the rule is applied to.
     * "https://" and "www." should be left out.
     *
     * If both `domains` and `domainRegex` are specified, they will be applied in an OR-fashion.
     * The rule is applied to all domains when both `domains` and `domainRegex` are empty.
     */
    val domains: List<String>

    /**
     * Optional regular expression of domain(s) where the rule is applied to.
     * "https://" and "www." should be left out.
     *
     * If both `domains` and `domainRegex` are specified, they will be applied in an OR-fashion.
     * The rule is applied to all domains when both `domains` and `domainRegex` are empty.
     */
    val domainRegex: String?

    /**
     * Optional description of rule.
     */
    val description: String?

    @Serializable
    @SerialName("remove_params")
    data class JsonRemoveParamsRule(
        override val domains: List<String> = emptyList(),
        @SerialName("domain_regex")
        override val domainRegex: String? = null,
        override val description: String? = null,
        val parameters: List<String>,
    ) : JsonRule

    @Serializable
    @SerialName("remove_regex")
    data class JsonRemoveRegexRule(
        override val domains: List<String> = emptyList(),
        @SerialName("domain_regex")
        override val domainRegex: String? = null,
        override val description: String? = null,
        val regex: String,
    ) : JsonRule

    @Serializable
    @SerialName("transform")
    data class JsonTransformRule(
        override val domains: List<String> = emptyList(),
        override val domainRegex: String? = null,
        override val description: String? = null,
        val input: String,
        val output: String? = null,
        val decode: Boolean = false,
    ) : JsonRule
}
