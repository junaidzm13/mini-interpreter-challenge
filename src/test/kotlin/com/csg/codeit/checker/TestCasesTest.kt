package com.csg.codeit.checker

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class TestCasesTest {

    @ParameterizedTest
    @MethodSource("casesSource")
    fun `test cases are converted correctly`(legacyTestCases: List<out TestCase>, convertedTestCases: List<out TestCase>) {
        assertEquals(legacyTestCases, convertedTestCases)
    }

    companion object {

        @JvmStatic
        fun casesSource(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(legacyEasyTestCases(), convertedEasyTestCases()),
                Arguments.of(legacyIntermediateTestCases(), convertedIntermediateTestCases()),
                Arguments.of(legacyHardTestCases(), convertedHardTestCases())
            )
        }

    }

}