package com.csg.codeit.checker

import com.csg.codeit.BooleanExpression
import com.csg.codeit.DoubleExpression
import com.csg.codeit.IntExpression
import com.csg.codeit.StringExpression
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

object RandomLiteralExGen {
    fun int(scale: Int = TEN_THOUSAND): IntExpression = Random.nextInt(-scale, scale).let(::IntExpression)

    fun double(scale: Int = TEN_THOUSAND): DoubleExpression = Random.nextDouble(-scale.toDouble(), scale.toDouble()).let{
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

    private const val TEN_THOUSAND: Int = 10_000
}