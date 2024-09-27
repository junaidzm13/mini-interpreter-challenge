package com.csg.codeit

import com.csg.codeit.Expression.EvaluationError
import com.csg.codeit.model.Context
import com.csg.codeit.utils.isNumber

trait ComparisonExpression extends Expression {
  override def evaluate(ctx: Context): Boolean
}

case class EqualsExpression(private val e1: Expression, private val e2: Expression) extends ComparisonExpression {
  override def evaluate(ctx: Context): Boolean =
    (e1.evaluate(ctx), e2.evaluate(ctx)) match {
      case (_: Unit, _: Unit) => true
      case (a, b)  => a == b
    }

  override def toString: String = s"(equal $e1 $e2)"
}

case class NotEqualsExpression(private val e1: Expression, private val e2: Expression) extends ComparisonExpression {
  override def evaluate(ctx: Context): Boolean =
    (e1.evaluate(ctx), e2.evaluate(ctx)) match {
      case (_: Unit, _: Unit) => false
      case (a, b)  => a != b
    }

  override def toString: String = s"(not_equal $e1 $e2)"
}

case class GtExpression(private val e1: Expression, private val e2: Expression) extends RangeOpExpression(RangeOp.GT)(e1, e2)
case class LtExpression(private val e1: Expression, private val e2: Expression) extends RangeOpExpression(RangeOp.LT)(e1, e2)

abstract class RangeOpExpression(val op: RangeOp)(private val e1: Expression, private val e2: Expression) extends ComparisonExpression {
  override def evaluate(ctx: Context): Boolean = (e1.evaluate(ctx), e2.evaluate(ctx)) match {
    case (n1, n2) if isNumber(n1) && isNumber(n2) => op.apply(n1.toString.toDouble, n2.toString.toDouble)
    case _ => throw EvaluationError(s"One of the args to `${op.name}` did not evaluate to a number.")
  }

  override def toString: String = s"(${op.name} $e1 $e2)"
}

sealed abstract class RangeOp(val name: String, val apply: (Double, Double) => Boolean)
object RangeOp {
  final case object LT extends RangeOp("lt", (a, b) => a < b)
  final case object GT extends RangeOp("gt", (a, b) => a > b)
}
