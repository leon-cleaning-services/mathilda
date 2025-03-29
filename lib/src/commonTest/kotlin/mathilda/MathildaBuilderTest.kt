package mathilda

import kotlinx.collections.immutable.persistentListOf
import mathilda.rule.RemoveRegexRule
import mathilda.rule.RemoveRule
import mathilda.rule.noOpRule
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
        assertEquals(mathilda.enabledRules.size, 2)
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

    @Test
    fun shouldOnlyEnableCertainRule() {
        val rules = listOf(
            noOpRule(id = "rule1"),
            noOpRule(id = "rule2"),
            noOpRule(id = "rule3"),
        )

        val mathilda = builder(rules)
            .only("rule2")
            .build()

        assertEquals(mathilda.rules.size, 3)
        assertEquals(mathilda.enabledRules.size, 1)
        assertEquals(mathilda.enabledRules.first().id, "rule2")
    }

    @Test
    fun shouldDisableRules() {
        val rules = listOf(
            noOpRule(id = "rule1"),
            noOpRule(id = "rule2"),
            noOpRule(id = "rule3"),
        )

        val mathilda = builder(rules)
            .without("rule1")
            .without("rule2")
            .build()

        assertEquals(mathilda.rules.size, 3)
        assertEquals(mathilda.enabledRules.size, 1)
        assertEquals(mathilda.enabledRules.first().id, "rule3")
    }
}
