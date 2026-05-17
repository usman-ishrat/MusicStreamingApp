package com.example.musicstreamingapp.data.repository

import com.example.musicstreamingapp.data.remote.ApiResult
import com.example.musicstreamingapp.data.remote.Nit3213Api
import com.example.musicstreamingapp.data.remote.dto.AuthRequestDto
import com.example.musicstreamingapp.data.remote.safeApiCall
import com.example.musicstreamingapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: Nit3213Api,
) : AuthRepository {

    override suspend fun login(username: String, password: String): ApiResult<String> {
        return when (val result = safeApiCall {
            api.authenticate(AuthRequestDto(username, password))
        }) {
            is ApiResult.Success -> ApiResult.Success(result.data.keypass)
            is ApiResult.Error -> result
        }
    }
}
