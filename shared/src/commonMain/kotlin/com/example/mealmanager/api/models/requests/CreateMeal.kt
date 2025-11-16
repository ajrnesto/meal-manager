package com.example.mealmanager.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateMeal(
    val name: String,
    val description: String? = null
)