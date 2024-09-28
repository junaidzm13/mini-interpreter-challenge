package com.csg.codeit.solver

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ExpressionSolverTest {

  @Test
  def `can parse and evaluate list of expressions`(): Unit = {
    val expressions = List(
      "(set x (add 10 20))",
      "(set y (multiply x 2))",
      "(set z (subtract y (divide 100 10)))",
      "(set result (concat \"Result is: \" (str z)))",
      "(puts (str (lowercase (str result))))",
      "(set output (replace (concat (uppercase (lowercase result)) \".75\") (str 0) (str 5)))",
      "(puts (str (uppercase (uppercase (uppercase output)))))"
    )

    val expectedOutput = List("result is: 50", "RESULT IS: 55.75")

    val actual = ExpressionSolver.solve(expressions = expressions)

    assertThat(actual).isEqualTo(expectedOutput)
  }
}
