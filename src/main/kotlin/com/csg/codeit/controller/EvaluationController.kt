package com.csg.codeit.controller

import com.csg.codeit.model.EvaluationRequest
import com.csg.codeit.service.CoordinatorService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EvaluationController(private val coordinatorService: CoordinatorService) {
    val coroutineScope = CoroutineScope(Dispatchers.Default)

    @PostMapping(value = ["/evaluate"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun evaluate(@RequestBody evaluationRequest: EvaluationRequest): ResponseEntity<Void> =
        ResponseEntity<Void>(HttpStatus.ACCEPTED).also { coroutineScope.launch { coordinatorService(evaluationRequest) } }
}