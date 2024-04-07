package me.abhigya.model

import kotlinx.serialization.Serializable

@Serializable
data class ShopItem(
    val id: Int,
    var name: String,
    var artist: Artist,
    var description: String,
    var price: Double,
    val art1: String,
    val art2: String,
)