package com.csg.codeit.checker

import org.springframework.stereotype.Component

@Component
class TestCaseContainer {
    fun getTestCases(): List<TestCase> {
        return (
            easyTestCases().shuffled().take(Difficulty.Easy.numCases) +
            intermediateTestCases().shuffled().take(Difficulty.Intermediate.numCases) +
            getHardTestCases()
        ).shuffled()
    }

    private fun getHardTestCases(): List<HardTestCase> {
        val superHard = superHardTestCases().shuffled().take(Difficulty.Hard.numCases / 2)
        return hardTestCases().shuffled().take(Difficulty.Hard.numCases - superHard.size) + superHard
    }
}