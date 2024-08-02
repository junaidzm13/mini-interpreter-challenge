package com.csg.codeit.checker

import  com.csg.codeit.model.Output

interface TestCase {
    val expressions: List<String>
    val difficulty: Difficulty
    val output: Output
}

data class EasyTestCase(
    override val expressions: List<String>,
    override val output: Output
) : TestCase {
    override val difficulty: Difficulty
        get() = Difficulty.Easy
}

data class IntermediateTestCase(
    override val expressions: List<String>,
    override val output: Output
) : TestCase {
    override val difficulty: Difficulty
        get() = Difficulty.Intermediate
}

data class HardTestCase(
    override val expressions: List<String>,
    override val output: Output
) : TestCase {
    override val difficulty: Difficulty
        get() = Difficulty.Hard
}

enum class Difficulty(val score: Int, val numCases: Int) {
    Easy(score = 1, numCases = 10), // 10 * 1 = 10
    Intermediate(score = 3, numCases = 10), // 10 * 3 = 30
    Hard(score = 5, numCases = 12); // 12 * 5 = 60
}

data class Expression(val value: String)
