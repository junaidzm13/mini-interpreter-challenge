package com.csg.codeit.service

import com.csg.codeit.config.objectMapper
import com.csg.codeit.model.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EvaluatorServiceImpl(
    private val checker: Checker,
    private val webClient: WebClient,
) : EvaluatorService {
    private val logger: Logger = LoggerFactory.getLogger(EvaluatorService::class.java)

    override fun evaluateTeam(evaluatorDto: TeamEvaluatorDto): ChallengeResult {
        return checker.check(evaluatorDto.asRun())
    }

    private fun convert(rawResponse: String): Output = objectMapper.readValue(rawResponse, Output::class.java)

    private fun TeamEvaluatorDto.asRun(): ChallengeRun = { req ->
        logger.debug("Evaluating {} with: {}", runId, req)
        try {
            webClient.postJson(url.toHttpUrl().newBuilder().addPathSegment(endpointSuffix).build(), req)
                ?.use { it.body?.string()?.let(this@EvaluatorServiceImpl::convert) }
                ?.also { logger.debug("Got response for {}: {}", runId, it) }
        } catch (e: Exception) {
            logger.error("Unexpected error occurred when posting to $url with body $req: {}", e.message)
            null
        }
    }
}
