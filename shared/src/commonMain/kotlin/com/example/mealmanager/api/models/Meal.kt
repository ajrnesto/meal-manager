package com.example.mealmanager.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    val id: String,
    @SerialName("user_id") val userId: String,
    val name: String,
    val description: String?,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)