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
