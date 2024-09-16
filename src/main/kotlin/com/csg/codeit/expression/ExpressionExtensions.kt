package com.csg.codeit.expression

import com.csg.codeit.expression.ExpressionGeneratorUtils.runEvaluate
import com.csg.codeit.expression.ExpressionGeneratorUtils.runStringify
import com.csg.codeit.checker.TestCase
import com.csg.codeit.model.Output
import com.csg.codeit.Expression
import kotlin.reflect.KClass

fun <T : TestCase> List<Expression>.toTestCase(cls: KClass<T>): T {
    val sExpressions = runStringify(this)
    val consoleOutputs = runEvaluate(this)
    return cls.constructors.single().call(sExpressions, Output(results = consoleOutputs))
}