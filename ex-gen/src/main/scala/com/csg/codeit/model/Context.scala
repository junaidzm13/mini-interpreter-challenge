package com.csg.codeit.model

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

case class Context(
  variables: mutable.HashMap[String, Any],
  console: ListBuffer[String],
)

object Context {
  def empty: Context = Context(
    variables = mutable.HashMap.empty,
    console = ListBuffer.empty
  )
}
