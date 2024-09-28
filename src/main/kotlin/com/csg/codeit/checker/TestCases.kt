package com.csg.codeit.checker

import com.csg.codeit.*
import com.csg.codeit.expression.ExpressionGeneratorUtils.ScalaUtils.toScalaSeq
import com.csg.codeit.expression.toTestCase
import com.csg.codeit.model.Output
import kotlin.random.Random


fun easyTestCases(): List<EasyTestCase> = convertedEasyTestCases() + listOf<List<Expression>>(

    // subtraction
    listOf(PutsExpression(StrExpression(SubtractExpression(DoubleExpression(15.5), DoubleExpression(4.0))))),
    listOf(PutsExpression(StrExpression(SubtractExpression(IntExpression(1), IntExpression(2))))),

    // multiplication
    listOf(PutsExpression(StrExpression(MultiplyExpression(toScalaSeq(DoubleExpression(10.6), DoubleExpression(2.0), DoubleExpression(10.0)))))),
    listOf(PutsExpression(StrExpression(MultiplyExpression(toScalaSeq(IntExpression(3), DoubleExpression(-2.5)))))),
    listOf(PutsExpression(StrExpression(MultiplyExpression(toScalaSeq(DoubleExpression(-100.5670)))))), // fail

    // division
    listOf(PutsExpression(StrExpression(DivideExpression(DoubleExpression(25.5), DoubleExpression(-5.1))))),
    listOf(PutsExpression(StrExpression(DivideExpression(DoubleExpression(650.25), DoubleExpression(5.0))))),
    listOf(PutsExpression(StrExpression(DivideExpression(IntExpression(10), IntExpression(4))))),
    listOf(PutsExpression(StrExpression(DivideExpression(IntExpression(1), IntExpression(2))))),
).map { it.toTestCase<EasyTestCase>() }

fun intermediateTestCases(): List<IntermediateTestCase> = convertedIntermediateTestCases() + listOf<List<Expression>>(

    // Different cases for division
    listOf(
        PutsExpression(StrExpression(DivideExpression(IntExpression(10), IntExpression(2)))),
        PutsExpression(StrExpression(DivideExpression(IntExpression(10), DoubleExpression(2.0)))),
        PutsExpression(StrExpression(DivideExpression(DoubleExpression(5.5), DoubleExpression(2.0)))),
        PutsExpression(StrExpression(DivideExpression(IntExpression(5), IntExpression(0)))),
    ).shuffled(),

    // nested puts
    listOf(
        PutsExpression(StrExpression(
            EqualsExpression(
                PutsExpression(RandomLiteralExGen.str(minLength = 100)),
                PutsExpression(RandomLiteralExGen.str(minLength = 100))
            )
        ))
    ),
).map { it.toTestCase<IntermediateTestCase>() }

fun superHardTestCases(): List<HardTestCase> = listOf<List<Expression>>(
    listOf(
        PutsExpression(StrExpression(EqualsExpression(PutsExpression(LowercaseExpression(RandomLiteralExGen.str(minLength = 50))), PutsExpression(UppercaseExpression(RandomLiteralExGen.str(minLength = 50)))))),
        PutsExpression(StrExpression(DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))),
        PutsExpression(StrExpression(DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.double()))),
        PutsExpression(StrExpression(SubtractExpression(RandomLiteralExGen.int(), RandomLiteralExGen.double()))),
        PutsExpression(StrExpression(AddExpression(toScalaSeq(*((1..50).map { RandomLiteralExGen.int() } + arrayOf(RandomLiteralExGen.double())).toTypedArray())))),
        PutsExpression(StrExpression(MaxExpression(toScalaSeq(*(1..100).map { RandomLiteralExGen.double() }.toTypedArray())))),
        PutsExpression(StrExpression(MinExpression(toScalaSeq(*(1..100).map { RandomLiteralExGen.double() }.toTypedArray())))),
        PutsExpression(StrExpression(MultiplyExpression(toScalaSeq(*(1..3).map { RandomLiteralExGen.int(scale=1000) }.toTypedArray())))),
        PutsExpression(StrExpression(AbsExpression(RandomLiteralExGen.int()))),
        SetExpression("integer", RandomLiteralExGen.int())
    ).shuffled() + listOf(
        PutsExpression(ConcatExpression(StrExpression(VarExpression("integer")), UppercaseExpression(StringExpression("o00000oo00000o")))),
        PutsExpression(SubtractExpression(VarExpression("integer"), RandomLiteralExGen.double()))
    ),

    // extreme mathematical operations
    listOf(
        PutsExpression(StrExpression(DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))),
        PutsExpression(StrExpression(AddExpression(toScalaSeq(*(1..100).map { RandomLiteralExGen.int() }.toTypedArray())))),
        PutsExpression(StrExpression(MaxExpression(toScalaSeq(*(1..100).map { RandomLiteralExGen.double() }.toTypedArray())))),
        PutsExpression(StrExpression(MinExpression(toScalaSeq(*((1..100).map { RandomLiteralExGen.int() } + arrayOf(RandomLiteralExGen.double())).toTypedArray())))),
        PutsExpression(StrExpression(MultiplyExpression(toScalaSeq(*((1..3).map { RandomLiteralExGen.int(scale=100) } + arrayOf(RandomLiteralExGen.double(scale=100))).toTypedArray())))),
        PutsExpression(UppercaseExpression(StrExpression(GtExpression(
            MultiplyExpression(toScalaSeq(*(1..3).map { RandomLiteralExGen.int(scale = 1000) }.toTypedArray())),
            AddExpression(toScalaSeq(*(1..100).map { RandomLiteralExGen.double() }.toTypedArray()))
        )))),
        PutsExpression(UppercaseExpression(StrExpression(LtExpression(
            MaxExpression(toScalaSeq(*(1..10).map { RandomLiteralExGen.int() }.toTypedArray())),
            AbsExpression(MinExpression(toScalaSeq(*(1..10).map { RandomLiteralExGen.double() }.toTypedArray())))
        )))),
    ).shuffled() + listOf(
        PutsExpression(StrExpression(ConcatExpression(
            ConcatExpression(RandomLiteralExGen.str(minLength = 100), RandomLiteralExGen.str(minLength = 100)),
            StrExpression(DivideExpression(RandomLiteralExGen.int(), IntExpression(0)))
        ))),
    ),

    // extreme string operations
    listOf(
        PutsExpression(ConcatExpression(
            ConcatExpression(RandomLiteralExGen.str(minLength = 100), RandomLiteralExGen.str(minLength = 100)),
            LowercaseExpression(RandomLiteralExGen.str(minLength = 5, maxLength = 10))
        )),
        PutsExpression(StrExpression(SubstringExpression(
            ConcatExpression(
                ConcatExpression(RandomLiteralExGen.str(maxLength = 10), RandomLiteralExGen.str(maxLength = 10)),
                LowercaseExpression(RandomLiteralExGen.str(minLength = 5, maxLength = 10))
            ),
            MinExpression(toScalaSeq(AbsExpression(MultiplyExpression(toScalaSeq(*(1..9).map { RandomLiteralExGen.int(scale = 10) }.toTypedArray()))), IntExpression(0))),
            MinExpression(toScalaSeq(AbsExpression(AddExpression(toScalaSeq(*(1..100).map { RandomLiteralExGen.int() }.toTypedArray()))), IntExpression(5))),
        ))),
        SetExpression("aa", ConcatExpression(RandomLiteralExGen.str(minLength = 50), RandomLiteralExGen.str(minLength = 50))),
        SetExpression("bb", LowercaseExpression(ConcatExpression(RandomLiteralExGen.str(minLength = 50), RandomLiteralExGen.str(minLength = 50)))),
    ).shuffled() + listOf(
        PutsExpression(ReplaceExpression(VarExpression("aa"), StringExpression("a"), StringExpression("varA"))),
        PutsExpression(ReplaceExpression(VarExpression("bb"), StringExpression("b"), StringExpression("varB"))),
        PutsExpression(ReplaceExpression(LowercaseExpression(VarExpression("aaa")), StringExpression("a"), StringExpression("varA"))),
    ),
).map { it.toTestCase<HardTestCase>() }

