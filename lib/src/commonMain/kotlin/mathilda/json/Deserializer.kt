package mathilda.json

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

internal object Deserializer {

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        allowComments = true
        allowTrailingComma = true
    }

    fun decodeFromString(input: String) = json.decodeFromString<JsonRoot>(input)
}
