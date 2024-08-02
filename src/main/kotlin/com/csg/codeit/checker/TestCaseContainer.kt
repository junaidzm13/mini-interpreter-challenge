package com.csg.codeit.checker

import org.springframework.stereotype.Component

@Component
class TestCaseContainer {
    fun getTestCases(): List<TestCase> {
        return easyTestCases.shuffled().take(Difficulty.Easy.numCases) +
                intermediateTestCases.shuffled().take(Difficulty.Intermediate.numCases) +
                hardTestCases.shuffled().take(Difficulty.Hard.numCases)
    }
}