fun hardTestCases(): List<HardTestCase> = convertedHardTestCases()


fun convertedEasyTestCases(): List<EasyTestCase> {
    return listOf<List<Expression>>(
        listOf(PutsExpression(
            ConcatExpression(
                StringExpression("MyParser"),
                StringExpression("IsBest")
            )
        )),
        listOf(PutsExpression(StringExpression("Hello World"))),
        listOf(PutsExpression(ConcatExpression(StringExpression("Hello"), StringExpression(" World")))),
        listOf(
            PutsExpression(UppercaseExpression(StringExpression("hello"))),
            PutsExpression(LowercaseExpression(StringExpression("WORLD")))
        ),

        listOf(PutsExpression(StrExpression(AddExpression(toScalaSeq(IntExpression(1), IntExpression(2)))))),

        listOf(
            SetExpression("x", IntExpression(10)),
            PutsExpression(StrExpression(VarExpression("x")))
        ),

        listOf(PutsExpression(StrExpression(SubtractExpression(IntExpression(5), IntExpression(2))))),

        listOf(PutsExpression(StrExpression(MinExpression(toScalaSeq(IntExpression(3), IntExpression(1), IntExpression(4)))))),

        listOf(
            PutsExpression(StrExpression(EqualsExpression(IntExpression(5), IntExpression(5)))),
            PutsExpression(StrExpression(EqualsExpression(IntExpression(5), StringExpression("5"))))
        ),

        listOf(
            PutsExpression(StrExpression(EqualsExpression(StringExpression("10.0"), StringExpression("10"))))
        ),

        listOf(PutsExpression(ReplaceExpression(StringExpression("Hello World"), StringExpression("World"), StringExpression("There")))
        ),

        listOf(PutsExpression(ReplaceExpression(StringExpression("hello"), StringExpression("l"), StringExpression("x")))),

        listOf(PutsExpression(ReplaceExpression(StringExpression("hello world"), StringExpression("world"), StringExpression("")))),

        listOf(PutsExpression(ReplaceExpression(StringExpression("hello world"), StringExpression("planet"), StringExpression("earth")))),

        listOf(PutsExpression(StrExpression(EqualsExpression(StringExpression("True"), StringExpression("true"))))),
    ).map{ it.toTestCase<EasyTestCase>() }
}

