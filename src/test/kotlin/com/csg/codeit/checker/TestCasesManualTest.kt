package com.csg.codeit.checker

import com.csg.codeit.config.objectMapper
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("Manual test")
class TestCasesManualTest {

    @Test
    fun `easy test cases`() {
        easyTestCases().forEach { println(it) }
    }


    @Test
    fun `super hard test cases`() {
        superHardTestCases().forEach { println(objectMapper.writeValueAsString(it)) }
    }
}