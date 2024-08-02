package com.csg.codeit.checker

import com.csg.codeit.model.Output

val easyTestCases: List<EasyTestCase> = listOf(

    // subtraction
    EasyTestCase(expressions = listOf("(puts (subtract, 15.5, 4.0))"), Output(results = listOf("11.5"))),
    EasyTestCase(expressions = listOf("(puts (subtract, 1, 2))"), Output(results = listOf("-1"))),

    // multiplication
    EasyTestCase(expressions = listOf("(puts (multiply, 10.6, 2.0, 10.0))"), Output(results = listOf("212.0"))),
    EasyTestCase(expressions = listOf("(puts (multiply, 3.0, -2.5))"), Output(results = listOf("-7.5"))),

    // division
    EasyTestCase(expressions = listOf("(puts (divide, 25.5, -5.1))"), Output(results = listOf("-5.0"))),
    EasyTestCase(expressions = listOf("(puts (divide, 650.25, 5.0, 5.0))"), Output(results = listOf("26.01"))),

    // concatenation
    EasyTestCase(
        expressions = listOf("(puts (concat, \"MyParser\", \"Is\", \"Best\")))"),
        Output(results = listOf("MyParserIsBest"))
    ),

    EasyTestCase(
        expressions = listOf("(puts \"Hello World\"))"),
        Output(results = listOf("Hello World"))
    ),
    EasyTestCase(
        expressions = listOf("(puts (concat \"Hello\" \" World\")))"),
        Output(results = listOf("Hello world"))
    ),
    EasyTestCase(
        expressions = listOf("(puts (uppercase \"hello\")))", "(puts (lowercase \"WORLD\")))"),
        Output(results = listOf("HELLO", "world"))
    ),
    EasyTestCase(
        expressions = listOf("(puts (str (add 1 2)))"),
        Output(results = listOf("3"))
    ),
    EasyTestCase(
        expressions = listOf("(set x 10)", "(puts (str x))"),
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
        expressions = listOf("(puts (str (equal 5 5)))", "(puts (str (equal 5 \"5\"))))"),
        Output(results = listOf("true", "false"))
    ),
    EasyTestCase(
        expressions = listOf("(puts (str (divide 10 2)))"),
        Output(results = listOf("5"))
    ),
    EasyTestCase(
        expressions = listOf("(puts (replace \"Hello World\" \"World\" \"There\")))"),
        Output(results = listOf("Hello There"))
    )
)

val intermediateTestCases: List<IntermediateTestCase> = listOf(
    IntermediateTestCase(
        expressions = listOf("(puts (str (add (subtract 10 5) 20)))"),
        Output(results = listOf("25"))
    ),
    IntermediateTestCase(
        expressions = listOf("(puts (str (divide 10 2)))", "(puts (str (divide 5 0)))"),
        Output(results = listOf("5", "ERROR at line 2"))
    ),
    IntermediateTestCase(
        expressions = listOf("(puts 5)"),
        Output(results = listOf("ERROR at line 1"))
    ),
    IntermediateTestCase(
        expressions = listOf("(set x 15)", "(puts (str (subtract x 5)))"),
        Output(results = listOf("10"))
    ),
    IntermediateTestCase(
        expressions = listOf("(puts (concat \"The result is: \" (str (add 5 5))))"),
        Output(results = listOf("The result is: 10"))
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
        expressions = listOf("(set x 10)", "(set x 20)"),
        Output(results = listOf("ERROR at line 2"))
    ),
    IntermediateTestCase(
        expressions = listOf("(puts (str (gt 10 5)))", "(puts (str (lt 5 10)))", "(puts (str (gt 5 10)))"),
        Output(results = listOf("true", "true", "false"))
    ),
    IntermediateTestCase(
        expressions = listOf(
            "(set x 10)",
            "(puts (str (add x 5)))",
            "(puts (str (divide x 0)))",
            "(puts \"This line should not be printed\"))"
        ),
        Output(results = listOf("15", "ERROR at line 3"))
    ),
    IntermediateTestCase(
        expressions = listOf("(puts (uppercase (concat \"hello\" (lowercase \"WORLD\")))))"),
        Output(results = listOf("HELLOworld"))
    ),
    IntermediateTestCase(
        expressions = listOf("(puts (str (max 3 9 2 5)))", "(puts (str (min 3 9 2 5)))"),
        Output(results = listOf("9", "2"))
    ),
    IntermediateTestCase(
        expressions = listOf(
            "(puts (str (equal true true)))",
            "(puts (str (equal null null)))",
            "(puts (str (not_equal null 0)))"
        ),
        Output(results = listOf("true", "true", "true"))
    )

)

