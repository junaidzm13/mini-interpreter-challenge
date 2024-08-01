package com.csg.codeit

import com.csg.codeit.checker.*
import com.csg.codeit.config.AppConfig
import com.csg.codeit.config.objectMapper
import com.csg.codeit.model.*
import com.csg.codeit.service.CoordinatorService
import com.csg.codeit.service.EvaluatorServiceImpl
import com.csg.codeit.service.WebClient
import io.mockk.every
import io.mockk.mockk
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class EvaluationEndToEndTest {

    private val appConfig = AppConfig(endpointSuffix = "test", coordinatorAuthToken = "COORDINATOR_AUTH_TOKEN")
    private val webClient = WebClient(appConfig.httpClient())
    private val testCaseContainer: TestCaseContainer = mockk()
    private val checker: Checker = CheckerService(testCaseContainer)
    private val evaluatorService: EvaluatorService = EvaluatorServiceImpl(webClient = webClient, checker = checker)
    private val coordinatorService = CoordinatorService(appConfig, webClient, evaluatorService)

    private lateinit var mockWebServer: MockWebServer

    @BeforeEach
    fun setUp() {
        every { testCaseContainer.getTestCases() } returns listOf(TEST_CASE)
        mockWebServer = MockWebServer().also { it.start() }
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `can evaluate team when correct response`() {
        val expectedEvaluationResult = EvaluationResultRequest(RUN_ID, TEST_CASE.difficulty.score, "")

        setMockServerResponses(
            mockTeamResponse = MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(ChallengeResponse("5"))),
            expectedEvaluationResultRequest = expectedEvaluationResult
        )

        assertThat(coordinatorService(getEvalReq())).isEqualTo(expectedEvaluationResult)
    }

    @Test
    fun `can evaluate team when incorrect response`() {
        val expectedEvaluationResult = EvaluationResultRequest(RUN_ID, 0, "")

        setMockServerResponses(
            mockTeamResponse = MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(ChallengeResponse("5.5"))),
            expectedEvaluationResultRequest = expectedEvaluationResult
        )

        assertThat(coordinatorService(getEvalReq())).isEqualTo(expectedEvaluationResult)
    }

    @Test
    fun `can evaluate team when response format is incorrect`() {
        val expectedEvaluationResult = EvaluationResultRequest(RUN_ID, 0, "Incorrect response format for some of the requests, please refer to attached challenge README.")

        setMockServerResponses(
            mockTeamResponse = MockResponse()
                .setResponseCode(200)
                .setBody(objectMapper.writeValueAsString("5")),
            expectedEvaluationResultRequest = expectedEvaluationResult
        )

        assertThat(coordinatorService(getEvalReq())).isEqualTo(expectedEvaluationResult)
    }

    private fun setMockServerResponses(
        mockTeamResponse: MockResponse,
        expectedEvaluationResultRequest: EvaluationResultRequest
    ) {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse = when (request.path) {
                "/${appConfig.endpointSuffix}" -> mockTeamResponse
                "/evaluate" -> MockResponse().setResponseCode(204).also {
                    assertThat(
                        objectMapper.readValue(request.body.readUtf8(), EvaluationResultRequest::class.java)
                    ).isEqualTo(expectedEvaluationResultRequest)
                }
                else -> MockResponse().setResponseCode(500)
            }
        }
    }

    private fun getEvalReq(): EvaluationRequest = EvaluationRequest(
        runId = RUN_ID,
        teamUrl = mockWebServer.url("").toString(),
        callbackUrl = mockWebServer.url("evaluate").toString()
    )

    companion object {
        private const val RUN_ID = "runId"
        private val TEST_CASE: TestCase<Int> = EasyTestCase(expression = Expression(value = "(add, 4, 1)"), result = 5)
    }
}