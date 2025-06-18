package mathilda

import kotlinx.coroutines.test.runTest
import mathilda.rule.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


class RulesTest {

    @Test
    fun shouldTestRules() {
        val mathilda = builder(jsonStream).build()

        mathilda.rules.forEach { rule ->
            if (rule.tests.isNotEmpty() && rule.tests[0].skip) {
                return@forEach // Skip rules that are not applicable
            }

            rule.tests.forEach { test ->
                runTest {
                    val actual = rule(test.input)

                    assertIs<Rule.Result.Success>(
                        actual,
                        "\nrule id: ${rule.id}\n" +
                            "input: ${test.input}\n" +
                            "expected: ${test.expected}\n"
                    )

                    assertEquals(
                        test.expected,
                        actual.value,
                        "\nrule id: ${rule.id}\n" +
                            "input: ${test.input}\n" +
                            "expected: ${test.expected}\n" +
                            "actual: ${actual.value})\n"
                    )
                }
            }
        }
    }
}
