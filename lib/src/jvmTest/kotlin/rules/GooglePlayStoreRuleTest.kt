package rules

import jsonStream
import kotlinx.coroutines.test.runTest
import mathilda.builder
import kotlin.test.Test
import kotlin.test.assertEquals

class GooglePlayStoreRuleTest {

    @Test
    fun shouldRemoveGooglePlayStoreParameters() {
        val mathilda = builder(jsonStream)
            .only("google_play_store")
            .build()

        runTest {
            val result = mathilda.clean(
                "https://store.google.com/gb/product/chromecast_google_tv?hl=en-GB" +
                    "&selections=eyJwcm9kdWN0RmFtaWx5IjoiWTJoeWIyMWxZMkZ6ZEY5bmIyOW5iR1" +
                    "ZmZEhZPSIsImhlcm9Qcm9kdWN0cyI6W1siY0hKa1h6YzRNekpmTXprMU1nPT0iLDEs" +
                    "bnVsbF1dfQ%3D%3D"
            )
            assertEquals(
                result.getOrNull(),
                "https://store.google.com/gb/product/chromecast_google_tv"
            )
        }
    }
}
