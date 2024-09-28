package com.csg.codeit.parser

import com.csg.codeit._

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

object ExpressionParser {

  def parse(ex: String): Expression = parserRecurs(ex)

  private def parserRecurs(ex: String): Expression = {
    val openParenthesisIdx = ex.indexOf('(')
    val closeParenthesisIdx = ex.lastIndexOf(')')

    if (openParenthesisIdx == -1 && closeParenthesisIdx == -1) {
      createNonFnExpression(ex)
    } else {
      val fn :: vs = splitAtOuterWhiteSpace(ex.substring(openParenthesisIdx + 1, closeParenthesisIdx)) //.split(" ")
      if (fn == "set") {
        createSetExpression(vs.head, vs.tail.map(parserRecurs))
      } else {
        createFnExpression(fn, vs.map(parserRecurs))
      }
    }
  }

  private def splitAtOuterWhiteSpace(s: String): List[String] = {

    @tailrec
    def split(currIdx: Int,
              prevWsIdx: Int,
              res: ListBuffer[String],
              parenthesisDiff: Int,
              doubleQuotesCount: Int): ListBuffer[String] = {
      if (currIdx == s.length) res.appended(s.substring(prevWsIdx + 1, currIdx))
      else s(currIdx) match {
        case ' ' if parenthesisDiff == 0 && doubleQuotesCount % 2 == 0 =>
          val newRes = res.appended(s.substring(prevWsIdx + 1, currIdx))
          split(currIdx + 1, currIdx, newRes, parenthesisDiff, doubleQuotesCount)
        case '(' => split(currIdx + 1, prevWsIdx, res, parenthesisDiff + 1, doubleQuotesCount)
        case ')' => split(currIdx + 1, prevWsIdx, res, parenthesisDiff - 1, doubleQuotesCount)
        case '\"' => split(currIdx + 1, prevWsIdx, res, parenthesisDiff, doubleQuotesCount + 1)
        case _   => split(currIdx + 1, prevWsIdx, res, parenthesisDiff, doubleQuotesCount)
      }
    }

    split(0, -1, ListBuffer(), 0, 0).toList
  }

  private def createFnExpression(fn: String, exs: List[Expression]): Expression = {
    fn match {
      case "puts"      if hasSizeOf(exs, 1) => PutsExpression(exs.head)
      case "str"       if hasSizeOf(exs, 1) => StrExpression(exs.head)
      case "concat"    if hasSizeOf(exs, 2) => ConcatExpression(exs.head, exs.tail.head)
      case "uppercase" if hasSizeOf(exs, 1) => UppercaseExpression(exs.head)
      case "lowercase" if hasSizeOf(exs, 1) => LowercaseExpression(exs.head)
      case "substring" if hasSizeOf(exs, 3) => val a :: b :: c :: Nil = exs; SubstringExpression(a, b, c)
      case "replace"   if hasSizeOf(exs, 3) => val a :: b :: c :: Nil = exs; ReplaceExpression(a, b, c)
      case "equal"     if hasSizeOf(exs, 2) => EqualsExpression(exs.head, exs.tail.head)
      case "not_equal" if hasSizeOf(exs, 2) => NotEqualsExpression(exs.head, exs.tail.head)
      case "gt"        if hasSizeOf(exs, 2) => GtExpression(exs.head, exs.tail.head)
      case "lt"        if hasSizeOf(exs, 2) => LtExpression(exs.head, exs.tail.head)
      case "abs"       if hasSizeOf(exs, 1) => AbsExpression(exs.head)
      case "add"                            => AddExpression(exs:_*)
      case "multiply"                       => MultiplyExpression(exs:_*)
      case "max"                            => MaxExpression(exs:_*)
      case "min"                            => MinExpression(exs:_*)
      case "subtract"  if hasSizeOf(exs, 2) => SubtractExpression(exs.head, exs.tail.head)
      case "divide"    if hasSizeOf(exs, 2) => DivideExpression(exs.head, exs.tail.head)
      case _ => throw new Exception(s"Parsing error: no fn $fn which accepts ${exs.size} arguments found.")
    }
  }

  private def createSetExpression(variableName: String, exs: List[Expression]): SetExpression = exs match {
    case head :: Nil if isValidVariableName(variableName) => SetExpression(variableName, head)
    case _ => throw new Exception("Parsing error: `set` should only have two args.")
  }

  private def hasSizeOf(exs: List[Expression], size: Int): Boolean = exs.size == size

  private def createNonFnExpression(s: String): Expression = {
    Iterator(
      toNumberExpression(_),
      toBooleanExpression(_),
      toNullExpression(_),
      toStringExpression(_),
      toVarExpression(_)
    ).map(_(s))
      .find(_.nonEmpty)
      .getOrElse(throw new Exception("Error with parsing!"))
      .get
  }

  private def toNumberExpression(s: String): Option[LiteralExpression[Any]] = {
    s.toDoubleOption match {
      case Some(d) if s.contains('.') => Some(DoubleExpression(d))
      case Some(d) => Some(IntExpression(d.toInt))
      case None => None
    }
  }

  private def toBooleanExpression(s: String): Option[BooleanExpression] = {
    s.toBooleanOption match {
      case Some(b) => Some(BooleanExpression(b))
      case None => None
    }
  }

  private def toNullExpression(s: String): Option[NullExpression] = {
    if (s == "null") Some(NullExpression()) else None
  }

  private def toStringExpression(s: String): Option[StringExpression] = {
    if (s.startsWith("\"") && s.endsWith("\"")) Some(StringExpression(s.substring(1, s.length - 1)))
    else None
  }

  private def toVarExpression(s: String): Option[VarExpression] = {
    if (isValidVariableName(s)) Some(VarExpression(s)) else None
  }

  private def isValidVariableName(s: String): Boolean = {
    s.zipWithIndex.forall({ case (c, i) => c.isLetter || (i != 0 && c.isDigit) })
  }
}
