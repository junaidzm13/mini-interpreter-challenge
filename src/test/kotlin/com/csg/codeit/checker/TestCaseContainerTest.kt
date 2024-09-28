package com.csg.codeit.checker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.xmlunit.diff.Diff

class TestCaseContainerTest {

    private val container = TestCaseContainer()

    @Test
    fun `should return correct distribution of test cases that sum to a score of 100`() {
        val testCases = container.getTestCases()

        val totalScore = testCases.map { it.difficulty.score }.sum()
        assertThat(totalScore).isEqualTo(100)

        val testsByDifficulty = testCases.groupBy { it.difficulty }
        Difficulty.values().forEach { assertThat(testsByDifficulty[it]).hasSize(it.numCases) }
    }
}