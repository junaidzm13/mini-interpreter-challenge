package com.csg.codeit.checker

import com.csg.codeit.*
import com.csg.codeit.expression.ExpressionGeneratorUtils.ScalaUtils.toScalaSeq
import com.csg.codeit.expression.toTestCase
import com.csg.codeit.model.Output

fun easyTestCases(): List<EasyTestCase> = listOf(
    // concatenation
    EasyTestCase(
        expressions = listOf("(puts (concat \"MyParser\" \"IsBest\")))"),
        Output(results = listOf("MyParserIsBest"))
    ),

    EasyTestCase(
        expressions = listOf("(puts \"Hello World\"))"),
        Output(results = listOf("Hello World"))
    ),
    EasyTestCase(
        expressions = listOf("(puts (concat \"Hello\" \" World\")))"),
        Output(results = listOf("Hello World"))
    ),
    EasyTestCase(
        expressions = listOf(
            "(puts (uppercase \"hello\")))",
            "(puts (lowercase \"WORLD\")))"
        ),
        Output(results = listOf(
            "HELLO",
            "world"
        ))
    ),
    EasyTestCase(
        expressions = listOf("(puts (str (add 1 2)))"),
        Output(results = listOf("3"))
    ),
    EasyTestCase(
        expressions = listOf(
            "(set x 10)",
            "(puts (str x))"
        ),
        Output(results = listOf("10"))
    ),
    EasyTestCase(
        expressions = listOf("(puts (str (subtract 5 2)))"),
        Output(results = listOf("3"))
    ),
    EasyTestCase(
        expressions = listOf("(puts (str (min 3 1 4)))"),
        Output(results = listOf("1"))
    ),
    EasyTestCase(
        expressions = listOf(
            "(puts (str (equal 5 5)))",
            "(puts (str (equal 5 \"5\"))))"
        ),
        Output(results = listOf(
            "true",
            "false"
        ))
    ),
    // Basic string comparison
    EasyTestCase(
        expressions = listOf("(puts (str (equal \"10.0\" \"10\")))"),
        Output(results = listOf("false"))
    ),

    EasyTestCase(
        expressions = listOf("(puts (str (divide 10 2)))"),
        Output(results = listOf("5"))
    ),
    EasyTestCase(
        expressions = listOf("(puts (replace \"Hello World\" \"World\" \"There\")))"),
        Output(results = listOf("Hello There"))
    ),
    // Replacement of a single character
    EasyTestCase(
        expressions = listOf("(puts (replace \"hello\" \"l\" \"x\"))"),
        Output(results = listOf("hexxo"))
    ),
    // Replacing with an empty string (deletion)
    EasyTestCase(
        expressions = listOf("(puts (replace \"hello world\" \"world\" \"\"))"),
        Output(results = listOf("hello "))
    ),
    // Replacing substring that doesn't exist
    EasyTestCase(
        expressions = listOf("(puts (replace \"hello world\" \"planet\" \"earth\"))"),
        Output(results = listOf("hello world"))
    )
) + listOf<List<Expression>>(
    // subtraction
    listOf(PutsExpression(StrExpression(SubtractExpression(DoubleExpression(15.5), DoubleExpression(4.0))))),
    listOf(PutsExpression(StrExpression(SubtractExpression(IntExpression(1), IntExpression(2))))),

    // multiplication
    listOf(PutsExpression(StrExpression(MultiplyExpression(toScalaSeq(DoubleExpression(10.6), DoubleExpression(2.0), DoubleExpression(10.0)))))),
    listOf(PutsExpression(StrExpression(MultiplyExpression(toScalaSeq(IntExpression(3), DoubleExpression(-2.5)))))),

    // division
    listOf(PutsExpression(StrExpression(DivideExpression(DoubleExpression(25.5), DoubleExpression(-5.1))))),
    listOf(PutsExpression(StrExpression(DivideExpression(DoubleExpression(650.25), DoubleExpression(5.0))))),
    listOf(PutsExpression(StrExpression(DivideExpression(IntExpression(10), IntExpression(4))))),
    listOf(PutsExpression(StrExpression(DivideExpression(IntExpression(1), IntExpression(2))))),
).map { it.toTestCase(EasyTestCase::class) }

