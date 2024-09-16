package com.csg.codeit

import com.csg.codeit.model.Context
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows

object TestFixture {
  type Expected = Any
  def assertEqual(args: (Expression, Expected)*): Unit = {
    args.foreach({
      case (expression, expected) => assertThat(expression.evaluate(Context.empty)).isEqualTo(expected)
    })
  }

  def assertAll(args: Expression*)(expected: Expected): Unit = {
    args.foreach(e => assertThat(e.evaluate(Context.empty)).isEqualTo(expected))
  }

  def assertThrowsException(args: Expression*): Unit = {
    args.foreach(e => assertThrows(classOf[Exception], () => e.evaluate(Context.empty)))
  }
}
