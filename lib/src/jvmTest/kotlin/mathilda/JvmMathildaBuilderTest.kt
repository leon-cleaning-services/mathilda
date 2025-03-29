package mathilda

import kotlinx.collections.immutable.persistentListOf
import mathilda.rule.RemoveRegexRule
import mathilda.rule.RemoveRule
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class JvmMathildaBuilderTest {

    @Test
    fun shouldParseJsonFile() {
        val stream = javaClass.getResourceAsStream("/test.json")
            ?: throw IllegalArgumentException("File test.json not found")
        val mathilda = builder(stream).build()

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