fun intermediateTestCases(): List<IntermediateTestCase> = listOf(
    IntermediateTestCase(
        expressions = listOf("(puts (str (add (subtract 10 5) 20)))"),
        Output(results = listOf("25"))
    ),
    IntermediateTestCase(
        expressions = listOf(
            "(puts (str (divide 10 2)))",
            "(puts (str (divide 5 0)))"
        ),
        Output(results = listOf(
            "5",
            "ERROR at line 2"
        ))
    ),
    IntermediateTestCase(
        expressions = listOf("(puts 5)"),
        Output(results = listOf("ERROR at line 1"))
    ),
    IntermediateTestCase(
        expressions = listOf(
            "(set x 15)",
            "(puts (str (subtract x 5)))"
        ),
        Output(results = listOf("10"))
    ),
    IntermediateTestCase(
        expressions = listOf("(puts (concat \"The result is: \" (str (add 5.0 5))))"),
        Output(results = listOf("The result is: 10.0"))
    ),
    IntermediateTestCase(
        expressions = listOf("(puts (substring \"abcdef\" 2 5))"),
        Output(results = listOf("cde"))
    ),
    IntermediateTestCase(
        expressions = listOf("(puts (substring \"abcdef\" 2 10))"),
        Output(results = listOf("ERROR at line 1"))
    ),
    IntermediateTestCase(
        expressions = listOf(
            "(set x 10)",
            "(set x 20)"
        ),
        Output(results = listOf("ERROR at line 2"))
    ),
    IntermediateTestCase(
        expressions = listOf(
            "(puts (str (gt 10 5)))",
            "(puts (str (lt 5 10)))",
            "(puts (str (gt 5 10)))"
        ),
        Output(results = listOf("true", "true", "false"))
    ),
    IntermediateTestCase(
        expressions = listOf("(puts (uppercase (concat \"hello\" (lowercase \"WORLD\")))))"),
        Output(results = listOf("HELLOWORLD"))
    ),
    IntermediateTestCase(
        expressions = listOf(
            "(puts (str (max 3 9 2 5)))",
            "(puts (str (min 3 9 2 5)))"
        ),
        Output(results = listOf(
            "9",
            "2"
        ))
    ),
    IntermediateTestCase(
        expressions = listOf(
            "(puts (str (equal true true)))",
            "(puts (str (equal null null)))",
            "(puts (str (not_equal null 0)))"
        ),
        Output(results = listOf("true", "true", "true"))
    ),
    IntermediateTestCase(
        expressions = listOf("(puts (replace (concat (uppercase \"hello\")) (lowercase \"WORLD\"))) \"LO\" \"XY\")))"),
        Output(results = listOf("HELXYWORLD"))
    ),
    IntermediateTestCase(
        expressions = listOf(
            "(puts (str (equal (add 5 5) 10)))",
            "(puts (str (gt (add 5 \"5\")) 10)))"
        ),
        Output(results = listOf(
            "true",
            "ERROR at line 2"
        ))
    ),
    IntermediateTestCase(
        expressions = listOf(
            "(puts \"ERROR at line 3\"))",
            "(set a (add 10 5))",
            "(puts (str a))"
        ),
        Output(results = listOf(
            "ERROR at line 3",
            "15"
        ))
    ),
    IntermediateTestCase(
        expressions = listOf("(puts (concat (str (add (subtract 20 10) (multiply 2 3)))) \" is the result\"))"),
        Output(results = listOf("16 is the result"))
    ),
    IntermediateTestCase(
        expressions = listOf(
            "(puts (substring \"abcdef\" 0 6))",
            "(puts (substring \"abcdef\" 2 10))"),
        Output(results = listOf(
            "abcdef",
            "ERROR at line 2"
        ))
    ),
    IntermediateTestCase(
        expressions = listOf("(puts (str (add (subtract (multiply (divide 100 2) (add 10 5)) 25) 10)))"),
        Output(results = listOf("735"))
    ),
    // Overlapping substrings
    IntermediateTestCase(
        expressions = listOf("(puts (replace \"abababab\" \"ab\" \"xy\"))"),
        Output(results = listOf("xyxyxyxy"))
    ),
    // Replacing with null
    IntermediateTestCase(
        expressions = listOf("(puts (replace \"test string\" null \"null\"))"),
        Output(results = listOf("ERROR at line 1"))
    ),
    // Replace with a combination of replace and concat
    IntermediateTestCase(
        expressions = listOf("(puts (replace (concat \"abc\" \"def\") \"bc\" \"xyz\"))"),
        Output(results = listOf("axyzdef"))
    ),
    // Replacing part of a string and nesting it with an arithmetic operation
    IntermediateTestCase(
        expressions = listOf("(puts (replace (concat (str (add 100 200)) \" number\") \"300\" \"Three Hundred\"))"),
        Output(results = listOf("Three Hundred number"))
    ),

    // Comparing boolean strings with capital letters
    IntermediateTestCase(
        expressions = listOf("(puts (str (equal \"True\" \"true\")))"),
        Output(results = listOf("false"))
    ),

) + listOf<List<Expression>>(
    // Different cases for division
    listOf(
        PutsExpression(StrExpression(DivideExpression(IntExpression(10), IntExpression(2)))),
        PutsExpression(StrExpression(DivideExpression(IntExpression(10), DoubleExpression(2.0)))),
        PutsExpression(StrExpression(DivideExpression(DoubleExpression(5.5), DoubleExpression(2.0)))),
        PutsExpression(StrExpression(DivideExpression(IntExpression(5), IntExpression(0)))),
    ).shuffled()
).map { it.toTestCase(IntermediateTestCase::class) }

