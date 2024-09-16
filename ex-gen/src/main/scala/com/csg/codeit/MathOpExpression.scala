package com.csg.codeit

import com.csg.codeit.Expression.EvaluationError
import com.csg.codeit.model.Context
import com.csg.codeit.utils.isNumber

trait MathOpExpression extends Expression

// Var args math operations
case class AddExpression(private val args: Expression*) extends VarArgMathOpExpression(VarArgMathOp.ADD)(args: _*)
case class MultiplyExpression(private val args: Expression*) extends VarArgMathOpExpression(VarArgMathOp.MULTIPLY)(args: _*)
case class MaxExpression(private val args: Expression*) extends VarArgMathOpExpression(VarArgMathOp.MAX)(args: _*)
case class MinExpression(private val args: Expression*) extends VarArgMathOpExpression(VarArgMathOp.MIN)(args: _*)

abstract class VarArgMathOpExpression(val op: VarArgMathOp)(private val args: Expression*) extends MathOpExpression {
  override def evaluate(ctx: Context): Any = {
    if (args.length < 2) throw EvaluationError(s"Args to ${op.name} should be at least 2.")

    var allIntegers = true
    val numbers = args.map(_.evaluate(ctx) match {
      case i: Int => i.toDouble
      case d: Double => allIntegers = false; d
      case _ => throw EvaluationError(s"One of the args to `${op.name}` did not evaluate to a number.")
    })
    val res = op.apply(numbers)
    if (allIntegers) res.toInt else res
  }

  override def toString: String = s"(${op.name} ${args.mkString(" ")})"
}

sealed abstract class VarArgMathOp(val name: String, val apply: Seq[Double] => Double)
object VarArgMathOp {
  final case object ADD extends VarArgMathOp("add", _.sum)
  final case object MULTIPLY extends VarArgMathOp("multiply", _.product)
  final case object MAX extends VarArgMathOp("max", _.max)
  final case object MIN extends VarArgMathOp("min", _.min)
}


// 2 arg math operations
case class SubtractExpression(private val e1: Expression, private val e2: Expression) extends TwoArgMathOpExpression(TwoArgMathOp.SUBTRACT)(e1, e2)
case class DivideExpression(private val e1: Expression, private val e2: Expression) extends TwoArgMathOpExpression(TwoArgMathOp.DIVIDE)(e1, e2)

abstract class TwoArgMathOpExpression(val op: TwoArgMathOp)(private val a1: Expression, private val a2: Expression) extends MathOpExpression {
  override def evaluate(ctx: Context): Any = {
    (a1.evaluate(ctx), a2.evaluate(ctx)) match {
      case (i1: Int, i2: Int) => op.apply(i1, i2).toInt
      case (n1, n2) if isNumber(n1) && isNumber(n2) => op.apply(n1.toString.toDouble, n2.toString.toDouble)
      case _ => throw EvaluationError(s"One of the args to `${op.name}` did not evaluate to a number.")
    }
  }

  override def toString: String = s"(${op.name} $a1 $a2)"
}

sealed abstract class TwoArgMathOp(val name: String, val apply: (Double, Double) => Double)
object TwoArgMathOp {
  final case object SUBTRACT extends TwoArgMathOp("subtract", (a, b) => a - b)
  final case object DIVIDE extends TwoArgMathOp("divide", (a, b) => if (b == 0) throw new ArithmeticException("/ by zero") else a / b)
}

// 1 arg math operation
case class AbsExpression(private val e: Expression) extends MathOpExpression {
  override def evaluate(ctx: Context): Any = e.evaluate(ctx) match {
    case i: Int => Math.abs(i)
    case d: Double => Math.abs(d)
    case _ => throw EvaluationError(s"Arg to `abs` did not evaluate to a number.")
  }

  override def toString: String = s"(abs $e)"
}