package com.csg.codeit.expression

import com.csg.codeit.expression.ExpressionGeneratorUtils.ScalaUtils.toScalaSeq
import com.csg.codeit.checker.EasyTestCase
import com.csg.codeit.checker.HardTestCase
import com.csg.codeit.checker.IntermediateTestCase
import com.csg.codeit.model.Output
import com.csg.codeit.AddExpression
import com.csg.codeit.ConcatExpression
import com.csg.codeit.DivideExpression
import com.csg.codeit.DoubleExpression
import com.csg.codeit.Expression
import com.csg.codeit.IntExpression
import com.csg.codeit.LowercaseExpression
import com.csg.codeit.MultiplyExpression
import com.csg.codeit.PutsExpression
import com.csg.codeit.ReplaceExpression
import com.csg.codeit.SetExpression
import com.csg.codeit.StrExpression
import com.csg.codeit.StringExpression
import com.csg.codeit.SubtractExpression
import com.csg.codeit.UppercaseExpression
import com.csg.codeit.VarExpression
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ExpressionTest {

    @Test
    fun `easy add case`() {
        val testCase = EasyTestCase(
            expressions = listOf("(puts (str (add 1 2)))"),
            Output(results = listOf("3"))
        )

        val l = toScalaSeq<Expression>(IntExpression(1), IntExpression(2))
        val exp = listOf(PutsExpression(StrExpression(AddExpression(l))))

        assertThat(testCase).isEqualTo(exp.toTestCase(EasyTestCase::class))
    }


    @Test
    fun `intermediate math expression add and subtract`() {
        val testCase = IntermediateTestCase(
            expressions = listOf("(puts (str (add (subtract 10 5) 20)))"),
            Output(results = listOf("25"))
        )

        val exp = listOf(
            PutsExpression(
                StrExpression(
                    AddExpression(toScalaSeq(
                        SubtractExpression(IntExpression(10), IntExpression(5)),
                        IntExpression(20)
                    ))
                )
            )
        )

        assertThat(testCase).isEqualTo(exp.toTestCase(IntermediateTestCase::class))
    }

    @Test
    fun `intermediate math expression division`() {
        val testCase = IntermediateTestCase(
            expressions = listOf(
                "(puts (str (divide 10 2)))",
                "(puts (str (divide 10 2.0)))",
                "(puts (str (divide 5.5 2.0)))",
                "(puts (str (divide 5 0)))",
            ),
            Output(results = listOf(
                "5",
                "5.0",
                "2.75",
                "ERROR at line 4"
            ))
        )

        val exp = listOf(
            PutsExpression(StrExpression(DivideExpression(IntExpression(10), IntExpression(2)))),
            PutsExpression(StrExpression(DivideExpression(IntExpression(10), DoubleExpression(2.0)))),
            PutsExpression(StrExpression(DivideExpression(DoubleExpression(5.5), DoubleExpression(2.0)))),
            PutsExpression(StrExpression(DivideExpression(IntExpression(5), IntExpression(0)))),
        )

        assertThat(testCase).isEqualTo(exp.toTestCase(IntermediateTestCase::class))
    }

    @Test
    fun `hard multi expression`() {
        val testCase = HardTestCase(
            expressions = listOf(
                "(set x (add 10 20))",
                "(set y (multiply x 2))",
                "(set z (subtract y (divide 100 10)))",
                "(set result (concat \"Result is: \" (str z)))",
                "(puts (str (lowercase (str result))))",
                "(set output (replace (concat (uppercase (lowercase result)) \".75\") (str 0) (str 5)))",
                "(puts (str (uppercase (uppercase (uppercase output)))))"
            ),
            Output(results = listOf("result is: 50", "RESULT IS: 55.75"))
        )

        val exp = listOf(
            SetExpression("x", AddExpression(toScalaSeq(IntExpression(10), IntExpression(20)))),
            SetExpression("y", MultiplyExpression(toScalaSeq(VarExpression("x"), IntExpression(2)))),
            SetExpression("z", SubtractExpression(VarExpression("y"), DivideExpression(IntExpression(100), IntExpression(10)))),
            SetExpression("result", ConcatExpression(StringExpression("Result is: "), StrExpression(VarExpression("z")))),
            PutsExpression(StrExpression(LowercaseExpression(StrExpression(VarExpression("result"))))),
            SetExpression("output", ReplaceExpression(
                ConcatExpression(
                    UppercaseExpression(LowercaseExpression(VarExpression("result"))),
                    StringExpression(".75")
                ),
                StrExpression(IntExpression(0)),
                StrExpression(IntExpression(5))
            )),
            PutsExpression(StrExpression(UppercaseExpression(UppercaseExpression(UppercaseExpression(VarExpression("output")))))),
        )

        assertThat(testCase).isEqualTo(exp.toTestCase(HardTestCase::class))
    }
}