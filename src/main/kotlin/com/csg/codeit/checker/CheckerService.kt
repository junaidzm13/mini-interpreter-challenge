package com.csg.codeit.checker

import com.csg.codeit.model.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CheckerService(private val testCaseContainer: TestCaseContainer) : Checker {
    private val logger: Logger = LoggerFactory.getLogger(CheckerService::class.java)

    override fun check(eval: ChallengeRun): ChallengeResult {
        return testCaseContainer.getTestCases()
        .map { Pair(eval(ChallengeRequest(it.expression)), it)}
        .map { score(it.first, it.second) }
        .fold(ChallengeResult(), ChallengeResult::plus)
    }

    private fun score(response: ChallengeResponse?, testCase: TestCase<out Any>): ChallengeResult {
        return response?.let {
            ChallengeResult((if (isEqual(actual = it.result, expected = testCase.result)) testCase.difficulty.score else 0))
        } ?: ChallengeResult(
            score = 0,
            message = "Incorrect response format for some of the requests, please refer to attached challenge README."
        )
    }

    private fun isEqual(actual: String?, expected: Any?): Boolean {
        if (actual == null) return expected == null

        return when (expected) {
            is Int         -> actual.toDoubleOrNull()?.let { it == expected.toDouble() } ?: false
            is Double      -> actual.toDoubleOrNull()?.let { it == expected } ?: false
            is Boolean     -> actual.toBooleanStrictOrNull()?.let { it == expected } ?: false
            is String      -> actual == expected
            null           -> false
            else           -> false.also { logger.error("Unexpected error: Expected result {} for one of the test cases does not confirm to the defined contract.", expected) }
        }
    }

}