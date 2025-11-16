package com.example.mealmanager.api.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SupabaseError(
    val code: Int? = null,
    @SerialName("error_code") val errorCode: String? = null,
    val msg: String
)

class SupabaseException(message: String) : Exception(message)