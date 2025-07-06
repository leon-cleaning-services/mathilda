package mathilda.rule.impl

import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import mathilda.rule.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class KeepParamsRuleTest {

    @Test
    fun shouldKeepParameters() {
        val rule = KeepParamsRule(
            id = "test1",
            parameters = persistentListOf("id")
        )

        runTest {
            val result = rule("https://www.example.com?utm_source=abc&utm_campaign=xyz&id=123")

            assertIs<Rule.Result.Success>(result)
            assertEquals(
                "https://www.example.com?id=123",
                result.value
            )
        }
    }

    @Test
    fun shouldKeepWildcardParameters() {
        val rule = KeepParamsRule(
            id = "test2",
            parameters = persistentListOf(
                "id_*",
                "test?",
            )
        )

        runTest {
            val result =
                rule("https://www.example.com?utm_source=abc&ga_campaign=xyz&test1=123&id_x=456")

            assertIs<Rule.Result.Success>(result)
            assertEquals(
                "https://www.example.com?test1=123&id_x=456",
                result.value
            )
        }
    }
}
