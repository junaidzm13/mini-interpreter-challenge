package com.csg.codeit.checker

interface TestCase<R> {
    val expression: Expression
    val difficulty: Difficulty
    val result: R?
}
data class EasyTestCase<R>(
    override val expression: Expression,
    override val result: R?
) : TestCase<R> {
    override val difficulty: Difficulty
        get() = Difficulty.Easy
}

data class IntermediateTestCase<R>(
    override val expression: Expression,
    override val result: R?
) : TestCase<R> {
    override val difficulty: Difficulty
        get() = Difficulty.Intermediate
}

data class HardTestCase<R>(
    override val expression: Expression,
    override val result: R?
) : TestCase<R> {
    override val difficulty: Difficulty
        get() = Difficulty.Hard
}

enum class Difficulty(val score: Int, val numCases: Int) {
    Easy(score = 1, numCases = 10), // 10 * 1 = 10
    Intermediate(score = 3, numCases = 10), // 10 * 3 = 30
    Hard(score = 5, numCases = 12); // 12 * 5 = 60
}

data class Expression(val value: String)
