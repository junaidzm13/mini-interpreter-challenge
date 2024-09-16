package com.csg.codeit

import com.csg.codeit.model.Context
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.{Nested, Test}

class LiteralExpressionTest {

  @Nested
  class String {
    @Test
    def `toString returns string with double quotes`(): Unit = {
      assertThat(StringExpression("str").toString).isEqualTo("\"str\"")
    }
  }
  @Nested
  class Null {
    @Test
    def `toString returns 'null'`(): Unit = {
      assertThat(NullExpression().toString).isEqualTo("null")
    }

    @Test
    def `evaluate returns value of type Unit`(): Unit = {
      assertThat(NullExpression().evaluate(Context.empty).isInstanceOf[Unit]).isTrue
    }
  }
}
