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
        return if (response != null) {
            val score = if (isEqual(actual = response, expected = testCase.output)) testCase.difficulty.score else 0
            if (score == 0) {
                logger.info("[SCORE] Incorrect response: {} for input expression {}, expected: {}", response.output, testCase.expressions, testCase.output)
            }
            ChallengeResult(
                score = score
            )
        } else {
            ChallengeResult(
                score = 0,
                message = "Incorrect response format for some of the requests, please refer to attached challenge README."
            )
        }
    }

    private fun isEqual(actual: Output, expected: Output): Boolean {
        return actual.output.size == expected.output.size &&
                actual.output.zip(expected.output).all { it.first == it.second }
    }

}