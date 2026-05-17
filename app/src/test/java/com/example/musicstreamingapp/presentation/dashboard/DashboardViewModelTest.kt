package com.example.musicstreamingapp.presentation.dashboard

import com.example.musicstreamingapp.data.remote.ApiResult
import com.example.musicstreamingapp.domain.model.Dashboard
import com.example.musicstreamingapp.domain.model.Entity
import com.example.musicstreamingapp.domain.usecase.GetDashboardUseCase
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
class DashboardViewModelTest {

    private val getDashboardUseCase: GetDashboardUseCase = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(getDashboardUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadDashboard_success_emitsEntities() = runTest {
        val entity = Entity(
            id = 0,
            title = "English",
            subtitle = "Indo-European",
            summaryFields = mapOf("name" to "English"),
            allFields = mapOf("name" to "English"),
            description = "Desc",
        )
        val dashboard = Dashboard(listOf(entity), 1, "languages")
        coEvery { getDashboardUseCase("languages") } returns ApiResult.Success(dashboard)

        viewModel.loadDashboard("languages")

        val state = viewModel.uiState.value
        assertTrue(state is DashboardUiState.Success)
        assertEquals(1, (state as DashboardUiState.Success).dashboard.entities.size)
    }

    @Test
    fun loadDashboard_failure_emitsError() = runTest {
        coEvery { getDashboardUseCase(any()) } returns ApiResult.Error("Network error")

        viewModel.loadDashboard("languages")

        val state = viewModel.uiState.value
        assertTrue(state is DashboardUiState.Error)
        assertEquals("Network error", (state as DashboardUiState.Error).message)
    }
}
