package com.csg.codeit.checker

// Test cases

val easyTestCases = listOf(

    // subtraction
    EasyTestCase(expression = Expression(value = "(puts (subtract, 15.5, 4.0))"), result = "11.5"),
    EasyTestCase(expression = Expression(value = "(puts (subtract, 1, 2))"), result = "-1"),

    // multiplication
    EasyTestCase(expression = Expression(value = "(puts (multiply, 10.6, 2.0, 10.0))"), result = "212.0"),
    EasyTestCase(expression = Expression(value = "(puts (multiply, 3.0, -2.5))"), result = "-7.5"),

    // division
    EasyTestCase(expression = Expression(value = "(puts (divide, 25.5, -5.1))"), result = "-5.0"),
    EasyTestCase(expression = Expression(value = "(puts (divide, 650.25, 5.0, 5.0))"), result = "26.01"),

    // concatenation
    EasyTestCase(expression = Expression(value = "(puts (concat, \"MyParser\", \"Is\", \"Best\"))"), result = "MyParserIsBest"),

    EasyTestCase(
        expression = Expression("(puts \"Hello World\")"),
        result = "Hello World"
    ),
    EasyTestCase(
        expression = Expression("(puts (concat \"Hello\" \" World\"))"),
        result = "Hello world"
    ),
    EasyTestCase(
        expression = Expression("(puts (uppercase \"hello\"))\n(puts (lowercase \"WORLD\"))"),
        result = "HELLO\nworld"
    ),
    EasyTestCase(
        expression = Expression("(puts (str (add 1 2)))"),
        result = "3"
    ),
    EasyTestCase(
        expression = Expression("(set x 10)\n(puts (str x))"),
        result = "10"
    ),
    EasyTestCase(
        expression = Expression("(puts (str (subtract 5 2)))"),
        result = "3"
    ),
    EasyTestCase(
        expression = Expression("(puts (str (min 3 1 4)))"),
        result = "1"
    ),
    EasyTestCase(
        expression = Expression("(puts (str (equal 5 5)))\n(puts (str (equal 5 \"5\")))"),
        result = "true\nfalse"
    ),
    EasyTestCase(
        expression = Expression("(puts (str (divide 10 2)))"),
        result = "5"
    ),
    EasyTestCase(
        expression = Expression("(puts (replace \"Hello World\" \"World\" \"There\"))"),
        result = "Hello There"
    )
)

val intermediateTestCases = listOf(
    IntermediateTestCase(
        expression = Expression("(puts (str (add (subtract 10 5) 20)))"),
        result = "25"
    ),
    IntermediateTestCase(
        expression = Expression("(puts (str (divide 10 2)))\n(puts (str (divide 5 0)))"),
        result = "5\nERROR at line 2"
    ),
    IntermediateTestCase(
        expression = Expression("(puts 5)"),
        result = "ERROR at line 1"
    ),
    IntermediateTestCase(
        expression = Expression("(set x 15)\n(puts (str (subtract x 5)))"),
        result = "10"
    ),
    IntermediateTestCase(
        expression = Expression("(puts (concat \"The result is: \" (str (add 5 5))))"),
        result = "The result is: 10"
    ),
    IntermediateTestCase(
        expression = Expression("(puts (substring \"abcdef\" 2 5))"),
        result = "cde"
    ),
    IntermediateTestCase(
        expression = Expression("(puts (substring \"abcdef\" 2 10))"),
        result = "ERROR at line 1"
    ),
    IntermediateTestCase(
        expression = Expression("(set x 10)\n(set x 20)"),
        result = "ERROR at line 2"
    ),
    IntermediateTestCase(
        expression = Expression("(puts (str (gt 10 5)))\n(puts (str (lt 5 10)))\n(puts (str (gt 5 10)))"),
        result = "true\ntrue\nfalse"
    ),
    IntermediateTestCase(
        expression = Expression("(set x 10)\n(puts (str (add x 5)))\n(puts (str (divide x 0)))\n(puts \"This line should not be printed\")"),
        result = "15\nERROR at line 3"
    ),
    IntermediateTestCase(
        expression = Expression("(puts (uppercase (concat \"hello\" (lowercase \"WORLD\"))))"),
        result = "HELLOworld"
    ),
    IntermediateTestCase(
        expression = Expression("(puts (str (max 3 9 2 5)))\n(puts (str (min 3 9 2 5)))"),
        result = "9\n2"
    ),
    IntermediateTestCase(
        expression = Expression("(puts (str (equal true true)))\n(puts (str (equal null null)))\n(puts (str (not_equal null 0)))"),
        result = "true\ntrue\ntrue"
    )
)

