package com.example.musicstreamingapp.domain.usecase

import com.example.musicstreamingapp.data.remote.ApiResult
import com.example.musicstreamingapp.domain.model.Dashboard
import com.example.musicstreamingapp.domain.repository.DashboardRepository
import javax.inject.Inject

class GetDashboardUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository,
) {
    suspend operator fun invoke(keypass: String): ApiResult<Dashboard> {
        if (keypass.isBlank()) {
            return ApiResult.Error("Missing session. Please log in again.")
        }
        return dashboardRepository.getDashboard(keypass)
    }
}
