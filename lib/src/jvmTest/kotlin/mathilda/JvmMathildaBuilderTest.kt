package mathilda

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JvmMathildaBuilderTest {

    @Test
    fun shouldParseJsonFile() {
        val mathilda = builder(jsonStream).build()

        assertEquals(6, mathilda.rules.size)
        assertEquals(6, mathilda.enabledRules.size)
        assertTrue { mathilda.rules.any { it.id == "google_analytics" } }
        assertTrue { mathilda.rules.any { it.id == "google_play_store" } }
    }
}
