package com.example.musicstreamingapp.data.repository

import com.example.musicstreamingapp.data.mapper.EntityMapper
import com.example.musicstreamingapp.data.remote.ApiResult
import com.example.musicstreamingapp.data.remote.Nit3213Api
import com.example.musicstreamingapp.data.remote.safeApiCall
import com.example.musicstreamingapp.domain.model.Dashboard
import com.example.musicstreamingapp.domain.repository.DashboardRepository
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val api: Nit3213Api,
) : DashboardRepository {

    override suspend fun getDashboard(keypass: String): ApiResult<Dashboard> {
        return when (val result = safeApiCall { api.getDashboard(keypass) }) {
            is ApiResult.Success -> {
                val response = result.data
                val entities = response.entities.mapIndexed { index, dto ->
                    EntityMapper.toDomain(dto, index)
                }
                ApiResult.Success(
                    Dashboard(
                        entities = entities,
                        entityTotal = response.entityTotal,
                        topicKey = keypass,
                    ),
                )
            }
            is ApiResult.Error -> result
        }
    }
}
