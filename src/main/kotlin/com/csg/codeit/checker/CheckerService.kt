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
        .map { Pair(eval(ChallengeRequest(expressions = it.expressions)), it)}
        .map { score(it.first, it.second) }
        .fold(ChallengeResult(), ChallengeResult::plus)
    }

    private fun score(response: Output?, testCase: TestCase): ChallengeResult {
        return response?.let {
            ChallengeResult((if (isEqual(actual = it, expected = testCase.output)) testCase.difficulty.score else 0))
        } ?: ChallengeResult(
            score = 0,
            message = "Incorrect response format for some of the requests, please refer to attached challenge README."
        )
    }

    private fun isEqual(actual: Output, expected: Output): Boolean {
        return actual.output.size == expected.output.size &&
                actual.output.zip(expected.output).all { it.first == it.second }
    }

}