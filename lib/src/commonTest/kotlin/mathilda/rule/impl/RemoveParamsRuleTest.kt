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
            id = "test1",
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
            id = "test2",
            parameters = persistentListOf(
                "utm_*",
                "ga_*",
                "test_ab?"
            )
        )

        runTest {
            val result =
                rule("https://www.example.com?utm_source=abc&ga_campaign=xyz&test_abc=123&test=456")

            assertIs<Rule.Result.Success>(result)
            assertEquals(
                "https://www.example.com?test=456",
                result.value
            )
        }
    }

    @Test
    fun shouldRemoveAllParameters() {
        val rule = RemoveParamsRule(
            id = "test3",
            parameters = persistentListOf("*")
        )

        runTest {
            val result =
                rule("https://www.example.com?utm_source=abc&ga_campaign=xyz&test_abc=123&test=456")

            assertIs<Rule.Result.Success>(result)
            assertEquals(
                "https://www.example.com",
                result.value
            )
        }
    }
}
