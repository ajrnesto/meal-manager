package com.example.mealmanager.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Inventory(
    @SerialName("user_id") val userId: String,
    @SerialName("ingredient_id") val ingredientId: String,
    val quantity: Double,
    val unit: String? = null,
    @SerialName("updated_at") val updatedAt: String,
    val location: String? = null
)