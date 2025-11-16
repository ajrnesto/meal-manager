package com.example.mealmanager.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateInventory(
    val quantity: Double,
    val unit: String? = null,
    val location: String? = null
)