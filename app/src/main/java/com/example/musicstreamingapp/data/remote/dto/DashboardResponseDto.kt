package com.example.musicstreamingapp.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DashboardResponseDto(
    val entities: List<LanguageEntityDto>,
    val entityTotal: Int,
)
