package com.csg.codeit.checker

import com.csg.codeit.model.*
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class CheckerServiceTest {

    private val testCaseContainer: TestCaseContainer = mockk()
    private val checkerService: Checker = CheckerService(testCaseContainer)

    @MethodSource("testCases")
    @ParameterizedTest(name = "{0} where actual = `{1}` and expected = `{2}`")
    fun `test 'check' method`(name: String, actual: List<String>, expected: List<String>, isEqual: Boolean) {
        every { testCaseContainer.getTestCases() } returns listOf(mockTestCase(expected))

        val mockRun: ChallengeRun = { ChallengeResponse(Output(actual)) }
        val res = checkerService.check(mockRun)

        assertThat(res).isEqualTo(challengeResult(if (isEqual) 1 else 0))
    }

    private fun mockTestCase(result: List<String>): TestCase {
        return EasyTestCase(expressions = listOf("some-expression"), output = Output(result))
    }

    private fun challengeResult(score: Int, message: String = "") = ChallengeResult(score = score, message = message)

    companion object {
        @JvmStatic
        private fun testCases() = Stream.of(
            // test case name | actual response | expected response | true if equal else false
            Arguments.of("length of actual is greater than expected", listOf("10"), listOf("10", "0.5"), false),
            Arguments.of("length of expected is greater than actual", listOf("10", "0.5"), listOf("10"), false),
            Arguments.of("when lengths match but values do not", listOf("10", "0.5"), listOf("10.0", "0.5"), false),
            Arguments.of("when values and lengths both match", listOf("10", "0.5"), listOf("10", "0.5"), true),
        )
    }
}