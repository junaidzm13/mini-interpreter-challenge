package com.csg.codeit.checker

import com.csg.codeit.BooleanExpression
import com.csg.codeit.DoubleExpression
import com.csg.codeit.IntExpression
import com.csg.codeit.StringExpression
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

object RandomLiteralExGen {
    fun int(): IntExpression = Random.nextInt(-ONE_MILLION, ONE_MILLION).let(::IntExpression)

    fun double(): DoubleExpression = Random.nextDouble(-ONE_MILLION.toDouble(), ONE_MILLION.toDouble()).let{
        BigDecimal.valueOf(it).setScale(4, RoundingMode.HALF_UP).toDouble()
    }.let(::DoubleExpression)

    fun bool(): BooleanExpression = BooleanExpression(Random.nextInt(0, 2) == 0)

    fun str(minLength: Int = 1, maxLength: Int = 200): StringExpression {
        val randomLength = Random.nextInt(minLength, maxLength + 1)
        return (0 until randomLength)
            .fold("") { acc, _ -> acc + randomCharacter() }
            .let(::StringExpression)
    }

    private fun randomCharacter(): Char {
        val nextChar: () -> Char = { Random.nextInt(48, 123).toChar() }
        val restrictedChars = setOf('\\', '\'', '"')

        var c = nextChar()
        while (c in restrictedChars) { c = nextChar() }

        return c
    }

    private const val ONE_MILLION: Int = 1_000_000
}