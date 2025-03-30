package rules

import jsonStream
import kotlinx.coroutines.test.runTest
import mathilda.builder
import kotlin.test.Test
import kotlin.test.assertEquals

class GoogleAnalyticsRuleTest {

    @Test
    fun shouldRemoveGoogleAnalyticsParameters() {
        val mathilda = builder(jsonStream)
            .only("google_analytics")
            .build()

        runTest {
            val result = mathilda.clean("https://www.example.com?ga_abc=123&utm_def=456&gclid=789")
            assertEquals(result.getOrNull(), "https://www.example.com")
        }
    }
}
