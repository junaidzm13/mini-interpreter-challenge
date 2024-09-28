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
        .mapIndexed { idx, tc -> score(tc.first, tc.second, idx + 1) }
        .fold(ChallengeResult(), ChallengeResult::plus)
    }

    private fun score(response: Output?, testCase: TestCase, position: Int): ChallengeResult {
        val msgPrefix = "TestCase $position"
        return if (response != null) {
            if (isEqual(actual = response, expected = testCase.output)) {
                ChallengeResult(score = testCase.difficulty.score, message = "$msgPrefix - Passed")
            } else {
                logger.info("[SCORE] Incorrect response: {} for input expression {}, expected: {}", response.output, testCase.expressions, testCase.output)
                ChallengeResult(score = 0, message = "$msgPrefix - Failed")
            }
        } else {
            ChallengeResult(
                score = 0,
                message = "$msgPrefix - Incorrect response format"
            )
        }
    }

    private fun isEqual(actual: Output, expected: Output): Boolean {
        return actual.output.size == expected.output.size &&
                actual.output.zip(expected.output).all { it.first == it.second }
    }

}