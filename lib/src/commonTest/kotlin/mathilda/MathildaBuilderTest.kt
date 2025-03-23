package mathilda

import mathilda.rule.RemoveRegexRule
import mathilda.rule.RemoveRule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class MathildaBuilderTest {

    private val json = """
            {
                "version":1,
                "rules": {
                    "google": {
                        "type": "remove",
                        "parameters": ["utm_","ga_"],
                    },
                    "google2": {
                        "type":"remove_regex",
                        "regex": "regex",
                    }
                }
            }
        """.trimIndent()

    @Test
    fun shouldParseJson() {
        val mathilda = builder(json).build()

        assertEquals(mathilda.rules.size, 2)
        assertIs<RemoveRule>(mathilda.rules[0])
        assertIs<RemoveRegexRule>(mathilda.rules[1])
    }
}
