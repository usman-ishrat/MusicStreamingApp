package com.example.musicstreamingapp.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicstreamingapp.data.remote.ApiResult
import com.example.musicstreamingapp.domain.usecase.GetDashboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardUseCase: GetDashboardUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Idle)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private var currentKeypass: String? = null

    fun loadDashboard(keypass: String) {
        currentKeypass = keypass
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading
            when (val result = getDashboardUseCase(keypass)) {
                is ApiResult.Success -> _uiState.value = DashboardUiState.Success(result.data)
                is ApiResult.Error -> _uiState.value = DashboardUiState.Error(result.message)
            }
        }
    }

    fun retry() {
        currentKeypass?.let { loadDashboard(it) }
    }
}
