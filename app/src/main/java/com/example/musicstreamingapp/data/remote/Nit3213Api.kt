package com.example.musicstreamingapp.data.remote

import com.example.musicstreamingapp.data.remote.dto.AuthRequestDto
import com.example.musicstreamingapp.data.remote.dto.AuthResponseDto
import com.example.musicstreamingapp.data.remote.dto.DashboardResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Nit3213Api {

    @POST("sydney/auth")
    suspend fun authenticate(@Body body: AuthRequestDto): AuthResponseDto

    @GET("dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): DashboardResponseDto
}
