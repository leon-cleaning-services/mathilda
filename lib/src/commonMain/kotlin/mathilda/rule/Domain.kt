package mathilda.rule

internal object Domain {

    /**
     * Returns `true` if `domain` is present in `url`
     */
    fun matches(domain: String, url: String) =
        matchesRegex(domain.replace(".", "\\."), url)

    /**
     * Returns `true` if `domainRegex` is present in `url`
     */
    @Suppress("RegExpUnnecessaryNonCapturingGroup")
    fun matchesRegex(domainRegex: String, url: String) =
        Regex("^(?:https?://)?(?:www\\.)?(?:$domainRegex).*").matches(url)

    /**
     * Tidies `url` changing the first `&` separator to `?` if no `?` is present
     */
    fun tidy(url: String) =
        if (url.contains('?')) url else url.replaceFirst('&', '?')
}
