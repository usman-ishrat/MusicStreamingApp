package com.example.musicstreamingapp.domain.repository

import com.example.musicstreamingapp.data.remote.ApiResult

interface AuthRepository {
    suspend fun login(username: String, password: String): ApiResult<String>
}
