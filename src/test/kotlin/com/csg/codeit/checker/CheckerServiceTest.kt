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
    fun `test 'check' method`(name: String, actual: String?, expected: Any?, isEqual: Boolean) {
        every { testCaseContainer.getTestCases() } returns listOf(mockTestCase(expected))

        val mockRun: ChallengeRun = { ChallengeResponse(actual) }
        val res = checkerService.check(mockRun)

        assertThat(res).isEqualTo(challengeResult(if (isEqual) 1 else 0))
    }

    private fun <T> mockTestCase(result: T?): TestCase<T> {
        return EasyTestCase(expression = Expression("some-expression"), result)
    }

    private fun challengeResult(score: Int, message: String = "") = ChallengeResult(score = score, message = message)


    companion object {
        @JvmStatic
        private fun testCases() = Stream.of(
            // test case name | actual response | expected response | true if equal else false
            Arguments.of("simple integers match", "10", 10, true),
            Arguments.of("double with trailing 0 decimals matches with integer", "10.000", 10, true),
            Arguments.of("double with trailing 0 decimals matches with integer", "10", 10.0, true),
            Arguments.of("integer match fails when double has non-zero decimals", "10", 10.01, false),
            Arguments.of("integer match fails when double has non-zero decimals", "10.01", 10, false),

            Arguments.of("simple doubles match", "10.65", 10.65, true),
            Arguments.of("doubles match ignoring any trailing zeros", "10.650000", 10.650, true),
            Arguments.of("doubles match fails when not equal", "10.655", 10.65, false),
            Arguments.of("doubles match fails when not equal", "11.655", 10.655, false),

            Arguments.of("numbers as strings match when exact", "10.65", "10.65", true),
            Arguments.of("numbers as strings fails when not an exact match", "10.650", "10.65", false),
            Arguments.of("simple strings match", "foo", "foo", true),
            Arguments.of("simple strings fail match", "foO", "foo", false),

            Arguments.of("simple boolean match", "false", false, true),
            Arguments.of("simple boolean match", "true", true, true),
            Arguments.of("simple boolean match fail", "true", false, false),
            Arguments.of("boolean match fails if response is not all lower case", "False", false, false),


            Arguments.of("simple null match", null, null, true),
            Arguments.of("null as string is treated different from null value", "null", null, false),
            Arguments.of("null as string is treated different from null value", null, "null", false),
            Arguments.of("null match with number should fail", null, 55, false),
            Arguments.of("null match with number should fail", "60.5", null, false),
            Arguments.of("null match with boolean should fail", null, true, false),
            Arguments.of("null match with number should fail", "false", null, false),
        )
    }
}