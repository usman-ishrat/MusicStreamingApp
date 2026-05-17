package com.example.musicstreamingapp.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicstreamingapp.data.remote.ApiResult
import com.example.musicstreamingapp.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    private var loginJob: Job? = null

    fun login(username: String, password: String) {
        if (_uiState.value is LoginUiState.Loading) return
        loginJob?.cancel()
        loginJob = viewModelScope.launch {
            Log.d(TAG, "login started for user=$username")
            _uiState.value = LoginUiState.Loading
            when (val result = loginUseCase(username, password)) {
                is ApiResult.Success -> {
                    Log.d(TAG, "login success keypass=${result.data}")
                    _uiState.value = LoginUiState.Success(result.data)
                }
                is ApiResult.Error -> {
                    Log.e(TAG, "login failed: ${result.message}", result.cause)
                    val message = if (result.message.contains("401") || result.message.contains("403")) {
                        "Login failed. Check username and password."
                    } else {
                        result.message
                    }
                    _uiState.value = LoginUiState.Error(message)
                }
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}