fun convertedIntermediateTestCases(): List<IntermediateTestCase> {
    return listOf(
        listOf(
            PutsExpression(StrExpression(AddExpression(toScalaSeq(SubtractExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()), RandomLiteralExGen.int())))),
            PutsExpression(StrExpression(AddExpression(toScalaSeq(RandomLiteralExGen.double())))), // fail
        ),

        listOf(
            PutsExpression(StrExpression(DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))),
            PutsExpression(StrExpression(DivideExpression(RandomLiteralExGen.double(), IntExpression(0))))
        ),

        listOf(PutsExpression(AbsExpression(MaxExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.double()))))),

        listOf(
            SetExpression("x", RandomLiteralExGen.int()),
            PutsExpression(StrExpression(SubtractExpression(VarExpression("x"), RandomLiteralExGen.int())))
        ),

        listOf(
            PutsExpression(ConcatExpression(
                StringExpression("The result is: "),
                StrExpression(AddExpression(toScalaSeq(RandomLiteralExGen.double(), RandomLiteralExGen.double())))
            )),
            PutsExpression(StrExpression(AddExpression(toScalaSeq(RandomLiteralExGen.double())))), // fail
        ),

        listOf(PutsExpression(
            RandomLiteralExGen.str(minLength = 50).let {
                SubstringExpression(
                    it,
                    IntExpression(Random.nextInt(it.value().length)),
                    IntExpression(Random.nextInt(it.value().length, it.value().length * 2))
                )
            }
        )),

        listOf(
            PutsExpression(
                RandomLiteralExGen.str(minLength = 100).let {
                    SubstringExpression(
                        StringExpression("abcdef"),
                        IntExpression(Random.nextInt(it.value().length - 1)),
                        IntExpression(it.value().length - 1)
                    )
                }
            )
        ),

        listOf(
            SetExpression("x", RandomLiteralExGen.int()),
            SetExpression("x", RandomLiteralExGen.int())
        ),

        listOf(
            PutsExpression(StrExpression(GtExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))),
            PutsExpression(StrExpression(LtExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))),
            PutsExpression(StrExpression(GtExpression(RandomLiteralExGen.int(), RandomLiteralExGen.double()))),
            PutsExpression(StrExpression(LtExpression(RandomLiteralExGen.double(), RandomLiteralExGen.int()))),
            PutsExpression(StrExpression(RandomLiteralExGen.int().let { GtExpression(it, it) } )),
            PutsExpression(StrExpression(RandomLiteralExGen.int().let { LtExpression(it, it) } )),
        ).shuffled(),

        listOf(
            PutsExpression(UppercaseExpression(ConcatExpression(RandomLiteralExGen.str(maxLength = 20), LowercaseExpression(RandomLiteralExGen.str(maxLength = 20))))),
            PutsExpression(StrExpression(NotEqualsExpression(NullExpression(), AbsExpression(RandomLiteralExGen.double()))))
        ).shuffled(),

        listOf(
            PutsExpression(StrExpression(MaxExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int(), RandomLiteralExGen.int(), RandomLiteralExGen.double())))),
            PutsExpression(StrExpression(MinExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int(), RandomLiteralExGen.double(), RandomLiteralExGen.int())))),
        ).shuffled() + listOf(
            PutsExpression(StrExpression(MinExpression(toScalaSeq(RandomLiteralExGen.int())))), // should fail
        ),

        listOf(
            PutsExpression(StrExpression(EqualsExpression(RandomLiteralExGen.bool(), RandomLiteralExGen.bool()))),
            PutsExpression(StrExpression(EqualsExpression(RandomLiteralExGen.bool(), RandomLiteralExGen.bool()))),
            PutsExpression(StrExpression(EqualsExpression(RandomLiteralExGen.bool(), RandomLiteralExGen.bool()))),
            PutsExpression(StrExpression(EqualsExpression(RandomLiteralExGen.bool(), RandomLiteralExGen.bool()))),
            PutsExpression(StrExpression(EqualsExpression(NullExpression(), NullExpression()))),
            PutsExpression(StrExpression(NotEqualsExpression(NullExpression(), AbsExpression(RandomLiteralExGen.double())))),
        ).shuffled(),

        listOf(
            PutsExpression(ReplaceExpression(
                ReplaceExpression(
                    ConcatExpression(
                        UppercaseExpression(RandomLiteralExGen.str(minLength = 10)),
                        LowercaseExpression(RandomLiteralExGen.str(minLength = 10))
                    ),
                    StringExpression("l"),
                    StringExpression("x")
                ),
                StringExpression("o"),
                StringExpression("y")
            ))
        ),

        RandomLiteralExGen.int().let {
            listOf(
                PutsExpression(StrExpression(EqualsExpression(AddExpression(toScalaSeq(it, it)), DoubleExpression(it.value() * 2.0)))),
                PutsExpression(StrExpression(GtExpression(AddExpression(toScalaSeq(it, StringExpression(it.value().toString()))), DoubleExpression(it.value() * 2.0))))
            )
        },

        listOf(
            PutsExpression(StringExpression("ERROR at line 3")),
            SetExpression("a", AddExpression(toScalaSeq(*((1..100).map { RandomLiteralExGen.int() } + arrayOf(RandomLiteralExGen.double())).toTypedArray()))),
            PutsExpression(StrExpression(VarExpression("a")))
        ),

        listOf(
            PutsExpression(
                ConcatExpression(
                    StrExpression(
                        AddExpression(
                            toScalaSeq(
                                SubtractExpression(RandomLiteralExGen.int(), AbsExpression(RandomLiteralExGen.double())),
                                MultiplyExpression(toScalaSeq(*((1..7).map { RandomLiteralExGen.int(scale = 10) } + arrayOf(RandomLiteralExGen.double(scale = 10))).toTypedArray()))
                            )
                        )
                    ),
                    ConcatExpression(StringExpression(" is the result AND :D "), RandomLiteralExGen.str(minLength = 200)),
                )
            ),
            PutsExpression(StrExpression(MultiplyExpression(toScalaSeq(RandomLiteralExGen.double())))), // fail
        ),

        listOf(
            PutsExpression(SubstringExpression(StringExpression("abcdef"), IntExpression(0), IntExpression(6))),
            PutsExpression(SubstringExpression(StringExpression("abcdef"), IntExpression(2), IntExpression(10)))
        ),

        listOf(
            PutsExpression(
                StrExpression(
                    AddExpression(
                        toScalaSeq(
                            SubtractExpression(
                                MultiplyExpression(
                                    toScalaSeq(
                                        DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()),
                                        AddExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.double()))
                                    )
                                ),
                                RandomLiteralExGen.int()
                            ),
                            RandomLiteralExGen.int()))
                )
            ),
            AddExpression(toScalaSeq(RandomLiteralExGen.int())) // error
        ),

        listOf(
            listOf(
                Triple("abababab", "ab", "xy"),
                Triple("yzyzyzyzABCDEFGHIJKLMNOPQRSTyzyzyzyzy", "yz", "mn"),
                Triple("yzyzyzyzABCDEFGHIJKLMNOPQRSTyzyzyzyzy", "zy", "mn"),
                Triple("12121221212ABCDEFGHIJKLMNOPQRST12121212112", "12", "00"),
            ).shuffled().first().let {
                PutsExpression(ReplaceExpression(StringExpression(it.first), StringExpression(it.second), StringExpression(it.third)))
            }
        ),

        listOf(
            PutsExpression(ReplaceExpression(RandomLiteralExGen.str(minLength = 20), NullExpression(), StringExpression("null")))
        ),

        listOf(
            PutsExpression(ReplaceExpression(ConcatExpression(StringExpression("abc"), StringExpression("def")), StringExpression("bc"), StringExpression("xyz")))
        ),

        listOf(
            PutsExpression(
                ReplaceExpression(
                    ReplaceExpression(
                        ConcatExpression(
                            StrExpression(
                                AddExpression(toScalaSeq(*((1..100).map { RandomLiteralExGen.int() } + arrayOf(RandomLiteralExGen.double())).toTypedArray()))
                            ),
                            StringExpression(" number")
                        ),
                        StringExpression(Random.nextInt(1, 10).toString()),
                        StringExpression("0")
                    ),
                    StringExpression(Random.nextInt(1, 10).toString()),
                    StringExpression("0")
                )
            )
        ),
    ).map { it.toTestCase<IntermediateTestCase>() }
}

