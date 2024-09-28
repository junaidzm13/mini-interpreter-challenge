package com.csg.codeit.parser

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ExpressionParserTest {

  @ParameterizedTest
  @ValueSource(strings = Array(
    "(puts (str (add 1 (multiply 1 2) 3)))",
    "(puts \"Hello, World!\")",
    "(puts (replace (concat \"abc\" \"def\") \"bc\" \"xyz\"))",
    "(puts \"ERROR at line 3\")",
    "(set a (add 10 5))",
    "(puts (str a))"
  ))
  def `can parse basic expression`(exp: String): Unit = {
    val parsedResult = ExpressionParser.parse(exp)

    assertThat(parsedResult.toString).isEqualTo(exp)
  }

}
