package mathilda.rule

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DomainTest {

    @Test
    fun shouldMatchDomain() {
        assertTrue("match google.com") {
            Domain.matches("google.com", "https://google.com/")
        }

        assertTrue("match startpage.com") {
            Domain.matches(
                "startpage.com",
                "https://www.startpage.com/"
            )
        }

        assertTrue("match duckduckgo.com") {
            Domain.matches(
                "duckduckgo.com",
                "www.duckduckgo.com/path"
            )
        }

        assertFalse("don't match google.com") {
            Domain.matches(
                "google.com",
                "https://googleadservices.com/"
            )
        }
    }

    @Test
    fun shouldMatchDomainRegex() {
        assertTrue("match google\\.(com|de)") {
            Domain.matchesRegex(
                "google\\.(com|de)",
                "https://google.de/"
            )
        }

        assertTrue("match google\\.com|startpage\\.com") {
            Domain.matchesRegex(
                "google\\.com|startpage\\.com",
                "https://startpage.com/"
            )
        }
    }
}
