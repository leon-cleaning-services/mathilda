package mathilda.rule.impl

import kotlinx.coroutines.test.runTest
import mathilda.rule.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class TransformRuleTest {

    @Test
    fun shouldExtractValueFromUrl() {
        val rule = TransformRule(
            id = "transform",
            input = "[?&]result=([^&#]*)",
            output = "{{1:urldecode}}"
        )

        runTest {
            val result =
                rule("https://www.example.com?abc=123&result=https%3A%2F%2Fwww.example2.com%2F")

            assertIs<Rule.Result.Success>(result)
            assertEquals(
                "https://www.example2.com/",
                result.value
            )
        }
    }

    @Test
    fun shouldTransformUrl() {
        val rule = TransformRule(
            id = "transform",
            input = "[?&]id=(.*?)&title=([^&#]*)",
            output = "https://www.result.example?id={{1}}&title={{2}}"
        )

        runTest {
            val result =
                rule("https://www.example.com?id=12345&title=HelloWorld")

            assertIs<Rule.Result.Success>(result)
            assertEquals(
                "https://www.result.example?id=12345&title=HelloWorld",
                result.value
            )
        }
    }

    @Test
    fun shouldBase64Decode() {
        val rule = TransformRule(
            id = "transform",
            input = "[?&]id=(.*?)&title=([^&#]*)",
            output = "https://www.result.example?id={{1}}&title={{2:urldecode:base64decode}}",
        )

        runTest {
            val result =
                rule("https://www.example.com?id=12345&title=SGVsbG9Xb3JsZA%3D%3D")

            assertIs<Rule.Result.Success>(result)
            assertEquals(
                "https://www.result.example?id=12345&title=HelloWorld",
                result.value
            )
        }
    }
}
