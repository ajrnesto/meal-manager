package com.example.mealmanager.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateIngredient(
    val name: String,
    val unit: String? = null
)