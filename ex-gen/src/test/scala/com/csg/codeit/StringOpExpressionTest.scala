package com.csg.codeit

import com.csg.codeit.TestFixture.{assertEqual, assertThrowsException}
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.{Nested, Test}

class StringOpExpressionTest {

  @Nested
  class Concat {
    @Test
    def `can concat two strings`(): Unit = {
      assertEqual((
        ConcatExpression(StringExpression("Junaid"), StringExpression(" Malik")),
        "Junaid Malik"
      ))
    }

    @Test
    def `can correctly stringify`(): Unit = {
      val e = ConcatExpression(StringExpression("Junaid"), StringExpression(" Malik"))

      assertThat(e.toString).isEqualTo("(concat \"Junaid\" \" Malik\")")
    }

    @Test
    def `throws when one of the args is not a string`(): Unit = {
      assertThrowsException(
        ConcatExpression(BooleanExpression(true), StringExpression("foo")),
        ConcatExpression(StringExpression("foo"), IntExpression(2)),
        ConcatExpression(IntExpression(55), BooleanExpression(true))
      )
    }
  }

  @Nested
  class UpperCase {
    @Test
    def `can uppercase string`(): Unit = {
      assertEqual((UppercaseExpression(StringExpression("foo-Bar-123")), "FOO-BAR-123"))
    }

    @Test
    def `can correctly stringify`(): Unit = {
      val e = UppercaseExpression(StringExpression("Junaid"))

      assertThat(e.toString).isEqualTo("(uppercase \"Junaid\")")
    }

    @Test
    def `throws when arg is not a string`(): Unit = {
      assertThrowsException(
        UppercaseExpression(BooleanExpression(true)),
        UppercaseExpression(NullExpression()),
        UppercaseExpression(IntExpression(55))
      )
    }
  }

  @Nested
  class LowerCase {
    @Test
    def `can lowercase string`(): Unit = {
      assertEqual((LowercaseExpression(StringExpression("FoO-BAr-123")), "foo-bar-123"))
    }

    @Test
    def `can correctly stringify`(): Unit = {
      val e = LowercaseExpression(StringExpression("Junaid"))

      assertThat(e.toString).isEqualTo("(lowercase \"Junaid\")")
    }

    @Test
    def `throws when arg is not a string`(): Unit = {
      assertThrowsException(
        LowercaseExpression(BooleanExpression(true)),
        LowercaseExpression(NullExpression()),
        LowercaseExpression(IntExpression(55))
      )
    }
  }

  @Nested
  class Replace {
    @Test
    def `can replace substring from a source string`(): Unit = {
      assertEqual((
        ReplaceExpression(
          StringExpression("FoO-BAr-123"),
          StringExpression("O-BA"),
          StringExpression("o-ca")
        ),
        "Foo-car-123"
      ))
    }

    @Test
    def `can replace more than one occurrences`(): Unit = {
      assertEqual((
        ReplaceExpression(
          StringExpression("FooBarCar"),
          StringExpression("ar"),
          StringExpression("at")
        ),
        "FooBatCat"
      ))
    }

    @Test
    def `returns string unchanged when target substring not found`(): Unit = {
      assertEqual((
        ReplaceExpression(
          StringExpression("foo-bar"),
          StringExpression("BA"),
          StringExpression("CA")
        ),
        "foo-bar"
      ))
    }

    @Test
    def `throws when any of the args is not a string`(): Unit = {
      assertThrowsException(
        ReplaceExpression(StringExpression("foo"), StringExpression("o"), IntExpression(0)),
        ReplaceExpression(StringExpression("foo"), BooleanExpression(false), StringExpression("0")),
        ReplaceExpression(BooleanExpression(true), StringExpression("true"), StringExpression("false")),
      )
    }

    @Test
    def `can correctly stringify`(): Unit = {
      val e = ReplaceExpression(StringExpression("Junaid"), StringExpression("aid"), StringExpression("u"))

      assertThat(e.toString).isEqualTo("(replace \"Junaid\" \"aid\" \"u\")")
    }
  }

  @Nested
  class Substring {
    private val string = StringExpression("foo-bar-zoo")

    @Test
    def `can get substring at specified indexes`(): Unit = {
      assertEqual((SubstringExpression(string, IntExpression(0), IntExpression(3)), "foo"))
    }

    @Test
    def `can get substring from the last index`(): Unit = {
      assertEqual((SubstringExpression(string, IntExpression(8), IntExpression(11)), "zoo"))
    }

    @Test
    def `can correctly stringify`(): Unit = {
      val e = SubstringExpression(string, IntExpression(0), IntExpression(10))

      assertThat(e.toString).isEqualTo(s"(substring $string 0 10)")
    }

    @Test
    def `throws when any index is out of bounds`(): Unit = {
      assertThrowsException(
        SubstringExpression(string, IntExpression(11), IntExpression(15)),
        SubstringExpression(string, IntExpression(-1), IntExpression(10)),
        SubstringExpression(string, IntExpression(5), IntExpression(12)),
      )
    }
  }
}
