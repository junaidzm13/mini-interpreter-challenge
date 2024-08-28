# Test cases
## Easy

### Simple printing

```
(puts "Hello World")
```

Expected output:

```
Hello World
```

### String concatenation

```
(puts (concat "Hello" " World"))
```

Expected output:
```
Hello world
```

### String case conversion

```
(puts (uppercase "hello"))
(puts (lowercase "WORLD"))
```

Expected output:

```
HELLO
world
```

### Simple Arithmetic

```
(puts (str (add 1 2)))

```

Expected output:

```
3
```

### Setting a constant and using it

```
(set x 10)
(puts (str x))
```

Expected value:

```
10
```


### Simple subtraction

```
(puts (str (subtract 5 2)))
```

Expected value:

`3`

### Minimum of numbers:

```
(puts (str (min 3 1 4)))
```

Expected value:
```
1
```

### Equality check

```
(puts (str (equal 5 5)))
(puts (str (equal 5 "5")))
```

Expected value:

```
true
false
```

### Division operation

```
(puts (str (divide 10 2)))
```

Expected value:
```
5
```

### String replacement

```
(puts (replace "Hello World" "World" "There"))
```

Expected value:
```
Hello There
```


## Medium difficulty Test cases

### Nested function calls

```
(puts (str (add (subtract 10 5) 20)))
```

Expected value:
```
25
```

### Division by Zero error:

```
(puts (str (divide 10 2)))
(puts (str (divide 5 0)))
```

Expected value:
```
5
ERROR at line 2

```

### Type error in `puts`

```
(puts 5)
```

Expected value:

```
Error at line 1
```

### Using a constant in calculation

```
(set x 15)
(puts (str (subtract x 5)))
```

Expected output:

```
10
```

### String concatenation with type conversion

```
(puts (concat "The result is: " (str (add 5 5))))
```

Expected output:

```
The result is: 10
```

### Substring with Valid indices

```
(puts (substring "abcdef" 2 5))
```

Expected output:

```
cde
```

### Substring with out of bound index

```
(puts (substring "abcdef" 2 10))
```

Expected output:

```
Error at line 1
```

### Reassinging a constant error

```
(set x 10)
(set x 20)
```

Expected output:

```
ERROR at line 2
```

### Logical comparison

```
(puts (str (gt 10 5)))
(puts (str (lt 5 10)))
(puts (str (gt 5 10)))
```

Expected output:

```
true
true
false
```

### Mixed operations with error handling

```
(set x 10)
(puts (str (add x 5)))
(puts (str (divide x 0)))
(puts "This line should not be printed")
```

Expected output:

```
15
ERROR at line 3
```

### Concatenation and case conversion

```
(puts (uppercase (concat "hello" (lowercase "WORLD"))))
```

Expected output:

```
HELLOworld
```

### Maximum and minimum value calculation

```
(puts (str (max 3 9 2 5)))
(puts (str (min 3 9 2 5)))
```

Expected output:

```
9
2
```

### Boolean and null handling

```
(puts (str (equal true true)))
(puts (str (equal null null)))
(puts (str (not_equal null 0)))
```

Expected output:

```
true
true
true
```

## Hard

### Simulated error message with continued exection

```
(puts "ERROR at line 3")
(set a (add 10 5))
(puts (str a))
```

Expected output

```
ERROR at line 3
15
```

### Nested operations with type conversion

```
(puts (concat (str (add (subtract 20 10) (multiply 2 3))) " is the result"))
```

Expected output:

```
16 is the result
```

### Multiple errors in one case

```
(puts (str (divide 10 2)))
(puts (str (divide 10 0)))
(puts (add 5 "5"))
(puts (subtract 5))
```

Expected output:

```
5
ERROR at line 2
```

### Substring edge case and error

```
(puts (substring "abcdef" 0 6))
(puts (substring "abcdef" 2 10))
```

Expected output:

```
abcdef
ERROR at line 2
```

### Deeply nested function calls

```
(puts (str (add (subtract (multiply (divide 100 2) (add 10 5)) 25) 10)))
```

```
735
```

### Error after valid operations

```
(set x 50)
(puts (str (add x 10)))
(puts (substring "hello" 1 3))
(set x 100)
```

Expected output:

```
60
el
ERROR at line 4
```

### Concatenation with case conversion and replacement

