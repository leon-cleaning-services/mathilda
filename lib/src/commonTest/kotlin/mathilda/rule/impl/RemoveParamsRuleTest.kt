package mathilda.rule.impl

import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import mathilda.rule.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RemoveParamsRuleTest {

    @Test
    fun shouldRemoveParameters() {
        val rule = RemoveParamsRule(
            id = "google_analytics",
            parameters = persistentListOf(
                "utm_source",
                "utm_medium",
                "utm_campaign",
                "utm_term",
                "utm_content",
            )
        )

        runTest {
            val result = rule("https://www.example.com?utm_source=abc&utm_campaign=xyz&abc=123")

            assertIs<Rule.Result.Success>(result)
            assertEquals(
                "https://www.example.com?abc=123",
                result.value
            )
        }
    }

    @Test
    fun shouldRemoveWildcardParameters() {
        val rule = RemoveParamsRule(
            id = "google_analytics",
            parameters = persistentListOf(
                "utm_*",
                "ga_*",
                "test_ab?"
            )
        )

        runTest {
            val result = rule("https://www.example.com?utm_source=abc&ga_campaign=xyz&test_abc=123")

            assertIs<Rule.Result.Success>(result)
            assertEquals(
                "https://www.example.com",
                result.value
            )
        }
    }
}