val hardTestCases: List<HardTestCase> = listOf(
    HardTestCase(
        expressions = listOf("(puts \"ERROR at line 3\"))", "(set a (add 10 5))", "(puts (str a))"),
        Output(results = listOf("ERROR at line 3", "15"))
    ),
    HardTestCase(
        expressions = listOf("(puts (concat (str (add (subtract 20 10) (multiply 2 3)))) \" is the result\"))"),
        Output(results = listOf("16 is the result"))
    ),
    HardTestCase(
        expressions = listOf(
            "(puts (str (divide 10 2)))",
            "(puts (str (divide 10 0)))",
            "(puts (add 5 \"5\")))",
            "(puts (subtract 5))"
        ),
        Output(results = listOf("5", "ERROR at line 2"))
    ),
    HardTestCase(
        expressions = listOf("(puts (substring \"abcdef\" 0 6))", "(puts (substring \"abcdef\" 2 10))"),
        Output(results = listOf("abcdef", "ERROR at line 2"))
    ),
    HardTestCase(
        expressions = listOf("(puts (str (add (subtract (multiply (divide 100 2) (add 10 5)) 25) 10)))"),
        Output(results = listOf("735"))
    ),
    HardTestCase(
        expressions = listOf(
            "(set x 50)",
            "(puts (str (add x 10)))",
            "(puts (substring \"hello\" 1 3))",
            "(set x 100)"
        ),
        Output(results = listOf("60", "el", "ERROR at line 4"))
    ),
    HardTestCase(
        expressions = listOf("(puts (replace (concat (uppercase \"hello\")) (lowercase \"WORLD\"))) \"LO\" \"XY\")))"),
        Output(results = listOf("HEXYLOWORLD"))
    ),
    HardTestCase(
        expressions = listOf("(puts (str (equal (add 5 5) 10)))", "(puts (str (gt (add 5 \"5\")) 10)))"),
        Output(results = listOf("true", "ERROR at line 2"))
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
        Output(results = listOf("HARDXYTE", "HARDXYTEing", "ERROR at line 6"))
    ),
    HardTestCase(
        expressions = listOf(
            "(set a 100)",
            "(set b (divide a 5))",
            "(set c (subtract b (multiply 2 10)))",
            "(puts (str c))",
            "(set d (add c (divide 10 0)))",
            "(puts (str d))",
            "(set e (add d 50))",
            "(puts (str e))"
        ),
        Output(results = listOf("60", "ERROR at line 5"))
    ),
    HardTestCase(
        expressions = listOf(
            "(set x (add 10 20))",
            "(set y (multiply x 2))",
            "(set z (subtract y (divide 100 10)))",
            "(set result (concat \"Result is: \" (str z)))",
            "(puts result)",
            "(set invalidOperation (add result 10))",
            "(puts (str invalidOperation))"
        ),
        Output(results = listOf("Result is: 50", "ERROR at line 6"))
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
            "(set invalidOp (add z a))",
            "(puts (str invalidOp))",
            "(set finalResult (divide invalidOp (min (abs b) 0)))",
            "(puts (str finalResult))"
        ),
        Output(results = listOf("true", "Result: 1000 complex", "ERROR at line 10"))
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
    )
)
