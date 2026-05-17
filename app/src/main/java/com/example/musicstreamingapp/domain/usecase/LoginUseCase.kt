package com.example.musicstreamingapp.domain.usecase

import com.example.musicstreamingapp.data.remote.ApiResult
import com.example.musicstreamingapp.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(username: String, password: String): ApiResult<String> {
        val trimmedUser = username.trim()
        val trimmedPass = password.trim()
        if (trimmedUser.isEmpty() || trimmedPass.isEmpty()) {
            return ApiResult.Error("Please enter username and password.")
        }
        return authRepository.login(trimmedUser, trimmedPass)
    }
}
