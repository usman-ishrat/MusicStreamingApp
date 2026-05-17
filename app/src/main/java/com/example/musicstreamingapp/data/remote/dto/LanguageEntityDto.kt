package com.example.musicstreamingapp.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LanguageEntityDto(
    val name: String? = null,
    val family: String? = null,
    val branch: String? = null,
    val speakers: Long? = null,
    @Json(name = "writingSystem") val writingSystem: String? = null,
    val officialIn: List<String>? = null,
    val description: String? = null,
)
