package com.csg.codeit.checker

val easyTestCases: List<EasyTestCase<out Any>> = listOf(
    // addition
    EasyTestCase(expression = Expression(value = "(add, 1, 2)"), result = 3),

    // subtraction
    EasyTestCase(expression = Expression(value = "(subtract, 15.5, 4.0)"), result = 11.5),
    EasyTestCase(expression = Expression(value = "(subtract, 1, 2)"), result = -1),

    // multiplication
    EasyTestCase(expression = Expression(value = "(multiply, 10.6, 2.0, 10.0)"), result = 212.0),
    EasyTestCase(expression = Expression(value = "(multiply, 3.0, -2.5)"), result = -7.5),

    // division
    EasyTestCase(expression = Expression(value = "(divide, 25.5, -5.1)"), result = -5.0),
    EasyTestCase(expression = Expression(value = "(divide, 650.25, 5.0, 5.0)"), result = 26.01),

    // concatenation
    EasyTestCase(expression = Expression(value = "(concat, \"MyParser\", \"Is\", \"Best\")"), result = "MyParserIsBest")
)

val intermediateTestCases: List<IntermediateTestCase<out Any>> = listOf()
val hardTestCases: List<HardTestCase<out Any>> = listOf()
