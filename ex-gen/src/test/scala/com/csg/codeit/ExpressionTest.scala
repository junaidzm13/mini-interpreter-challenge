package com.csg.codeit

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ExpressionTest {

  @Test
  def `puts with plain string literal`(): Unit = {
    val exp = PutsExpression(StringExpression("Simple"))

    assertThat(Expression.stringify(List(exp))).isEqualTo(List("(puts \"Simple\")"))
    assertThat(Expression.evaluate(List(exp))).isEqualTo(List("Simple"))
  }

  @Test
  def `puts with a number`(): Unit = {
    val exp = PutsExpression(StrExpression(DoubleExpression(10D)))

    assertThat(Expression.stringify(List(exp))).isEqualTo(List("(puts (str 10.0))"))
    assertThat(Expression.evaluate(List(exp))).isEqualTo(List("10.0"))
  }

  @Test
  def `puts errors when arg not a string`(): Unit = {
    val exp = PutsExpression(DoubleExpression(10D))

    assertThat(Expression.stringify(List(exp))).isEqualTo(List("(puts 10.0)"))
    assertThat(Expression.evaluate(List(exp))).isEqualTo(List("ERROR at line 1"))
  }

  @Test
  def `variable set and then printed`(): Unit = {
    val exp = List(
      SetExpression("a", BooleanExpression(true)),
      PutsExpression(StrExpression(VarExpression("a")))
    )

    assertThat(Expression.stringify(exp)).isEqualTo(List(
      "(set a true)",
      "(puts (str a))"
    ))
    assertThat(Expression.evaluate(exp)).isEqualTo(List("true"))
  }

  @Test
  def `errors when an undeclared variable used`(): Unit = {
    val exp = List(
      PutsExpression(StrExpression(VarExpression("a")))
    )

    assertThat(Expression.stringify(exp)).isEqualTo(List("(puts (str a))"))
    assertThat(Expression.evaluate(exp)).isEqualTo(List("ERROR at line 1"))
  }

  @Test
  def `equal on two puts statements should return true since returns null`(): Unit = {
    val exp = List(PutsExpression(StrExpression(EqualsExpression(
      PutsExpression(StringExpression("foo")),
      PutsExpression(StringExpression("bar")),
    ))))

    assertThat(Expression.evaluate(exp)).isEqualTo(List(
      "foo",
      "bar",
      "true"
    ))
  }
}
