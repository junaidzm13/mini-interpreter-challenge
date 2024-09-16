package com.csg.codeit.expression

import com.csg.codeit.Expression
import com.csg.codeit.`Expression$`
import scala.jdk.javaapi.CollectionConverters

object ExpressionGeneratorUtils {
    fun runEvaluate(es: List<Expression>): List<String> {
        return ScalaUtils.toKotlin(`Expression$`.`MODULE$`.evaluate(ScalaUtils.toScala(es), true))
    }

    fun runStringify(es: List<Expression>): List<String> {
        return ScalaUtils.toKotlin(`Expression$`.`MODULE$`.stringify(ScalaUtils.toScala(es)))
    }

    object ScalaUtils {
        fun <T> toScalaSeq(l: Iterable<T>): scala.collection.immutable.Seq<T> {
            return CollectionConverters.asScala(l).toSeq()
        }

        fun <T> toScala(l: List<T>): scala.collection.immutable.List<T> {
            return CollectionConverters.asScala(l).toList()
        }

        fun <T> toKotlin(l: scala.collection.immutable.List<T>): List<T> {
            return CollectionConverters.asJava(l).toList()
        }
    }
}