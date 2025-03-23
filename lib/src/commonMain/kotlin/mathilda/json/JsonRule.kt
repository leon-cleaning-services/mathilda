package mathilda.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface JsonRule {

    val description: String?

    @Serializable
    @SerialName("remove")
    data class JsonRemoveRule(
        override val description: String? = null,
        val parameters: List<String>,
    ) : JsonRule

    @Serializable
    @SerialName("remove_regex")
    data class JsonRemoveRegexRule(
        override val description: String? = null,
        val regex: String,
    ) : JsonRule
}
