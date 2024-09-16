package com.csg.codeit

import com.csg.codeit.Expression.EvaluationError
import com.csg.codeit.model.Context

trait StringOpExpression extends Expression {
  override def evaluate(ctx: Context): String
}

case class ConcatExpression(private val e1: Expression, private val e2: Expression) extends StringOpExpression {
  override def evaluate(ctx: Context): String = (e1.evaluate(ctx), e2.evaluate(ctx)) match {
    case (s1: String, s2: String) => s1.concat(s2)
    case _ => throw EvaluationError("Both args to Concat should be strings.")
  }

  override def toString: String = s"(concat $e1 $e2)"
}

case class LowercaseExpression(private val e: Expression) extends StringOpExpression {
  override def evaluate(ctx: Context): String = e.evaluate(ctx) match {
    case s1: String => s1.toLowerCase
    case _ => throw EvaluationError("Argument to Lowercase should be a string.")
  }

  override def toString: String = s"(lowercase $e)"
}

case class UppercaseExpression(private val e: Expression) extends StringOpExpression {
  override def evaluate(ctx: Context): String = e.evaluate(ctx) match {
    case s1: String => s1.toUpperCase
    case _ => throw EvaluationError("Argument to Uppercase should be a string.")
  }

  override def toString: String = s"(uppercase $e)"
}

case class ReplaceExpression(
  private val source: Expression,
  private val target: Expression,
  private val replacement: Expression,
) extends StringOpExpression {
  override def evaluate(ctx: Context): String =
    (source.evaluate(ctx), target.evaluate(ctx), replacement.evaluate(ctx)) match {
      case (sourceStr: String, targetStr: String, replacementStr: String)  => sourceStr.replace(targetStr, replacementStr)
      case _ => throw EvaluationError("All args to Replace should be strings.")
    }

  override def toString: String = s"(replace $source $target $replacement)"
}

case class SubstringExpression(
  private val source: Expression,
  private val startIdx: Expression,
  private val endIdx: Expression
) extends StringOpExpression {
  override def evaluate(ctx: Context): String =
    (source.evaluate(ctx), startIdx.evaluate(ctx), endIdx.evaluate(ctx)) match {
      case (sourceStr: String, start: Int, end: Int)  => sourceStr.substring(start, end)
      case _ => throw EvaluationError("One of the args to Substring is of incorrect type - expected (String, Int, Int).")
    }

  override def toString: String = s"(substring $source $startIdx $endIdx)"
}