```
(puts (replace (concat (uppercase "hello") (lowercase "WORLD")) "LO" "XY"))
```

```
HEXYLOWORLD
```

### Logical and arithmetic error combination

```
(puts (str (equal (add 5 5) 10)))
(puts (str (gt (add 5 "5") 10)))
```

Expected output:

```
true
ERROR at line 2
```

### Complex boolean and arithmetic operations

```
(puts (str (not_equal (abs (subtract 10 20)) 10)))
(puts (str (gt (multiply 2 3 4) 20)))
(puts (str (lt (divide 100 5) (multiply 3 3))))
```

Expected output

```
false
true
false
```

### String replacement with special characters -> consider if we want to keep this test case

```
(puts (replace "a*b*c" "*" "-"))
(puts (replace "hello" "l" "L"))
```

Expected output

```
a-b-c
heLLo
```

### Reassigning and using a variable

```
(set y 20)
(puts (str (add y 10)))
(set y 30)
(puts (str y))
```

Expected output:

```
30
ERROR at line 3

```

## Even harder test cases

### Complex string manipulation with multiple nested function

```
(set x (concat (uppercase "harder") (lowercase "TEST")))
(set y (replace x "ER" "XY"))
(set z (substring y 0 8))
(puts (str z))
(puts (concat z "ing"))
(set errorTest (concat x 123))
```

Expected output

```
HARDXYTE
HARDXYTEing
ERROR at line 6
```

### Interdependent variables with cascading errors

```
(set a 100)
(set b (divide a 5))
(set c (subtract b (multiply 2 10)))
(puts (str c))
(set d (add c (divide 10 0)))
(puts (str d))
(set e (add d 50))
(puts (str e))
```

Expected output:

```
60
ERROR at line 5
```

### Complex variable interaction with type mismatch error

```
(set x (add 10 20))
(set y (multiply x 2))
(set z (subtract y (divide 100 10)))
(set result (concat "Result is: " (str z)))
(puts result)
(set invalidOperation (add result 10))
(puts (str invalidOperation))
```

Expected output:

```
Result is: 50
ERROR at line 6
```

## Needs verification
### Deeply nested operations with multiple data types and errors

```
(set a (add 100 (multiply (subtract 200 (divide 100 5)) (add 10 (max 1 2 3)))))
(set b (concat (uppercase (str (subtract a (divide 100 0)))) " TEST"))
(set c (subtract (multiply (add 50 50) (max (subtract 20 5) 10)) (min 30 40)))
(set d (replace b "100" (str c)))
(puts (str c))
(puts (lowercase (concat d " ERROR?")))
(set e (divide c (multiply (subtract c (divide 100 5)) 0)))
(puts (str e))
```

Expected output

```
ERROR at line 2
```

### Extreme variable dependency and function nesting with type errors

```
(set x (str (add 100 (subtract 200 (multiply 3 (divide 300 10))))))
(set y (concat (lowercase x) " complex"))
(set z (replace y "300" (str (divide 900 3))))
(set a (max (min (subtract 1000 200) (multiply 2 3)) (divide 1000 10)))
(set b (subtract a (add (multiply 2 3) (divide (add (multiply 100 2) (subtract 200 100)) 5))))
(puts (str (gt a b)))
(puts (concat "Result: " (uppercase z)))
(set invalidOp (add z a))
(puts (str invalidOp))
(set finalResult (divide invalidOp (min (abs b) 0)))
(puts (str finalResult))
```

Expected output

```
true
Result: 1000 complex
ERROR at line 10
```

### Interwoven Logical, Arithmetic, and String Operations with Multiple Layers of Errors

```
(set a (add 100 (subtract 200 (divide 300 10))))
(set b (concat (uppercase (str a)) " Complex"))
(set c (replace b "300" (str (divide (add (multiply 2 3) (subtract 500 100)) 5))))
(set d (multiply (subtract (max 100 200) (min 10 20)) (abs (subtract 500 (divide 100 0)))))
(puts (str (equal (subtract d (multiply a 2)) 1000)))
(set e (concat (lowercase (str d)) " Nightmare"))
(puts e)
(set f (add (str d) (uppercase e)))
(puts (str f))
(set g (divide (subtract f a) (add (multiply (subtract 100 50) (divide 200 0)) 10)))
(puts (str g))
```

```
ERROR at line 4
```