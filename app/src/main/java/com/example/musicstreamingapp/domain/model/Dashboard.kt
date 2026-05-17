package com.example.musicstreamingapp.domain.model

data class Dashboard(
    val entities: List<Entity>,
    val entityTotal: Int,
    val topicKey: String,
)
