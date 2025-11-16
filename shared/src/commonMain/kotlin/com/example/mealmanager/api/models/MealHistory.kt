package com.example.mealmanager.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealHistory(
    val id: String,
    @SerialName("user_id") val userId: String,
    @SerialName("meal_id") val mealId: String? = null,
    @SerialName("made_at") val madeAt: String,
    val servings: Int? = 1,
    val notes: String? = null,
    @SerialName("created_at") val createdAt: String
)