val hardTestCases = listOf(
    HardTestCase(
        expression = Expression("(puts \"ERROR at line 3\")\n(set a (add 10 5))\n(puts (str a))"),
        result = "ERROR at line 3\n15"
    ),
    HardTestCase(
        expression = Expression("(puts (concat (str (add (subtract 20 10) (multiply 2 3)))) \" is the result\")"),
        result = "16 is the result"
    ),
    HardTestCase(
        expression = Expression("(puts (str (divide 10 2)))\n(puts (str (divide 10 0)))\n(puts (add 5 \"5\"))\n(puts (subtract 5))"),
        result = "5\nERROR at line 2"
    ),
    HardTestCase(
        expression = Expression("(puts (substring \"abcdef\" 0 6))\n(puts (substring \"abcdef\" 2 10))"),
        result = "abcdef\nERROR at line 2"
    ),
    HardTestCase(
        expression = Expression("(puts (str (add (subtract (multiply (divide 100 2) (add 10 5)) 25) 10)))"),
        result = "735"
    ),
    HardTestCase(
        expression = Expression("(set x 50)\n(puts (str (add x 10)))\n(puts (substring \"hello\" 1 3))\n(set x 100)"),
        result = "60\nel\nERROR at line 4"
    ),
    HardTestCase(
        expression = Expression("(puts (replace (concat (uppercase \"hello\") (lowercase \"WORLD\")) \"LO\" \"XY\"))"),
        result = "HEXYLOWORLD"
    ),
    HardTestCase(
        expression = Expression("(puts (str (equal (add 5 5) 10)))\n(puts (str (gt (add 5 \"5\") 10)))"),
        result = "true\nERROR at line 2"
    ),
    HardTestCase(
        expression = Expression("(puts (str (not_equal (abs (subtract 10 20)) 10)))\n(puts (str (gt (multiply 2 3 4) 20)))\n(puts (str (lt (divide 100 5) (multiply 3 3))))"),
        result = "false\ntrue\nfalse"
    ),
    HardTestCase(
        expression = Expression("(set x (concat (uppercase \"harder\") (lowercase \"TEST\")))\n(set y (replace x \"ER\" \"XY\"))\n(set z (substring y 0 8))\n(puts (str z))\n(puts (concat z \"ing\"))\n(set errorTest (concat x 123))"),
        result = "HARDXYTE\nHARDXYTEing\nERROR at line 6"
    ),
    HardTestCase(
        expression = Expression("(set a 100)\n(set b (divide a 5))\n(set c (subtract b (multiply 2 10)))\n(puts (str c))\n(set d (add c (divide 10 0)))\n(puts (str d))\n(set e (add d 50))\n(puts (str e))"),
        result = "60\nERROR at line 5"
    ),
    HardTestCase(
        expression = Expression("(set x (add 10 20))\n(set y (multiply x 2))\n(set z (subtract y (divide 100 10)))\n(set result (concat \"Result is: \" (str z)))\n(puts result)\n(set invalidOperation (add result 10))\n(puts (str invalidOperation))"),
        result = "Result is: 50\nERROR at line 6"
    ),
    HardTestCase(
        expression = Expression("(set a (add 100 (multiply (subtract 200 (divide 100 5)) (add 10 (max 1 2 3)))))\n(set b (concat (uppercase (str (subtract a (divide 100 0)))) \" TEST\"))\n(set c (subtract (multiply (add 50 50) (max (subtract 20 5) 10)) (min 30 40)))\n(set d (replace b \"100\" (str c)))\n(puts (str c))\n(puts (lowercase (concat d \" ERROR?\")))\n(set e (divide c (multiply (subtract c (divide 100 5)) 0)))\n(puts (str e))"),
        result = "ERROR at line 2"
    ),
    HardTestCase(
        expression = Expression("(set x (str (add 100 (subtract 200 (multiply 3 (divide 300 10))))))\n(set y (concat (lowercase x) \" complex\"))\n(set z (replace y \"300\" (str (divide 900 3))))\n(set a (max (min (subtract 1000 200) (multiply 2 3)) (divide 1000 10)))\n(set b (subtract a (add (multiply 2 3) (divide (add (multiply 100 2) (subtract 200 100)) 5))))\n(puts (str (gt a b)))\n(puts (concat \"Result: \" (uppercase z)))\n(set invalidOp (add z a))\n(puts (str invalidOp))\n(set finalResult (divide invalidOp (min (abs b) 0)))\n(puts (str finalResult))"),
        result = "true\nResult: 1000 complex\nERROR at line 10"
    ),
    HardTestCase(
        expression = Expression("(set a (add 100 (subtract 200 (divide 300 10))))\n(set b (concat (uppercase (str a)) \" Complex\"))\n(set c (replace b \"300\" (str (divide (add (multiply 2 3) (subtract 500 100)) 5))))\n(set d (multiply (subtract (max 100 200) (min 10 20)) (abs (subtract 500 (divide 100 0)))))\n(puts (str (equal (subtract d (multiply a 2)) 1000)))\n(set e (concat (lowercase (str d)) \" Nightmare\"))\n(puts e)\n(set f (add (str d) (uppercase e)))\n(puts (str f))\n(set g (divide (subtract f a) (add (multiply (subtract 100 50) (divide 200 0)) 10)))\n(puts (str g))"),
        result = "ERROR at line 4"
    )
)
