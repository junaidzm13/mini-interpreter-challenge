package com.csg.codeit.checker

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("Manual test")
class TestCasesManualTest {

    @Test
    fun `easy test cases`() {
        easyTestCases().forEach { println(it) }
    }

}