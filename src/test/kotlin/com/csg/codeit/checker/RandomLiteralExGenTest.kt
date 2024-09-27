package com.csg.codeit.checker

import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class RandomLiteralExGenTest {

    @Test
    fun `int - can return random IntExpression`() {
        val expressions = (1..10).map { RandomLiteralExGen.int() }

        assertThat(expressions.map { it.value() }.distinct()).hasSizeGreaterThan(2)
    }

    @Test
    fun `double - can return random DoubleExpression`() {
        val values = (1..10).map { RandomLiteralExGen.double() }.map { it.value() }

        assertThat(values.distinct()).hasSizeGreaterThan(5)
        assertThat(values.map { it.toString().split(".")[1] } ).allMatch { it.length in 1..4 }
    }

    @Test
    fun `str - can return random StringExpression`() {
        val values = (1..10).map { RandomLiteralExGen.str(minLength = 10, maxLength = 100) }.map { it.value() }

        assertThat(values.distinct()).hasSize(10)
        assertThat(values).allMatch { it.length in 10..100 }
    }

    @Test
    fun `str - should not contain restricted characters`() {
        val restrictedChars = setOf('\\', '\'', '"')

        val values = (1..10).map { RandomLiteralExGen.str(minLength = 200, maxLength = 1000) }.map { it.value() }

        assertThat(values).allMatch { str -> restrictedChars.all { it !in str } }
    }

    @Test
    fun `str - can return fixed length StringExpression`() {
        val ex = RandomLiteralExGen.str(minLength = 10, maxLength = 10)

        assertThat(ex.value()).hasSize(10)
    }

    @Test
    fun `bool - can return random BooleanExpression`() {
        val expressions = (1..10).map { RandomLiteralExGen.bool() }

        assertThat(expressions.map { it.value() }.distinct()).hasSize(2)
    }
}