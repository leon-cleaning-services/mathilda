package mathilda

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import mathilda.json.Deserializer
import mathilda.rule.Rule
import mathilda.rule.RuleFactory

public fun builder(json: String): Mathilda.Builder = Mathilda.Builder(json)

public class Mathilda internal constructor(
    public val rules: ImmutableList<Rule>,
) {

    public class Builder(private val json: String) {

        public fun build(): Mathilda {
            val root = Deserializer.decodeFromString(json)
            return Mathilda(
                rules = root.rules.map(RuleFactory::fromJsonRule).toImmutableList()
            )
        }
    }

    public fun clean(input: String): String {
        return input
    }
}
