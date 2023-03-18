package com.csg.codeit.service

import com.csg.codeit.config.AppConfig
import com.csg.codeit.model.EvaluationRequest
import com.csg.codeit.model.EvaluationResultRequest
import com.csg.codeit.model.EvaluatorService
import com.csg.codeit.model.TeamEvaluatorDto
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CoordinatorService(
    val appConfig: AppConfig,
    val webClient: WebClient,
    val evaluatorService: EvaluatorService
) {
    private val logger: Logger = LoggerFactory.getLogger(CoordinatorService::class.java)

    operator fun invoke(evaluationRequest: EvaluationRequest) {
        val result = evaluatorService.evaluateTeam(evaluationRequest.toTeamEvaluatorDto())
            .let { EvaluationResultRequest(evaluationRequest.runId, it.score, it.message) }
        try {
            webClient.postJson(evaluationRequest.callbackUrl.toHttpUrl(), result) {
                it.addHeader("Authorization", appConfig.bearerToken)
            }?.also { logger.info("Notified coordinator with: $result") }
                ?: logger.warn("Error notifying coordinator with: $result")
        } catch (e: Exception) {
            logger.error("Error notifying coordinator with: $result\nException message: ${e.message}")
        }
    }

    private fun EvaluationRequest.toTeamEvaluatorDto(): TeamEvaluatorDto {
        return TeamEvaluatorDto(
            url = teamUrl,
            endpointSuffix = appConfig.endpointSuffix,
            runId = runId
        )
    }

    private val AppConfig.bearerToken: String get() = "Bearer $coordinatorAuthToken"
}
