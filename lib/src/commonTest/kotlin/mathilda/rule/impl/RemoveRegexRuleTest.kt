package mathilda.rule.impl

import kotlinx.coroutines.test.runTest
import mathilda.rule.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RemoveRegexRuleTest {

    @Test
    fun shouldRemoveRegularExpression() {
        val rule = RemoveRegexRule(
            id = "google_analytics",
            regex = "[?&](utm_|ga_).*?=([^&#]*)"
        )

        runTest {
            val result = rule("https://www.example.com?utm_source=abc&abc=123&ga_campaign=xyz")

            assertIs<Rule.Result.Success>(result)
            assertEquals(
                "https://www.example.com?abc=123",
                result.value
            )
        }
    }
}
