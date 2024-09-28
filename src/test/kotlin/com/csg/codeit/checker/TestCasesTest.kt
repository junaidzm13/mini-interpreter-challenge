package com.csg.codeit.checker

import com.csg.codeit.expression.ExpressionGeneratorUtils.ScalaUtils.toKotlin
import com.csg.codeit.expression.ExpressionGeneratorUtils.ScalaUtils.toScala
import com.csg.codeit.solver.ExpressionSolver
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class TestCasesTest {

    @ParameterizedTest
    @MethodSource("casesSource")
    fun `test cases are converted correctly`(legacyTestCases: List<TestCase>, convertedTestCases: List<TestCase>) {
        assertEquals(legacyTestCases, convertedTestCases)
    }

    @ParameterizedTest
    @MethodSource("casesSource")
    fun `can solve legacy test cases`(legacyTestCases: List<TestCase>) {
        legacyTestCases.forEach {
            assertThat(toKotlin(ExpressionSolver.solve(toScala(it.expressions)))).isEqualTo(it.output.output)
        }
    }

    @ParameterizedTest
    @MethodSource("latestTestCases")
    fun `can solve all latest test cases`(testCase: TestCase) {
        assertThat(toKotlin(ExpressionSolver.solve(toScala(testCase.expressions)))).isEqualTo(testCase.output.output)
    }

    companion object {

        @JvmStatic
        fun casesSource(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(legacyEasyTestCases(), convertedEasyTestCases()),
                Arguments.of(legacyIntermediateTestCases(), convertedLegacyIntermediateTestCases()),
                Arguments.of(legacyHardTestCases(), convertedLegacyHardTestCases())
            )
        }

        @JvmStatic
        fun latestTestCases(): Stream<Arguments> {
            return (
                easyTestCases() +
                intermediateTestCases() +
                hardTestCases() +
                superHardTestCases()
            ).map { Arguments.of(it) }.stream()
        }

    }

}