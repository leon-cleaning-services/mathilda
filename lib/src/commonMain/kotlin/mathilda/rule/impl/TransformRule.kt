package mathilda.rule.impl

import com.eygraber.uri.UriCodec
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import mathilda.rule.BaseRule

internal data class TransformRule(
    override val id: String,
    override val domains: ImmutableList<String> = persistentListOf(),
    override val domainRegex: String? = null,
    private val input: String,
    private val output: String? = null,
    private val decode: Boolean = false,
) : BaseRule() {

    override suspend fun execute(url: String): String {
        val regex = Regex(input)
        val template = output ?: "\\1"

        val groups = regex.find(url)?.groupValues
        if (groups == null || groups.size == 1) return url

        return groups
            .drop(1)
            .foldIndexed(template) { index, curr, group ->
                val value = if (decode) UriCodec.decode(group) else group
                curr.replace("\\${index + 1}", value)
            }
    }
}
