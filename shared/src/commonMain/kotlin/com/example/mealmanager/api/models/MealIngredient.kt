package com.example.mealmanager.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealIngredient(
    @SerialName("meal_id") val mealId: String,
    @SerialName("ingredient_id") val ingredientId: String,
    val quantity: Double? = null,
    val unit: String? = null,
    val notes: String? = null,
    @SerialName("added_at") val addedAt: String? = null
)