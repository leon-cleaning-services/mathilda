package mathilda.rule

import mathilda.rule.Domain.tidy
import mathilda.rule.Rule.Result

internal abstract class BaseRule : Rule {

    override suspend fun invoke(url: String): Result {
        if (domains.isEmpty() && domainRegex == null) {
            return executeWithResult(url)
        }

        if (domains.any { Domain.matches(it, url) }) {
            return executeWithResult(url)
        }

        domainRegex?.let {
            if (Domain.matchesRegex(it, url)) {
                return executeWithResult(url)
            }
        }

        return Result.NoMatch(url)
    }

    internal abstract suspend fun execute(url: String): String

    private suspend fun executeWithResult(url: String): Result {
        val result = tidy(execute(url))

        return when {
            result == url -> Result.Unmodified(result)
            else -> Result.Success(result)
        }
    }
}
