package mathilda.json

import kotlinx.serialization.Serializable

@Serializable
internal data class JsonRoot(
    val version: Int,
    val rules: Map<String, JsonRule>,
)
