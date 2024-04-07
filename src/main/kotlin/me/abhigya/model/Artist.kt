package me.abhigya.model

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: Int,
    val name: String,
    val bio: String
)