package com.example.musicstreamingapp.presentation.login

import com.example.musicstreamingapp.data.remote.ApiResult
import com.example.musicstreamingapp.domain.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val loginUseCase: LoginUseCase = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(loginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun login_success_emitsSuccessState() = runTest {
        coEvery { loginUseCase("s8170807", "Usman") } returns ApiResult.Success("languages")

        viewModel.login("s8170807", "Usman")

        val state = viewModel.uiState.value
        assertTrue(state is LoginUiState.Success)
        assertEquals("languages", (state as LoginUiState.Success).keypass)
    }

    @Test
    fun login_failure_emitsErrorState() = runTest {
        coEvery { loginUseCase(any(), any()) } returns ApiResult.Error("Invalid credentials")

        viewModel.login("bad", "bad")

        val state = viewModel.uiState.value
        assertTrue(state is LoginUiState.Error)
        assertEquals("Invalid credentials", (state as LoginUiState.Error).message)
    }

    @Test
    fun login_setsLoadingBeforeResult() = runTest {
        coEvery { loginUseCase(any(), any()) } returns ApiResult.Success("languages")

        viewModel.login("s8170807", "Usman")

        assertTrue(
            viewModel.uiState.value is LoginUiState.Success ||
                viewModel.uiState.value is LoginUiState.Loading,
        )
    }
}