fun convertedHardTestCases(): List<HardTestCase> = listOf<List<Expression>>(
    listOf(
        SetExpression("x", RandomLiteralExGen.int()),
        PutsExpression(StrExpression(AddExpression(toScalaSeq(VarExpression("x"), RandomLiteralExGen.int())))),
        PutsExpression(StrExpression(DivideExpression(VarExpression("x"), DoubleExpression(0.0)))),
        PutsExpression(StringExpression("This line should not be printed"))
    ),

    listOf(
        PutsExpression(StrExpression(DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))),
        PutsExpression(StrExpression(DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.double()))),
        PutsExpression(AddExpression(RandomLiteralExGen.int().let { toScalaSeq(it, StringExpression(it.value().toString())) })),
        PutsExpression(SubtractExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))
    ),

    listOf(
        SetExpression("x", RandomLiteralExGen.int()),
        PutsExpression(StrExpression(AddExpression(toScalaSeq(VarExpression("x"), RandomLiteralExGen.int())))),
        PutsExpression(SubstringExpression(RandomLiteralExGen.str(minLength = 10), RandomLiteralExGen.int(), RandomLiteralExGen.int())),
        SetExpression("x", RandomLiteralExGen.int())
    ),
    listOf(
        PutsExpression(
            StrExpression(
                NotEqualsExpression(
                    AbsExpression(SubtractExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())),
                    RandomLiteralExGen.int()
                )
            )
        ),
        PutsExpression(
            StrExpression(
                GtExpression(
                    MultiplyExpression(
                        toScalaSeq(
                            RandomLiteralExGen.int(scale = 1_000),
                            RandomLiteralExGen.int(scale = 1_000),
                            RandomLiteralExGen.int(scale = 1_000)
                        )
                    ), RandomLiteralExGen.int()
                )
            )
        ),
        PutsExpression(
            StrExpression(
                LtExpression(
                    DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()), MultiplyExpression(
                        toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                    )
                )
            )
        )
    ),

    listOf(
        SetExpression("x", ConcatExpression(UppercaseExpression(StringExpression("harder")), LowercaseExpression(StringExpression("TEST")))),
        SetExpression("y", ReplaceExpression(VarExpression("x"), StringExpression("ER"), StringExpression("XY"))),
        SetExpression("z", SubstringExpression(VarExpression("y"), IntExpression(0), IntExpression(8))),
        PutsExpression(StrExpression(VarExpression("z"))),
        PutsExpression(ConcatExpression(VarExpression("z"), RandomLiteralExGen.str(minLength = 50))),
        SetExpression("errorTest", ConcatExpression(VarExpression("x"), RandomLiteralExGen.int()))
    ),

    listOf(
        SetExpression("a", RandomLiteralExGen.int()),
        SetExpression(("b"), DivideExpression(VarExpression("a"), RandomLiteralExGen.int())),
        SetExpression("c", SubtractExpression(
            VarExpression("b"),
            MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int()))
        )),
        PutsExpression(StrExpression(VarExpression("c"))),
        SetExpression("d", AddExpression(toScalaSeq(
            VarExpression("c"),
            DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())
        ))),
        PutsExpression(StrExpression(VarExpression("d"))),
        SetExpression("e", AddExpression(toScalaSeq(VarExpression("d"), RandomLiteralExGen.int()))),
        PutsExpression(StrExpression(VarExpression("e")))
    ),

    listOf(
        SetExpression("x", AddExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int()))),
        SetExpression("y", MultiplyExpression(toScalaSeq(VarExpression("x"), RandomLiteralExGen.int()))),
        SetExpression("z", SubtractExpression(VarExpression("y"), DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))),
        SetExpression("result", ConcatExpression(RandomLiteralExGen.str(minLength = 10), StrExpression(VarExpression("z")))),
        PutsExpression(VarExpression("result")),
        SetExpression("invalidOperation", AddExpression(toScalaSeq(VarExpression("result"), RandomLiteralExGen.int()))),
        PutsExpression(StrExpression(VarExpression("invalidOperation")))
    ),

    listOf(
        SetExpression("a", AddExpression(
            toScalaSeq(
                RandomLiteralExGen.int(), MultiplyExpression(
                    toScalaSeq(
                        SubtractExpression(RandomLiteralExGen.int(), DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())),
                        AddExpression(
                            toScalaSeq(
                                RandomLiteralExGen.int(),
                                MaxExpression(
                                    toScalaSeq(
                                        RandomLiteralExGen.int(),
                                        RandomLiteralExGen.int(),
                                        RandomLiteralExGen.int()
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
        ),
        SetExpression("b", ConcatExpression(
            UppercaseExpression(
                StrExpression(
                    SubtractExpression(
                        VarExpression("a"), DivideExpression(
                            RandomLiteralExGen.int(), RandomLiteralExGen.int()
                        )
                    )
                )
            ), RandomLiteralExGen.str(minLength = 10)
        )
        ),
        SetExpression("c", SubtractExpression(
            MultiplyExpression(
                toScalaSeq(
                    AddExpression(
                        toScalaSeq(
                            RandomLiteralExGen.int(),
                            RandomLiteralExGen.int()
                        )
                    ),
                    MaxExpression(
                        toScalaSeq(
                            SubtractExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()),
                            RandomLiteralExGen.int()
                        )
                    )
                )
            ),
            MinExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int()))
        )
        ),
        SetExpression("d", ReplaceExpression(VarExpression("b"), RandomLiteralExGen.str(minLength = 10), StrExpression(VarExpression("c")))),
        PutsExpression(StrExpression(VarExpression("c"))),
        PutsExpression(LowercaseExpression(ConcatExpression(VarExpression("d"), RandomLiteralExGen.str(minLength = 10)))),
        SetExpression("e",
            DivideExpression(
                VarExpression("c"),
                MultiplyExpression(
                    toScalaSeq(
                        SubtractExpression(
                            VarExpression("c"),
                            DivideExpression(
                                RandomLiteralExGen.int(),
                                RandomLiteralExGen.int()
                            ),
                        ),
                        RandomLiteralExGen.int()
                    )
                )
            )
        ),
        PutsExpression(StrExpression(VarExpression("e")))
    ),

    listOf(
        SetExpression("x", StrExpression(
            AddExpression(
                toScalaSeq(
                    RandomLiteralExGen.int(), SubtractExpression(
                        RandomLiteralExGen.int(), MultiplyExpression(
                            toScalaSeq(RandomLiteralExGen.int(), DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))
                        )
                    )
                )
            )
        )
        ),
        SetExpression("y", ConcatExpression(LowercaseExpression(VarExpression("x")), RandomLiteralExGen.str(minLength = 10))),
        SetExpression("z", ReplaceExpression(
            VarExpression("y"), RandomLiteralExGen.str(minLength = 10), StrExpression(
                DivideExpression(
                    RandomLiteralExGen.int(), RandomLiteralExGen.int()
                )
            )
        )
        ),
        SetExpression("a", MaxExpression(
            toScalaSeq(
                MinExpression(
                    toScalaSeq(
                        SubtractExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()),
                        MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())),
                    )
                ),
                DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())
            )
        )
        ),
        SetExpression("b", SubtractExpression(
            VarExpression("a"), AddExpression(
                toScalaSeq(
                    MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())),
                    DivideExpression(
                        AddExpression(
                            toScalaSeq(
                                MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())),
                                SubtractExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                            )
                        ),
                        RandomLiteralExGen.int()
                    )
                )
            )
        )
        ),
        PutsExpression(StrExpression(GtExpression(VarExpression("a"), VarExpression("b")))),
        PutsExpression(ConcatExpression(RandomLiteralExGen.str(minLength = 10), UppercaseExpression(VarExpression("z")))),
        SetExpression("invalidOp", AddExpression(toScalaSeq(VarExpression("z"), VarExpression("a")))),
        PutsExpression(StrExpression(VarExpression("invalidOp"))),
        SetExpression("finalResult", DivideExpression(
            VarExpression("invalidOp"), (MinExpression(
                toScalaSeq(AbsExpression(VarExpression("b")), RandomLiteralExGen.int())
            )))
        ),
        PutsExpression(StrExpression(VarExpression("finalResult")))
    ),

    listOf(
        SetExpression("a", AddExpression(
            toScalaSeq(
                RandomLiteralExGen.int(),
                SubtractExpression(RandomLiteralExGen.int(), DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))
            )
        )
        ),
        SetExpression("b", ConcatExpression(UppercaseExpression(StrExpression(VarExpression("a"))), RandomLiteralExGen.str(minLength = 10))),
        SetExpression("c",
            ReplaceExpression(
                VarExpression("b"),
                RandomLiteralExGen.str(minLength = 10),
                StrExpression(
                    DivideExpression(
                        AddExpression(
                            toScalaSeq(
                                MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())),
                                SubtractExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                            )
                        ),
                        RandomLiteralExGen.int()
                    )
                ),
            )
        ),
        SetExpression("d", MultiplyExpression(
            toScalaSeq(
                SubtractExpression(
                    MaxExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())),
                    MinExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int()))
                ),
                AbsExpression(SubtractExpression(RandomLiteralExGen.int(), DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())))
            )
        )
        ),
        PutsExpression(
            StrExpression(
                EqualsExpression(
                    SubtractExpression(
                        VarExpression("d"), MultiplyExpression(
                            toScalaSeq(
                                VarExpression("a"), RandomLiteralExGen.int()
                            )
                        )
                    ),
                    RandomLiteralExGen.int()
                )
            )
        ),
        SetExpression("e", ConcatExpression(LowercaseExpression(StrExpression(VarExpression("d"))), RandomLiteralExGen.str(minLength = 10))),
        PutsExpression(VarExpression("e")),
        SetExpression("f", AddExpression(
            toScalaSeq(
                StrExpression(VarExpression("d")), UppercaseExpression(
                    VarExpression("e")
                )
            )
        )
        ),
        PutsExpression(StrExpression(VarExpression("f")))
    ),

    listOf(
        SetExpression("a", AddExpression(
            toScalaSeq(
                RandomLiteralExGen.int(),
                SubtractExpression(RandomLiteralExGen.int(), DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))
            )
        )
        ),
        SetExpression("b", ConcatExpression(UppercaseExpression(StrExpression(VarExpression("a"))), RandomLiteralExGen.str(minLength = 10))),
        SetExpression("c", ReplaceExpression(
            VarExpression("b"),
            RandomLiteralExGen.str(minLength = 10),
            StrExpression(
                DivideExpression(
                    AddExpression(
                        toScalaSeq(
                            MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())),
                            SubtractExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                        )
                    ),
                    RandomLiteralExGen.int()
                )
            )
        )
        ),
        SetExpression("d", MultiplyExpression(
            toScalaSeq(
                SubtractExpression(
                    MaxExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())),
                    MinExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int()))
                ),
                AbsExpression(SubtractExpression(RandomLiteralExGen.int(), DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())))
            )
        )
        ),
        PutsExpression(
            StrExpression(
                EqualsExpression(
                    SubtractExpression(
                        VarExpression("d"),
                        MultiplyExpression(toScalaSeq(VarExpression("a"), RandomLiteralExGen.int()))
                    ),
                    RandomLiteralExGen.int()
                )
            )
        ),
        SetExpression("e", ConcatExpression(LowercaseExpression(StrExpression(VarExpression("d"))), RandomLiteralExGen.str(minLength = 10))),
        PutsExpression(VarExpression("e")),
        SetExpression("f", AddExpression(
            toScalaSeq(
                StrExpression(VarExpression("d")), UppercaseExpression(
                    VarExpression("e")
                )
            )
        )
        ),
        PutsExpression(StrExpression(VarExpression("f"))),
        SetExpression("g", DivideExpression(
            SubtractExpression(VarExpression("f"), VarExpression("a")),
            AddExpression(
                toScalaSeq(
                    MultiplyExpression(
                        toScalaSeq(
                            SubtractExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()),
                            DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()),
                        )
                    ),
                    RandomLiteralExGen.int()
                )
            )
        )
        ),
        PutsExpression(StrExpression(VarExpression("g")))
    ),

    listOf(
        SetExpression("x", AddExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int()))),
        SetExpression("y", MultiplyExpression(toScalaSeq(VarExpression("x"), RandomLiteralExGen.int()))),
        SetExpression("z", SubtractExpression(VarExpression("y"), DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))),
        SetExpression("result", ConcatExpression(RandomLiteralExGen.str(minLength = 10), StrExpression(VarExpression("z")))),
        PutsExpression(StrExpression(LowercaseExpression(StrExpression(VarExpression("result"))))),
        SetExpression("output", ReplaceExpression(
            ConcatExpression(UppercaseExpression(LowercaseExpression(VarExpression("result"))), RandomLiteralExGen.str(minLength = 10)), StrExpression(
                RandomLiteralExGen.int()
            ), StrExpression(RandomLiteralExGen.int())
        )
        ),
        PutsExpression(StrExpression(UppercaseExpression(UppercaseExpression(UppercaseExpression(VarExpression("output"))))))
    ),

    listOf(
        SetExpression("x", StrExpression(AddExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())))),
        SetExpression("y", StrExpression(DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))),
        SetExpression("z", StrExpression(MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())))),
        PutsExpression(StrExpression(EqualsExpression(VarExpression("x"), VarExpression("y")))),
        PutsExpression(StrExpression(EqualsExpression(VarExpression("y"), VarExpression("z")))),
        PutsExpression(StrExpression(EqualsExpression(VarExpression("z"), RandomLiteralExGen.str(minLength = 10)))),
        PutsExpression(StrExpression(EqualsExpression(VarExpression("z"), ConcatExpression(RandomLiteralExGen.str(minLength = 10), RandomLiteralExGen.str(minLength = 10)))))
    ),

    listOf(
        SetExpression("a", EqualsExpression(RandomLiteralExGen.bool(), RandomLiteralExGen.bool())),
        SetExpression("b", EqualsExpression(RandomLiteralExGen.bool(), RandomLiteralExGen.bool())),
        PutsExpression(StrExpression(EqualsExpression(StrExpression(VarExpression("a")), StrExpression(VarExpression("b"))))),
        SetExpression("c", ConcatExpression(RandomLiteralExGen.str(minLength = 10), RandomLiteralExGen.str(minLength = 10))),
        PutsExpression(StrExpression(EqualsExpression(VarExpression("c"), RandomLiteralExGen.str(minLength = 10))))
    ),

    listOf(
        SetExpression("x", StrExpression(MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())))),
        SetExpression("y", StrExpression(DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))),
        SetExpression("z", ConcatExpression(VarExpression("x"), VarExpression("y"))),
        PutsExpression(StrExpression(EqualsExpression(VarExpression("z"), RandomLiteralExGen.str(minLength = 10)))),
        PutsExpression(StrExpression(EqualsExpression(VarExpression("z"), ConcatExpression(VarExpression("x"), RandomLiteralExGen.str(minLength = 10))))),
        PutsExpression(StrExpression(EqualsExpression(VarExpression("z"), RandomLiteralExGen.str(minLength = 10))))
    ),

    listOf(
        SetExpression("str1", RandomLiteralExGen.str(minLength = 10)),
        SetExpression("part", SubstringExpression(VarExpression("str1"), RandomLiteralExGen.int(), RandomLiteralExGen.int())),
        PutsExpression(StrExpression(EqualsExpression(VarExpression("part"), RandomLiteralExGen.str(minLength = 10)))),
        SetExpression("invalidPart", SubstringExpression(VarExpression("str1"), RandomLiteralExGen.int(), RandomLiteralExGen.int())),
        PutsExpression(StrExpression(EqualsExpression(VarExpression("invalidPart"), RandomLiteralExGen.str(minLength = 10))))
    ),

    listOf(
        SetExpression("a", NullExpression()),
        SetExpression("b", ConcatExpression(RandomLiteralExGen.str(minLength = 10), RandomLiteralExGen.str(minLength = 10))),
        PutsExpression(StrExpression(EqualsExpression(VarExpression("a"), VarExpression("b")))),
        SetExpression("c", EqualsExpression(NullExpression(), VarExpression("a"))),
        PutsExpression(StrExpression(EqualsExpression(StrExpression(VarExpression("c")), RandomLiteralExGen.str(minLength = 10)))),
        PutsExpression(StrExpression(EqualsExpression(VarExpression("a"), RandomLiteralExGen.str(minLength = 10))))
    ),

    listOf(
        PutsExpression(
            StrExpression(
                AddExpression(
                    toScalaSeq(
                        MultiplyExpression(
                            toScalaSeq(
                                RandomLiteralExGen.int(),
                                SubtractExpression(
                                    RandomLiteralExGen.int(), DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                                )
                            )
                        ),
                        SubtractExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                    )
                )
            )
        ),
        PutsExpression((StrExpression(
            SubtractExpression(
                AddExpression(
                    toScalaSeq(
                        MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())),
                        DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                    )
                ),
                DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())
            )
        )))
    ),

    listOf(
        SetExpression("x", AddExpression(
            toScalaSeq(
                MultiplyExpression(
                    toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                ), SubtractExpression(RandomLiteralExGen.int(), DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))
            )
        )
        ),
        SetExpression("y", DivideExpression(AddExpression(toScalaSeq(VarExpression("x"), RandomLiteralExGen.int())), RandomLiteralExGen.int())),
        PutsExpression(
            StrExpression(
                MultiplyExpression(
                    toScalaSeq(
                        VarExpression("y"),
                        SubtractExpression(
                            RandomLiteralExGen.int(), DivideExpression(VarExpression("x"), RandomLiteralExGen.int())
                        )
                    )
                )
            )
        )
    ),

    listOf(
        PutsExpression(
            ConcatExpression(
                RandomLiteralExGen.str(minLength = 10), StrExpression(
                    AddExpression(
                        toScalaSeq(
                            MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())),
                            DivideExpression(RandomLiteralExGen.int(), SubtractExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int()))
                        )
                    )
                )
            )
        ),
        PutsExpression(
            StrExpression(
                ReplaceExpression(
                    RandomLiteralExGen.str(minLength = 10), RandomLiteralExGen.str(minLength = 10), ConcatExpression(
                        RandomLiteralExGen.str(minLength = 10), StrExpression(RandomLiteralExGen.int())
                    )
                )
            )
        )
    ),

    listOf(
        SetExpression("x", StrExpression(
            MultiplyExpression(
                toScalaSeq(
                    AddExpression(
                        toScalaSeq(
                            RandomLiteralExGen.int(),
                            SubtractExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                        )
                    ),
                    DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                )
            )
        )
        ),
        PutsExpression(
            ReplaceExpression(
                VarExpression("x"),
                StrExpression(DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())),
                StrExpression(MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())))
            )
        ),
        PutsExpression(ConcatExpression(UppercaseExpression(VarExpression("x")), RandomLiteralExGen.str(minLength = 10)))
    ),

    listOf(
        SetExpression("a", ConcatExpression(
            StrExpression(AddExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int()))),
            UppercaseExpression(ReplaceExpression(RandomLiteralExGen.str(minLength = 10), RandomLiteralExGen.str(minLength = 10), RandomLiteralExGen.str(minLength = 10)))
        )
        ),
        PutsExpression(
            SubstringExpression(
                VarExpression("a"), SubtractExpression(
                    MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())),
                    RandomLiteralExGen.int()
                ), AddExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int()))
            )
        ),
        PutsExpression(StrExpression(NotEqualsExpression(VarExpression("a"), RandomLiteralExGen.int())))
    ),

    listOf(
        SetExpression("x", DivideExpression(
            AddExpression(
                toScalaSeq(
                    RandomLiteralExGen.int(),
                    SubtractExpression(MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())), RandomLiteralExGen.int()),
                )
            ),
            MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int()))
        )
        ),
        PutsExpression(ConcatExpression(StrExpression(EqualsExpression(VarExpression("x"), RandomLiteralExGen.int())), RandomLiteralExGen.str(minLength = 10))),
        PutsExpression(
            StrExpression(
                AddExpression(
                    toScalaSeq(
                        VarExpression("x"),
                        ReplaceExpression(StrExpression(RandomLiteralExGen.int()), RandomLiteralExGen.str(minLength = 10), RandomLiteralExGen.str(minLength = 10))
                    )
                )
            )
        )
    ),

    listOf(
        SetExpression("x", AddExpression(
            toScalaSeq(
                RandomLiteralExGen.int(), MultiplyExpression(
                    toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                )
            )
        )
        ),
        SetExpression("y", SubtractExpression(
            VarExpression("x"), DivideExpression(
                MultiplyExpression(toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())), AddExpression(
                    toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                )
            )
        )
        ),
        PutsExpression(
            StrExpression(
                AddExpression(
                    toScalaSeq(
                        MultiplyExpression(toScalaSeq(VarExpression("x"), VarExpression("y"))),
                        DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                    ),
                )
            )
        ),
        PutsExpression(ConcatExpression(UppercaseExpression(StrExpression(VarExpression("y"))), RandomLiteralExGen.str(minLength = 10)))

    ),

    listOf(
        SetExpression("a", AddExpression(
            toScalaSeq(
                RandomLiteralExGen.int(),
                MultiplyExpression(
                    toScalaSeq(
                        SubtractExpression(RandomLiteralExGen.int(), DivideExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())),
                        AddExpression(
                            toScalaSeq(
                                RandomLiteralExGen.int(), MaxExpression(
                                    toScalaSeq(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                                )
                            )
                        )
                    )
                )
            )
        )
        ),
        SetExpression("b", ConcatExpression(LowercaseExpression(StrExpression(VarExpression("a"))), RandomLiteralExGen.str(minLength = 10))),
        PutsExpression(
            ReplaceExpression(
                VarExpression("b"), RandomLiteralExGen.str(minLength = 10), StrExpression(
                    AddExpression(
                        toScalaSeq(
                            RandomLiteralExGen.int(),
                            SubtractExpression(RandomLiteralExGen.int(), RandomLiteralExGen.int())
                        )
                    )
                )
            )
        ),
        PutsExpression(StrExpression(EqualsExpression(VarExpression("a"), RandomLiteralExGen.int()))),
        PutsExpression(ConcatExpression(VarExpression("b"), RandomLiteralExGen.str(minLength = 10)))
    )
).map { it.toTestCase<HardTestCase>() }