fun hardTestCases(): List<HardTestCase> = listOf(
    HardTestCase(
        expressions = listOf(
            "(set x 10)",
            "(puts (str (add x 5)))",
            "(puts (str (divide x 0)))",
            "(puts \"This line should not be printed\"))"
        ),
        Output(results = listOf(
            "15",
            "ERROR at line 3"
        ))
    ),
    HardTestCase(
        expressions = listOf(
            "(puts (str (divide 10 2)))",
            "(puts (str (divide 10 0)))",
            "(puts (add 5 \"5\")))",
            "(puts (subtract 5))"
        ),
        Output(results = listOf(
            "5",
            "ERROR at line 2"
        ))
    ),
    HardTestCase(
        expressions = listOf(
            "(set x 50)",
            "(puts (str (add x 10)))",
            "(puts (substring \"hello\" 1 3))",
            "(set x 100)"
        ),
        Output(results = listOf(
            "60",
            "el",
            "ERROR at line 4"
        ))
    ),
    HardTestCase(
        expressions = listOf(
            "(puts (str (not_equal (abs (subtract 10 20)) 10)))",
            "(puts (str (gt (multiply 2 3 4) 20)))",
            "(puts (str (lt (divide 100 5) (multiply 3 3))))"
        ),
        Output(results = listOf("false", "true", "false"))
    ),
    HardTestCase(
        expressions = listOf(
            "(set x (concat (uppercase \"harder\")) (lowercase \"TEST\"))))",
            "(set y (replace x \"ER\" \"XY\")))",
            "(set z (substring y 0 8))",
            "(puts (str z))",
            "(puts (concat z \"ing\")))",
            "(set errorTest (concat x 123))"
        ),
        Output(results = listOf(
            "HARDXYTE",
            "HARDXYTEing",
            "ERROR at line 6"
        ))
    ),
    HardTestCase(
        expressions = listOf(
            "(set a 100)",
            "(set b (divide a 5))", // 20
            "(set c (subtract b (multiply 2 10)))", // 20 - 20 = 0
            "(puts (str c))", // 0
            "(set d (add c (divide 10 0)))", // division by 0 error
            "(puts (str d))",
            "(set e (add d 50))",
            "(puts (str e))"
        ),
        Output(results = listOf(
            "0",
            "ERROR at line 5"
        ))
    ),
    HardTestCase(
        expressions = listOf(
            "(set x (add 10 20))", // 30
            "(set y (multiply x 2))", // 60
            "(set z (subtract y (divide 100 10)))", // 50
            "(set result (concat \"Result is: \" (str z)))",
            "(puts result)",
            "(set invalidOperation (add result 10))",
            "(puts (str invalidOperation))"
        ),
        Output(results = listOf(
            "Result is: 50",
            "ERROR at line 6"
        ))
    ),
    HardTestCase(
        expressions = listOf(
            "(set a (add 100 (multiply (subtract 200 (divide 100 5)) (add 10 (max 1 2 3)))))",
            "(set b (concat (uppercase (str (subtract a (divide 100 0)))) \" TEST\")))",
            "(set c (subtract (multiply (add 50 50) (max (subtract 20 5) 10)) (min 30 40)))",
            "(set d (replace b \"100\" (str c)))",
            "(puts (str c))",
            "(puts (lowercase (concat d \" ERROR?\"))))",
            "(set e (divide c (multiply (subtract c (divide 100 5)) 0)))",
            "(puts (str e))"
        ),
        Output(results = listOf("ERROR at line 2"))
    ),
    HardTestCase(
        expressions = listOf(
            "(set x (str (add 100 (subtract 200 (multiply 3 (divide 300 10))))))",
            "(set y (concat (lowercase x) \" complex\")))",
            "(set z (replace y \"300\" (str (divide 900 3))))",
            "(set a (max (min (subtract 1000 200) (multiply 2 3)) (divide 1000 10)))",
            "(set b (subtract a (add (multiply 2 3) (divide (add (multiply 100 2) (subtract 200 100)) 5))))",
            "(puts (str (gt a b)))",
            "(puts (concat \"Result: \" (uppercase z)))",
            "(set invalidOp (add z a))", // add string and number, should be error at this line (8)
            "(puts (str invalidOp))",
            "(set finalResult (divide invalidOp (min (abs b) 0)))",
            "(puts (str finalResult))"
        ),
        Output(results = listOf("true", "Result: 1000 complex", "ERROR at line 8"))
    ),
    HardTestCase(
        expressions = listOf(
            "(set a (add 100 (subtract 200 (divide 300 10))))",
            "(set b (concat (uppercase (str a)) \" Complex\")))",
            "(set c (replace b \"300\" (str (divide (add (multiply 2 3) (subtract 500 100)) 5))))",
            "(set d (multiply (subtract (max 100 200) (min 10 20)) (abs (subtract 500 (divide 100 0)))))",
            "(puts (str (equal (subtract d (multiply a 2)) 1000)))",
            "(set e (concat (lowercase (str d)) \" Nightmare\")))",
            "(puts e)",
            "(set f (add (str d) (uppercase e)))",
            "(puts (str f))",
            "(set g (divide (subtract f a) (add (multiply (subtract 100 50) (divide 200 0)) 10)))",
            "(puts (str g))"
        ),
        Output(results = listOf("ERROR at line 4"))
    ),
    HardTestCase(
        expressions = listOf(
            "(set a (add 100 (subtract 200 (divide 300 10))))", // 270
            "(set b (concat (uppercase (str a)) \" Complex\")))", // 270 Complex
            "(set c (replace b \"300\" (str (divide (add (multiply 2 5) (subtract 500 100)) 5))))", // 270 Complex
            "(set d (multiply (subtract (max 100 200) (min 10 20)) (abs (subtract 500 (divide 100 10)))))", // 190 * 490 = 93_100
            "(puts (str (equal (subtract d (multiply a 2)) 1000)))", // 92560 == 1000 => false
            "(set e (concat (lowercase (str d)) \" Nightmare\")))", // e := "93100 Nightmare"
            "(puts e)",
            "(set f (add (str d) (uppercase e)))",
            "(puts (str f))",
            "(set g (divide (subtract f a) (add (multiply (subtract 100 50) (divide 200 0)) 10)))",
            "(puts (str g))"
        ),
        Output(results = listOf("false", "93100 Nightmare", "ERROR at line 8"))
    ),
    HardTestCase(
        expressions = listOf(
            "(set x (add 10 20))",
            "(set y (multiply x 2))",
            "(set z (subtract y (divide 100 10)))",
            "(set result (concat \"Result is: \" (str z)))",
            "(puts (str (lowercase (str result))))",
            "(set output (replace (concat (uppercase (lowercase result)) \".75\") (str 0) (str 5)))", // output := "RESULT IS: 55.75"
            "(puts (str (uppercase (uppercase (uppercase output)))))"
        ),
        Output(results = listOf("result is: 50", "RESULT IS: 55.75"))
    ),
    // Deeply nested string comparison with numbers
    HardTestCase(
        expressions = listOf(
            "(set x (str (add 10 20)))", // x = "30"
            "(set y (str (divide 60 2)))", // y = "30"
            "(set z (str (multiply 5 6)))", // z = "30"
            "(puts (str (equal x y)))", // true
            "(puts (str (equal y z)))", // true
            "(puts (str (equal z \"30.0\")))", // false
            "(puts (str (equal z (concat \"3\" \"0\"))))" // true
        ),
        Output(results = listOf("true", "true", "false", "true"))
    ),
    // Complex boolean and string comparison
    HardTestCase(
        expressions = listOf(
            "(set a (equal true false))", // a = false
            "(set b (equal false false))", // b = true
            "(puts (str (equal (str a) (str b))))", // false
            "(set c (concat \"True\" \"False\"))", // c = "TrueFalse"
            "(puts (str (equal c \"TrueFalse\")))" // true
        ),
        Output(results = listOf("false", "true"))
    ),
    // Multiple string and number manipulations with set, concat, and equal
    HardTestCase(
        expressions = listOf(
            "(set x (str (multiply 10 10)))", // x = "100"
            "(set y (str (divide 200 2)))", // y = "100"
            "(set z (concat x y))", // z = "100100"
            "(puts (str (equal z \"100100\")))", // true
            "(puts (str (equal z (concat x \"100\"))))", // true
            "(puts (str (equal z \"1000100\")))" // false
        ),
        Output(results = listOf("true", "true", "false"))
    ),
    // Using set, substring, and equal to compare partial strings
    HardTestCase(
        expressions = listOf(
            "(set str1 \"substring test\")",
            "(set part (substring str1 0 9))", // part = "substring"
            "(puts (str (equal part \"substring\")))", // true
            "(set invalidPart (substring str1 10 20))", // invalid range, should cause error
            "(puts (str (equal invalidPart \"test\")))"
        ),
        Output(results = listOf("true", "ERROR at line 4"))
    ),
    // Comparison involving null values and error propagation
    HardTestCase(
        expressions = listOf(
            "(set a null)",
            "(set b (concat \"null\" \" value\"))", // b = "null value"
            "(puts (str (equal a b)))", // false
            "(set c (equal null a))", // c = true
            "(puts (str (equal (str c) \"true\")))", // true
            "(puts (str (equal a \"null\")))" // false
        ),
        Output(results = listOf("false", "true", "false"))
    ),

    HardTestCase(
        expressions = listOf(
            "(puts (str (add (multiply 2 (subtract 10 (divide 20 2))) (subtract 5 2))))",
            "(puts (str (subtract (add (multiply 2 3) (divide 10 2)) (divide 5 1))))"
        ),
        Output(results = listOf("3", "6"))
    ),

    HardTestCase(
        expressions = listOf(
            "(set x (add (multiply 2 3) (subtract 10 (divide 20 2))))", // x = 6
            "(set y (divide (add x 10) 2))", // y = 8
            "(puts (str (multiply y (subtract 50 (divide x 2)))))" // 8 * 47 = 376
        ),
        Output(results = listOf("376"))
    ),
    HardTestCase(
        expressions = listOf(
            "(puts (concat \"Result: \" (str (add (multiply 3 4) (divide 20 (subtract 10 5))))))",
            "(puts (str (replace \"NestedCalls\" \"Calls\" (concat \"Works\" (str 1)))))"
        ),
        Output(results = listOf("Result: 16", "NestedWorks1"))
    ),

    HardTestCase(
        expressions = listOf(
            "(set x (str (multiply (add 10 (subtract 20 5)) (divide 100 10))))", // x = 250
            "(puts (replace x (str (divide 250 10)) (str (multiply 2 3))))", // replace "25" with "6"
            "(puts (concat (uppercase x) \" TEST\"))" // "250 TEST"
        ),
        Output(results = listOf("60", "150 TEST"))
    ),
    HardTestCase(
        expressions = listOf(
            "(set a (concat (str (add 10 20)) (uppercase (replace \"test\" \"t\" \"b\"))))", // a = "30BESB"
            "(puts (substring a (subtract (multiply 2 3) 4) (add 3 3)))", // substring of last 4 characters
            "(puts (str (not_equal a 30)))" // true
        ),
        Output(results = listOf("BESB", "true"))
    ),
    HardTestCase(
        expressions = listOf(
            "(set x (divide (add 100 (subtract (multiply 2 5) 10)) (multiply 2 5)))", // x = 10
            "(puts (concat (str (equal x 10)) \" isEqual\"))", // "true isEqual"
            "(puts (str (add x (replace (str 10) \"10\" \"5\"))))" // should raise error
        ),
        Output(results = listOf("true isEqual", "ERROR at line 3"))
    ),

    HardTestCase(
        expressions = listOf(
            "(set x (add 10 (multiply 2 5)))", // x = 20
            "(set y (subtract x (divide (multiply 2 10) (add 5 5))))", // y = 18
            "(puts (str (add (multiply x y) (divide 100 5))))", // 20 * 18 + 20 = 380
            "(puts (concat (uppercase (str y)) \" FINAL\"))" // 18 FINAL
        ),
        Output(results = listOf("380", "18 FINAL"))
    ),
    HardTestCase(
        expressions = listOf(
            "(set a (add 10 (multiply (subtract 20 (divide 30 3)) (add 5 (max 1 2)))))", // a = 80
            "(set b (concat (lowercase (str a)) \" Test\"))", // "80 test"
            "(puts (replace b \"80\" (str (add 10 (subtract 20 5)))))", // replace "80" with "25"
            "(puts (str (equal a 80)))", // true
            "(puts (concat b \" Complete\"))" // "80 test Complete"
        ),
        Output(results = listOf("25 test", "true", "80 test Complete"))
    )
)
