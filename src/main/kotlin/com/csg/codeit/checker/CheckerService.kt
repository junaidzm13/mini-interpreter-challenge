package com.csg.codeit.checker

import com.csg.codeit.model.*
import org.springframework.stereotype.Service

@Service
class CheckerService : Checker {
    override fun check(eval: ChallengeRun): ChallengeResult {
        return (
            easyTestCases.shuffled().take(Difficulty.Easy.numCases) +
            intermediateTestCases.shuffled().take(Difficulty.Intermediate.numCases) +
            hardTestCases.shuffled().take(Difficulty.Hard.numCases)
        )
        .map { Pair(eval(ChallengeRequest(it.expression)), it)}
        .map { score(it.first, it.second) }
        .fold(ChallengeResult(), ChallengeResult::plus)
    }

    private fun score(response: ChallengeResponse?, testCase: TestCase<out Any>): ChallengeResult {
        return response?.let {
            ChallengeResult((if (it.result == testCase.result) testCase.difficulty.score else 0))
        } ?: ChallengeResult(
            score = 0,
            message = "Incorrect response format for some of the requests, please refer to attached challenge README."
        )
    }

}