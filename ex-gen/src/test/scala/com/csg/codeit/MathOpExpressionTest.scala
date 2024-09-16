package com.csg.codeit

import com.csg.codeit.TestFixture.{assertAll, assertEqual, assertThrowsException}
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.{Nested, Test}

class MathOpExpressionTest {

  @Nested
  class VarArgMathOpExpressionTest {
    private val argsToTestWith = List(
      Seq(IntExpression(13), IntExpression(12)),
      Seq(DoubleExpression(13.0), DoubleExpression(12.5)),
      Seq(DoubleExpression(13.0), IntExpression(12)),
      Seq(IntExpression(4), DoubleExpression(-13.0), DoubleExpression(12.5), DoubleExpression(3.0)),
      Seq(IntExpression(10), IntExpression(15), IntExpression(20), IntExpression(5)),
      Seq(IntExpression(1)),
      Seq(),
      Seq(StringExpression("abc"), IntExpression(1))
    )

    @Test
    def addExpression(): Unit = {
      runTest(
        exBuilder = seq => AddExpression(seq: _*),
        expectedValues = List(
          "25",
          "25.5",
          "25.0",
          "6.5",
          "50",
          "ERROR at line 6",
          "ERROR at line 7",
          "ERROR at line 8"
        ),
        op = VarArgMathOp.ADD
      )
    }

    @Test
    def multiplyExpression(): Unit = {
      runTest(
        exBuilder = seq => MultiplyExpression(seq: _*),
        expectedValues = List(
          "156",
          "162.5",
          "156.0",
          "-1950.0",
          "15000",
          "ERROR at line 6",
          "ERROR at line 7",
          "ERROR at line 8"
        ),
        op = VarArgMathOp.MULTIPLY
      )
    }

    @Test
    def maxExpression(): Unit = {
      runTest(
        exBuilder = seq => MaxExpression(seq: _*),
        expectedValues = List(
          "13",
          "13.0",
          "13.0",
          "12.5",
          "20",
          "ERROR at line 6",
          "ERROR at line 7",
          "ERROR at line 8"
        ),
        op = VarArgMathOp.MAX
      )
    }

    @Test
    def minExpression(): Unit = {
      runTest(
        exBuilder = seq => MinExpression(seq: _*),
        expectedValues = List(
          "12",
          "12.5",
          "12.0",
          "-13.0",
          "5",
          "ERROR at line 6",
          "ERROR at line 7",
          "ERROR at line 8"
        ),
        op = VarArgMathOp.MIN
      )
    }

    private def runTest(exBuilder: Seq[Expression] => VarArgMathOpExpression,
                        expectedValues: List[String],
                        op: VarArgMathOp): Unit = {
      val exps = argsToTestWith.map(exBuilder)

      assertThat(Expression.stringify(exps)).isEqualTo(List(
        "(op 13 12)",
        "(op 13.0 12.5)",
        "(op 13.0 12)",
        "(op 4 -13.0 12.5 3.0)",
        "(op 10 15 20 5)",
        "(op 1)",
        "(op )",
        "(op \"abc\" 1)"
      ).map(_.replace("op", op.name)))

      assertThat(
        Expression.evaluate(exps.map(e => PutsExpression(StrExpression(e))))
      ).isEqualTo(expectedValues)
    }
  }

  @Nested
  class TwoArgMathOpExpressionTest {

    @Nested
    class Division {

      @Test
      def `can do integer division`(): Unit = {
        assertEqual((DivideExpression(IntExpression(10), IntExpression(4)), 2))
      }

      @Test
      def `can do integer division with a negative arg`(): Unit = {
        assertEqual(
          (DivideExpression(IntExpression(-10), IntExpression(4)), -2),
          (DivideExpression(IntExpression(10), IntExpression(-4)), -2),
        )
      }

      @Test
      def `throws when divisor is zero on integer division`(): Unit = {
        assertThrowsException(DivideExpression(IntExpression(10), IntExpression(0)))
      }

      @Test
      def `can do normal division`(): Unit = {
        assertEqual((DivideExpression(DoubleExpression(10), IntExpression(4)), 2.5))
      }

      @Test
      def `throws when divisor is zero on normal division`(): Unit = {
        assertThrowsException(DivideExpression(DoubleExpression(10), IntExpression(0)))
      }

      @Test
      def `throws when at least one is not a number`(): Unit = {
        assertThrowsException(
          DivideExpression(StringExpression("20.5"), IntExpression(10)),
          DivideExpression(IntExpression(20), BooleanExpression(true)),
          DivideExpression(StringExpression("25.25"), StringExpression("4.75")),
        )
      }
    }

    @Nested
    class Subtraction {
      @Test
      def `can do subtraction when both positive integers`(): Unit = {
        assertEqual(
          (SubtractExpression(IntExpression(20), IntExpression(10)), 10),
          (SubtractExpression(IntExpression(10), IntExpression(20)), -10),
        )
      }

      @Test
      def `can do subtraction when at least one integer is negative`(): Unit = {
        assertEqual(
          (SubtractExpression(IntExpression(-10), IntExpression(5)), -15),
          (SubtractExpression(IntExpression(10), IntExpression(-25)), 35),
          (SubtractExpression(IntExpression(-20), IntExpression(-5)), -15),
        )
      }

      @Test
      def `can do subtraction when at least one is double and both positive`(): Unit = {
        assertAll(
          SubtractExpression(DoubleExpression(20.5), IntExpression(10)),
          SubtractExpression(IntExpression(20), DoubleExpression(9.5)),
          SubtractExpression(DoubleExpression(25.25), DoubleExpression(14.75)),
        )(expected = 10.5)
      }

      @Test
      def `can do subtraction when at least one is double and at least one is negative`(): Unit = {
        assertEqual(
          (SubtractExpression(DoubleExpression(-20.5), IntExpression(10)), -30.5),
          (SubtractExpression(IntExpression(1), DoubleExpression(-9.5)), 10.5),
          (SubtractExpression(DoubleExpression(-4.25), DoubleExpression(-6.25)), 2.0),
        )
      }

      @Test
      def `throws when at least one is not a number`(): Unit = {
        assertThrowsException(
          SubtractExpression(StringExpression("20.5"), IntExpression(10)),
          SubtractExpression(IntExpression(20), BooleanExpression(true)),
          SubtractExpression(StringExpression("25.25"), StringExpression("4.75")),
        )
      }
    }
  }

  @Nested
  class Abs {
    @Test
    def `non-zero integer`(): Unit = {
      assertAll(
        AbsExpression(IntExpression(7)),
        AbsExpression(IntExpression(-7))
      )(expected = 7)
    }

    @Test
    def `zero integer`(): Unit = assertEqual((AbsExpression(IntExpression(0)), 0))

    @Test
    def `non-zero decimal`(): Unit = {
      assertAll(
        AbsExpression(DoubleExpression(7.25)),
        AbsExpression(DoubleExpression(-7.25))
      )(expected = 7.25)
    }

    @Test
    def `zero decimal`(): Unit = {
      assertEqual(
        (AbsExpression(DoubleExpression(0)), 0.0)
      )
    }

    @Test
    def `throws when not a number`(): Unit = {
      assertThrowsException(
        AbsExpression(StringExpression("7")),
        AbsExpression(BooleanExpression(false)),
        AbsExpression(NullExpression()),
      )
    }
  }
}
