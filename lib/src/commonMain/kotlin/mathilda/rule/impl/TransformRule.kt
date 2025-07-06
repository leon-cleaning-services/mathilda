package mathilda.rule.impl

import com.eygraber.uri.UriCodec
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.json.Json
import mathilda.rule.BaseRule
import mathilda.rule.Rule
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

internal data class TransformRule(
    override val id: String,
    override val domains: ImmutableList<String> = persistentListOf(),
    override val domainRegex: String? = null,
    override val tests: ImmutableList<Rule.Test> = persistentListOf(),
    private val input: String,
    private val output: String? = null,
) : BaseRule() {

    private data class Operation(
        val name: String,
        val argument: String?,
    )

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun execute(url: String): String {
        val regex = Regex(input)
        val template = output ?: "{{1}}"

        val groups = regex.find(url)?.groupValues
        if (groups == null || groups.size == 1) return url

        val slots = REGEX_SLOTS.findAll(template)
            .map { result ->
                val ops = REGEX_SLOTS_OPS.findAll(result.groupValues[2])
                    .map { result ->
                        Operation(
                            name = result.groupValues[1],
                            argument = result.groupValues[2].ifEmpty { null }
                        )
                    }
                    .toList()

                result.groupValues[1].toInt() to ops
            }
            .toMap()

        return groups
            .drop(1)
            .foldIndexed(template) { index, curr, group ->
                val index = index + 1
                val value = slots[index]?.fold(group) { curr, op ->
                    when (op.name) {
                        OP_URLDECODE -> UriCodec.decode(curr)
                        OP_BASE64DECODE -> Base64.Default.decode(curr).decodeToString()
                        OP_JSONVALUE -> {
                            if (op.argument == null) throw IllegalArgumentException("No JSON key specified for jsonvalue")
                            val json = Json.decodeFromString<Map<String, String>>(curr)
                            json[op.argument].orEmpty()
                        }

                        else -> throw IllegalArgumentException("Unknown slot operation \"$op\"")
                    }
                } ?: group

                curr.replace(Regex("\\{\\{$index.*?}}"), value)
            }
    }

    private companion object {

        /**
         * Replacement slots are declared as `{{index}}` or `{{index:operations}}`,
         * for example `{{1}}` or `{{1:urldecode:base64decode}}`.
         */
        private val REGEX_SLOTS = Regex("\\{\\{(\\d)(:.+?)?}}")

        /**
         * Operations are declared as `:operation` or with an argument `:operation(argument)`.
         */
        private val REGEX_SLOTS_OPS = Regex(":([^:(]+)(?:\\(([^)]+)\\))?")

        private const val OP_URLDECODE = "urldecode"
        private const val OP_BASE64DECODE = "base64decode"
        private const val OP_JSONVALUE = "jsonvalue"
    }
}