@Deprecated("Do not use unless really necessary")
fun legacyEasyTestCases(): List<EasyTestCase> = listOf(
    // concatenation
    EasyTestCase(
        expressions = listOf("(puts (concat \"MyParser\" \"IsBest\"))"),
        Output(output = listOf("MyParserIsBest"))
    ),

    EasyTestCase(
        expressions = listOf("(puts \"Hello World\")"),
        Output(output = listOf("Hello World"))
    ),
    EasyTestCase(
        expressions = listOf("(puts (concat \"Hello\" \" World\"))"),
        Output(output = listOf("Hello World"))
    ),
    EasyTestCase(
        expressions = listOf(
            "(puts (uppercase \"hello\"))",
            "(puts (lowercase \"WORLD\"))"
        ),
        Output(output = listOf(
            "HELLO",
            "world"
        ))
    ),
    EasyTestCase(
        expressions = listOf("(puts (str (add 1 2)))"),
        Output(output = listOf("3"))
    ),
    EasyTestCase(
        expressions = listOf(
            "(set x 10)",
            "(puts (str x))"
        ),
        Output(output = listOf("10"))
    ),
    EasyTestCase(
        expressions = listOf("(puts (str (subtract 5 2)))"),
        Output(output = listOf("3"))
    ),
    EasyTestCase(
        expressions = listOf("(puts (str (min 3 1 4)))"),
        Output(output = listOf("1"))
    ),
    EasyTestCase(
        expressions = listOf(
            "(puts (str (equal 5 5)))",
            "(puts (str (equal 5 \"5\")))"
        ),
        Output(output = listOf(
            "true",
            "false"
        ))
    ),
    // Basic string comparison
    EasyTestCase(
        expressions = listOf("(puts (str (equal \"10.0\" \"10\")))"),
        Output(output = listOf("false"))
    ),
    EasyTestCase(
        expressions = listOf("(puts (replace \"Hello World\" \"World\" \"There\"))"),
        Output(output = listOf("Hello There"))
    ),
    // Replacement of a single character
    EasyTestCase(
        expressions = listOf("(puts (replace \"hello\" \"l\" \"x\"))"),
        Output(output = listOf("hexxo"))
    ),
    // Replacing with an empty string (deletion)
    EasyTestCase(
        expressions = listOf("(puts (replace \"hello world\" \"world\" \"\"))"),
        Output(output = listOf("hello "))
    ),
    // Replacing substring that doesn't exist
    EasyTestCase(
        expressions = listOf("(puts (replace \"hello world\" \"planet\" \"earth\"))"),
        Output(output = listOf("hello world"))
    ),
    // Comparing boolean strings with capital letters
    EasyTestCase(
        expressions = listOf("(puts (str (equal \"True\" \"true\")))"),
        Output(output = listOf("false"))
    ),
)
