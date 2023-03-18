package com.csg.codeit.service

import com.csg.codeit.config.AppConfig
import com.csg.codeit.config.objectMapper
import com.csg.codeit.model.EvaluationRequest
import com.csg.codeit.model.EvaluationResultRequest
import com.csg.codeit.model.TestEvaluatorService
import com.csg.codeit.model.TestResponse
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CoordinatorServiceTest {

    private val appConfig = AppConfig(endpointSuffix = "test", coordinatorAuthToken = "COORDINATOR_AUTH_TOKEN")

    private lateinit var mockWebServer: MockWebServer

    private lateinit var coordinatorService: CoordinatorService

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer().also { it.start() }
        coordinatorService = CoordinatorService(appConfig, appConfig.httpClient(), TestEvaluatorService)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `can evaluate team`() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse = when (request.path) {
                "/${appConfig.endpointSuffix}" -> MockResponse()
                    .setResponseCode(200)
                    .setBody(objectMapper.writeValueAsString(TestResponse(50)))
                "/evaluate" -> MockResponse().setResponseCode(204).also {
                    assertThat(
                        objectMapper.readValue(request.body.readUtf8(), EvaluationResultRequest::class.java)
                    ).isEqualTo(EvaluationResultRequest("runId", 50, ""))
                }
                else -> MockResponse().setResponseCode(500)
            }
        }
        coordinatorService(
            EvaluationRequest(
                runId = "runId",
                teamUrl = mockWebServer.url("").toString(),
                callbackUrl = mockWebServer.url("evaluate").toString()
            )
        )
    }
}