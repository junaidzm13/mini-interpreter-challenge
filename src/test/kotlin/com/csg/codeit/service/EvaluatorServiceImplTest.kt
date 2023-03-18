package com.csg.codeit.service

import com.csg.codeit.model.LevelBasedChallenge
import com.csg.codeit.model.LiteLevel
import com.csg.codeit.model.LudicrousLevel
import com.csg.codeit.model.TestChallengeRun.Companion.asRun
import com.csg.codeit.model.TestChecker
import com.csg.codeit.model.TestRequest
import com.csg.codeit.model.TestResponse
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class EvaluatorServiceImplTest {

    @MockK
    private lateinit var levelBasedChallenge: LevelBasedChallenge

    private lateinit var service: EvaluatorServiceImpl

    @Test
    fun `can evaluate single level`() {
        service = EvaluatorServiceImpl(levelBasedChallenge, TestChecker, listOf(LiteLevel))
        every { levelBasedChallenge.createFor(any(), eq(LiteLevel)) } returns TestRequest(100)
        service.evaluateTeam(TestResponse(10).asRun).apply {
            assertThat(score).isEqualTo(10)
            assertThat(message).isEmpty()
        }
    }

    @Test
    fun `can evaluate multiple level`() {
        service = EvaluatorServiceImpl(levelBasedChallenge, TestChecker, listOf(LiteLevel, LudicrousLevel))
        every { levelBasedChallenge.createFor(any(), any()) } returns TestRequest(100)
        service.evaluateTeam(TestResponse(10).asRun).apply {
            assertThat(score).isEqualTo(20)
            assertThat(message).isEmpty()
        }
    }

    @Test
    fun `can skip failed level`() {
        service = EvaluatorServiceImpl(levelBasedChallenge, TestChecker, listOf(LiteLevel, LudicrousLevel))
        every { levelBasedChallenge.createFor(any(), eq(LiteLevel)) } returns TestRequest(100)
        every { levelBasedChallenge.createFor(any(), eq(LudicrousLevel)) } throws RuntimeException()
        service.evaluateTeam(TestResponse(10).asRun).apply {
            assertThat(score).isEqualTo(10)
            assertThat(message).isEmpty()
        }
    }
}