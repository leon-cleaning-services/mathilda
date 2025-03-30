package mathilda

import jsonStream
import kotlinx.collections.immutable.persistentListOf
import mathilda.rule.impl.RemoveParamsRule
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class JvmMathildaBuilderTest {

    @Test
    fun shouldParseJsonFile() {
        val mathilda = builder(jsonStream).build()

        assertEquals(mathilda.rules.size, 2)
        assertEquals(mathilda.enabledRules.size, 2)
        assertContains(
            mathilda.rules,
            RemoveParamsRule(
                id = "google_analytics",
                parameters = persistentListOf("ga_*", "utm_*", "gclid")
            )
        )
        assertContains(
            mathilda.rules,
            RemoveParamsRule(
                id = "google_play_store",
                domains = persistentListOf("store.google.com"),
                parameters = persistentListOf("hl", "selections"),
            )
        )
    }
}
