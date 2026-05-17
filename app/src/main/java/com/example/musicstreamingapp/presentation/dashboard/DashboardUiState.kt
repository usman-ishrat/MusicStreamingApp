package com.example.musicstreamingapp.presentation.dashboard

import com.example.musicstreamingapp.domain.model.Dashboard

sealed class DashboardUiState {
    data object Idle : DashboardUiState()
    data object Loading : DashboardUiState()
    data class Success(val dashboard: Dashboard) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}
