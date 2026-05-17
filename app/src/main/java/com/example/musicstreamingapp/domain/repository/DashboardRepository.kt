package com.example.musicstreamingapp.domain.repository

import com.example.musicstreamingapp.data.remote.ApiResult
import com.example.musicstreamingapp.domain.model.Dashboard

interface DashboardRepository {
    suspend fun getDashboard(keypass: String): ApiResult<Dashboard>
}
