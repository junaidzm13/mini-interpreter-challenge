package com.csg.codeit

import com.csg.codeit.model.Context

abstract class LiteralExpression[+T](value: T) extends Expression {
  override def evaluate(ctx: Context): T = value
  override def toString: String = value.toString
}
case class IntExpression(value: Int) extends LiteralExpression(value)
case class DoubleExpression(value: Double) extends LiteralExpression(value)
case class BooleanExpression(value: Boolean) extends LiteralExpression(value)
case class StringExpression(value: String) extends LiteralExpression(value) {
  override def toString: String = s"\"$value\""
}
case class NullExpression() extends LiteralExpression(()) {
  override def toString: String = "null"
}