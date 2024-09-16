package com.csg.codeit

import com.csg.codeit.TestFixture.{assertAll, assertEqual, assertThrowsException}
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.{Nested, Test}

class ComparisonExpressionTest {

  @Nested
  class Equals {
    @Test
    def `can compare strings`(): Unit = {
      assertEqual(
        (EqualsExpression(StringExpression("foo"), StringExpression("foo")), true),
        (EqualsExpression(StringExpression("bar"), StringExpression("foo")), false),
        (EqualsExpression(StringExpression("Foo"), StringExpression("foo")), false),
      )
    }

    @Test
    def `can compare booleans`(): Unit = {
      assertEqual(
        (EqualsExpression(BooleanExpression(true), BooleanExpression(true)), true),
        (EqualsExpression(BooleanExpression(false), BooleanExpression(false)), true),
        (EqualsExpression(BooleanExpression(true), BooleanExpression(false)), false),
      )
    }

    @Test
    def `can compare numbers`(): Unit = {
      assertEqual(
        (EqualsExpression(IntExpression(5), IntExpression(5)), true),
        (EqualsExpression(IntExpression(10), DoubleExpression(10.0)), true),
        (EqualsExpression(DoubleExpression(10.55), DoubleExpression(10.550)), true),
        (EqualsExpression(IntExpression(10), IntExpression(-10)), false),
        (EqualsExpression(IntExpression(10), DoubleExpression(10.4)), false),
      )
    }

    @Test
    def `can compare nulls`(): Unit = {
      assertEqual(
        (EqualsExpression(NullExpression(), NullExpression()), true),
        (EqualsExpression(NullExpression(), BooleanExpression(false)), false),
        (EqualsExpression(NullExpression(), StringExpression("null")), false),
      )
    }

    @Test
    def `can stringify`(): Unit = {
      val e = EqualsExpression(IntExpression(5), DoubleExpression(5.5))
      assertThat(e.toString).isEqualTo("(equal 5 5.5)")
    }
  }

  @Nested
  class NotEquals {
    @Test
    def `can compare strings`(): Unit = {
      assertEqual(
        (NotEqualsExpression(StringExpression("foo"), StringExpression("foo")), false),
        (NotEqualsExpression(StringExpression("bar"), StringExpression("foo")), true),
        (NotEqualsExpression(StringExpression("Foo"), StringExpression("foo")), true),
      )
    }

    @Test
    def `can compare booleans`(): Unit = {
      assertEqual(
        (NotEqualsExpression(BooleanExpression(true), BooleanExpression(true)), false),
        (NotEqualsExpression(BooleanExpression(false), BooleanExpression(false)), false),
        (NotEqualsExpression(BooleanExpression(true), BooleanExpression(false)), true),
      )
    }

    @Test
    def `can compare numbers`(): Unit = {
      assertEqual(
        (NotEqualsExpression(IntExpression(5), IntExpression(5)), false),
        (NotEqualsExpression(IntExpression(10), DoubleExpression(10.0)), false),
        (NotEqualsExpression(DoubleExpression(10.55), DoubleExpression(10.550)), false),
        (NotEqualsExpression(IntExpression(10), IntExpression(-10)), true),
        (NotEqualsExpression(IntExpression(10), DoubleExpression(10.4)), true),
      )
    }

    @Test
    def `can compare nulls`(): Unit = {
      assertEqual(
        (NotEqualsExpression(NullExpression(), NullExpression()), false),
        (NotEqualsExpression(NullExpression(), BooleanExpression(false)), true),
        (NotEqualsExpression(NullExpression(), StringExpression("null")), true),
      )
    }

    @Test
    def `can stringify`(): Unit = {
      val e = NotEqualsExpression(IntExpression(5), DoubleExpression(5.5))
      assertThat(e.toString).isEqualTo("(non_equal 5 5.5)")
    }
  }

  @Nested
  class GreaterThan {
    @Test
    def `returns false when numbers equal`(): Unit = {
      assertAll(
        GtExpression(IntExpression(5), IntExpression(5)),
        GtExpression(IntExpression(10), DoubleExpression(10.0)),
        GtExpression(DoubleExpression(10.55), DoubleExpression(10.550))
      )(false)
    }

    @Test
    def `returns true when first number is greater than the second`(): Unit = {
      assertAll(
        GtExpression(IntExpression(6), IntExpression(5)),
        GtExpression(IntExpression(15), DoubleExpression(10.0)),
        GtExpression(DoubleExpression(10.56), DoubleExpression(10.55))
      )(true)
    }

    @Test
    def `returns false when first number is smaller than the second`(): Unit = {
      assertAll(
        GtExpression(IntExpression(4), IntExpression(5)),
        GtExpression(DoubleExpression(10.5), IntExpression(13)),
        GtExpression(DoubleExpression(10.50), DoubleExpression(10.55))
      )(false)
    }

    @Test
    def `throws when at least one of the arguments is not a number`(): Unit = {
      assertThrowsException(
        GtExpression(IntExpression(5), StringExpression("5")),
        GtExpression(BooleanExpression(true), IntExpression(5)),
        GtExpression(NullExpression(), IntExpression(5))
      )
    }

    @Test
    def `can stringify`(): Unit = {
      val e = GtExpression(IntExpression(5), DoubleExpression(5.5))
      assertThat(e.toString).isEqualTo("(gt 5 5.5)")
    }
  }

  @Nested
  class LowerThan {
    @Test
    def `returns false when numbers equal`(): Unit = {
      assertAll(
        LtExpression(IntExpression(5), IntExpression(5)),
        LtExpression(IntExpression(10), DoubleExpression(10.0)),
        LtExpression(DoubleExpression(10.55), DoubleExpression(10.550))
      )(false)
    }

    @Test
    def `returns false when first number is greater than the second`(): Unit = {
      assertAll(
        LtExpression(IntExpression(6), IntExpression(5)),
        LtExpression(IntExpression(15), DoubleExpression(10.0)),
        LtExpression(DoubleExpression(10.56), DoubleExpression(10.55))
      )(false)
    }

    @Test
    def `returns true when first number is smaller than the second`(): Unit = {
      assertAll(
        LtExpression(IntExpression(4), IntExpression(5)),
        LtExpression(DoubleExpression(10.5), IntExpression(13)),
        LtExpression(DoubleExpression(10.50), DoubleExpression(10.55))
      )(true)
    }

    @Test
    def `throws when at least one of the arguments is not a number`(): Unit = {
      assertThrowsException(
        LtExpression(IntExpression(5), StringExpression("5")),
        LtExpression(BooleanExpression(true), IntExpression(5)),
        LtExpression(NullExpression(), IntExpression(5))
      )
    }

    @Test
    def `can stringify`(): Unit = {
      val e = LtExpression(IntExpression(5), DoubleExpression(5.5))

      assertThat(e.toString).isEqualTo("(lt 5 5.5)")
    }
  }
}