package mathilda

import kotlinx.collections.immutable.persistentListOf
import mathilda.rule.RemoveRegexRule
import mathilda.rule.RemoveRule
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class MathildaBuilderTest {

    private val json = """
            {
                "version":1,
                "rules": {
                    "google": {
                        "type": "remove",
                        "domains": ["google.com", "google.de"],
                        "parameters": ["utm_","ga_"],
                    },
                    "google2": {
                        "type":"remove_regex",
                        "domain_regex": "google\\.com",
                        "regex": "regex",
                    }
                }
            }
        """.trimIndent()

    @Test
    fun shouldParseJson() {
        val mathilda = builder(json).build()

        assertEquals(mathilda.rules.size, 2)
        assertContains(
            mathilda.rules,
            RemoveRule(
                id = "google",
                domains = persistentListOf("google.com", "google.de"),
                parameters = persistentListOf("utm_", "ga_")
            )
        )
        assertContains(
            mathilda.rules,
            RemoveRegexRule(
                id = "google2",
                domainRegex = "google\\.com",
                regex = "regex",
            )
        )
    }
}
