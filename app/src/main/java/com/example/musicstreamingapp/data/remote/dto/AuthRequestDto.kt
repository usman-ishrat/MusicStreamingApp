package com.example.musicstreamingapp.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthRequestDto(
    val username: String,
    val password: String,
)
