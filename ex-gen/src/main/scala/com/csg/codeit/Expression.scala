package com.csg.codeit

import com.csg.codeit.Expression.EvaluationError
import com.csg.codeit.model.Context

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

trait Expression {
  def evaluate(ctx: Context): Any
}

object Expression {
  def stringify(expressions: List[Expression]): List[String] = expressions.map(_.toString)

  def evaluate(expressions: List[Expression], failFast: Boolean = false): List[String] = {
    evaluateRecurs(expressions, Context.empty, 0, failFast).toList
  }

  @tailrec
  private def evaluateRecurs(expressions: List[Expression], ctx: Context, idx: Int, failFast: Boolean): ListBuffer[String] = {
    if (idx == expressions.length) ctx.console
    else {
      Try(expressions(idx).evaluate(ctx)) match {
        case Success(_) => evaluateRecurs(expressions, ctx, idx + 1, failFast)
        case Failure(_) =>
          ctx.console.append(s"ERROR at line ${idx + 1}")
          if (failFast) ctx.console else evaluateRecurs(expressions, ctx, idx + 1, failFast)
      }
    }
  }

  case class EvaluationError(msg: String) extends RuntimeException(msg)
}

case class PutsExpression(e: Expression) extends Expression {
  override def evaluate(ctx: Context): Unit = {
    ctx.console.append(e.evaluate(ctx) match {
      case s: String => s
      case _ => throw EvaluationError("Argument to puts is not a String.")
    })
  }

  override def toString = s"(puts $e)"
}

case class SetExpression(variable: String, e: Expression) extends Expression {
  override def evaluate(ctx: Context): Unit = {
    val value = e.evaluate(ctx)
    ctx.variables.updateWith(variable)({
      case Some(_) => throw EvaluationError(s"Can't re-assign variable $variable.")
      case None    => Some(value)
    })
  }

  override def toString = s"(set $variable $e)"
}

case class VarExpression(name: String) extends Expression {
  override def evaluate(ctx: Context): Any = ctx.variables(name)
  override def toString: String = name
}

case class StrExpression(e: Expression) extends Expression {
  override def evaluate(ctx: Context): String = e.evaluate(ctx).toString

  override def toString = s"(str $e)"
}
