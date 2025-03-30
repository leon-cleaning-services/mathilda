package mathilda.rule

import mathilda.rule.Domain.tidy
import mathilda.rule.Rule.Result

internal abstract class BaseRule : Rule {

    override suspend fun invoke(input: String): Result {
        if (domains.isEmpty() && domainRegex == null) {
            return executeWithResult(input)
        }

        if (domains.any { Domain.matches(it, input) }) {
            return executeWithResult(input)
        }

        domainRegex?.let {
            if (Domain.matchesRegex(it, input)) {
                return executeWithResult(input)
            }
        }

        return Result.NoMatch(input)
    }

    internal abstract suspend fun execute(input: String): String

    private suspend fun executeWithResult(input: String): Result {
        val result = tidy(execute(input))

        return when {
            result == input -> Result.Unmodified(result)
            else -> Result.Success(result)
        }
    }
}
