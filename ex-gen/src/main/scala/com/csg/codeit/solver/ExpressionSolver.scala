package com.csg.codeit.solver

import com.csg.codeit.model.Context
import com.csg.codeit.parser.ExpressionParser

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

object ExpressionSolver {

  def solve(expressions: List[String]): List[String] = {
    solveRecurs(expressions, Context.empty, 0).toList
  }

  @tailrec
  private def solveRecurs(expStrings: List[String], ctx: Context, idx: Int): ListBuffer[String] = {
    if (idx == expStrings.length) ctx.console
    else {
      Try(ExpressionParser.parse(expStrings(idx)).evaluate(ctx)) match {
        case Success(_) => solveRecurs(expStrings, ctx, idx + 1)
        case Failure(_) => ctx.console.append(s"ERROR at line ${idx + 1}"); ctx.console
      }
    }
  }

}
