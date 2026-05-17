package com.example.musicstreamingapp.domain.usecase

import com.example.musicstreamingapp.data.remote.ApiResult
import com.example.musicstreamingapp.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LoginUseCaseTest {

    private val authRepository: AuthRepository = mockk()
    private val loginUseCase = LoginUseCase(authRepository)

    @Test
    fun invoke_blankCredentials_returnsValidationError() = runTest {
        val result = loginUseCase("  ", "")

        assertTrue(result is ApiResult.Error)
        assertEquals("Please enter username and password.", (result as ApiResult.Error).message)
    }

    @Test
    fun invoke_validCredentials_delegatesToRepository() = runTest {
        coEvery { authRepository.login("s8170807", "Usman") } returns ApiResult.Success("languages")

        val result = loginUseCase("s8170807", "Usman")

        assertTrue(result is ApiResult.Success)
        assertEquals("languages", (result as ApiResult.Success).data)
        coVerify { authRepository.login("s8170807", "Usman") }
    }
}
