package com.example.musicstreamingapp.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponseDto(
    val keypass: String,
)
