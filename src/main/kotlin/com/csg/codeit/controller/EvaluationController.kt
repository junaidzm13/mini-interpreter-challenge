package com.csg.codeit.controller

import com.csg.codeit.*
import com.csg.codeit.checker.EasyTestCase
import com.csg.codeit.checker.IntermediateTestCase
import com.csg.codeit.checker.TestCase
import com.csg.codeit.checker.easyTestCases
import com.csg.codeit.expression.toTestCase
import com.csg.codeit.model.EvaluationRequest
import com.csg.codeit.model.Output
import com.csg.codeit.service.CoordinatorService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EvaluationController(private val coordinatorService: CoordinatorService) {
    val coroutineScope = CoroutineScope(Dispatchers.Default)

    @PostMapping(value = ["/evaluate"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun evaluate(@RequestBody evaluationRequest: EvaluationRequest): ResponseEntity<Void> =
        ResponseEntity<Void>(HttpStatus.ACCEPTED).also { coroutineScope.launch { coordinatorService(evaluationRequest) } }

    @GetMapping(value = ["/examples"])
    fun example(): ResponseEntity<List<TestCase>> =
        ResponseEntity<List<TestCase>>(
            listOf(
                listOf(
                    SetExpression("x", IntExpression(15)),
                    PutsExpression(StrExpression(SubtractExpression(VarExpression("x"), IntExpression(5))))
                ),
                listOf(PutsExpression(ConcatExpression(StringExpression("Hello"), StringExpression(" World!")))),
                listOf(PutsExpression(StrExpression(EqualsExpression(StringExpression("10.5"), StringExpression("10")))))
            ).map {it.toTestCase<EasyTestCase>() },
            HttpStatus.OK
        )
}