package com.csg.codeit.expression

import com.csg.codeit.expression.ExpressionGeneratorUtils.runEvaluate
import com.csg.codeit.expression.ExpressionGeneratorUtils.runStringify
import com.csg.codeit.checker.TestCase
import com.csg.codeit.model.Output
import com.csg.codeit.Expression

inline fun <reified T : TestCase> List<Expression>.toTestCase(): T {
    val sExpressions = runStringify(this)
    val consoleOutputs = runEvaluate(this)
    return T::class.constructors.single().call(sExpressions, Output(output = consoleOutputs))
}