package com.csg.codeit.model

interface RequestPayload

data class ChallengeRequest(val expressions: List<String>) : RequestPayload

data class ChallengeResult(val score: Int = 0, val message: String = "") {
    operator fun plus(another: ChallengeResult) = copy(
        score = score + another.score,
        message = listOf(message, another.message).filter { it != "" }.joinToString(", ")
    )
}

typealias ChallengeRun = (ChallengeRequest) -> Output?

interface Checker {
    fun check(eval: ChallengeRun): ChallengeResult
}

interface EvaluatorService {
    fun evaluateTeam(evaluatorDto: TeamEvaluatorDto): ChallengeResult
}

data class EvaluationRequest(val runId: String, val teamUrl: String, val callbackUrl: String)

data class EvaluationResultRequest(val runId: String, val score: Int, val message: String) : RequestPayload
