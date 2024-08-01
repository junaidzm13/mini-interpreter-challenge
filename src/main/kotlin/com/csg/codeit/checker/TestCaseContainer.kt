package com.csg.codeit.checker

class TestCaseContainer {
    fun getTestCases(): List<TestCase<out Any>> {
        return easyTestCases.shuffled().take(Difficulty.Easy.numCases) +
                intermediateTestCases.shuffled().take(Difficulty.Intermediate.numCases) +
                hardTestCases.shuffled().take(Difficulty.Hard.numCases)
    }
}