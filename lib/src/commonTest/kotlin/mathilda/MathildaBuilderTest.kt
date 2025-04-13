package mathilda

import kotlinx.collections.immutable.persistentListOf
import mathilda.rule.impl.RemoveParamsRule
import mathilda.rule.impl.RemoveRegexRule
import mathilda.rule.noOpRule
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class MathildaBuilderTest {

    private val json = """
            {
                "version":1,
                "rules": {
                    "google": {
                        "type": "remove_params",
                        "domains": ["google.com", "google.de"],
                        "parameters": ["utm_","ga_"],
                    },
                    "google2": {
                        "type":"remove_regex",
                        "domain_regex": "google\\.com",
                        "regex": "regex",
                    },
                    "disabled": {
                        "type":"remove_params",
                        "parameters": ["a","b"],
                        "enabled": false,
                    }
                }
            }
        """.trimIndent()

    @Test
    fun shouldParseJson() {
        val mathilda = builder(json).build()

        assertEquals(mathilda.rules.size, 3)
        assertEquals(mathilda.enabledRules.size, 2)
        assertContains(
            mathilda.rules,
            RemoveParamsRule(
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
        assertFalse { mathilda.isEnabled("disabled") }
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

        assertEquals(3, mathilda.rules.size)
        assertEquals(1, mathilda.enabledRules.size)
        assertEquals("rule2", mathilda.enabledRules.first().id)
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

        assertEquals(3, mathilda.rules.size)
        assertEquals(1, mathilda.enabledRules.size)
        assertEquals("rule3", mathilda.enabledRules.first().id)
    }
}
