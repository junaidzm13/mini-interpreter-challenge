package com.csg.codeit.model

import com.csg.codeit.config.objectMapper

data class TestRequest(val request: Int) : ChallengeRequest

data class TestResponse(val response: Int) : ChallengeResponse

data class TestChallengeRun(val response: ChallengeResponse?) : ChallengeRun {
    override val teamUrl: String get() = ""

    override fun invoke(challengeRequest: ChallengeRequest): ChallengeResponse? = response

    companion object {
        val TestResponse.asRun: TestChallengeRun get() = TestChallengeRun(this)
    }
}

object TestEvaluatorService : EvaluatorService {
    override val challenge: LevelBasedChallenge
        get() = throw UnsupportedOperationException()
    override val checker: Checker get() = TestChecker

    override fun evaluateTeam(challengeRun: ChallengeRun): ChallengeResult =
        with(TestRequest(100)) { challengeRun(this)?.let { checker.check(this, it) } } ?: ChallengeResult(0, "")

    override fun convert(rawResponse: String): ChallengeResponse = TestChecker.convert(rawResponse)
}

object TestChecker : Checker {
    override fun convert(rawResponse: String): ChallengeResponse =
        objectMapper.readValue(rawResponse, TestResponse::class.java)

    override fun check(request: ChallengeRequest, response: ChallengeResponse): ChallengeResult = when {
        (request is TestRequest && response is TestResponse) ->
            ChallengeResult(request.request.coerceAtMost(response.response), "")
        else -> throw UnsupportedOperationException()
    }
}

object LiteLevel : ChallengeLevel {
    override val difficulty: Int get() = 0
}

object LudicrousLevel : ChallengeLevel {
    override val difficulty: Int get() = 1
}
