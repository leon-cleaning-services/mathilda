package mathilda

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import mathilda.json.Deserializer
import mathilda.json.JsonRoot
import java.io.InputStream

@OptIn(ExperimentalSerializationApi::class)
public fun builder(stream: InputStream): Mathilda.Builder {
    val root = Deserializer.json.decodeFromStream<JsonRoot>(stream)
    return Mathilda.Builder(root)
}
