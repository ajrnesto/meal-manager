package com.example.mealmanager.api.repository

import com.example.mealmanager.api.client.SupabaseAuthClient
import com.example.mealmanager.api.models.AuthResponse
import com.example.mealmanager.api.models.User

class AuthRepository(private val authClient: SupabaseAuthClient) {

    suspend fun signUp(email: String, password: String): ApiResult<AuthResponse> = try {
        ApiResult.Success(authClient.signUp(email, password))
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "Sign up failed")
    }

    suspend fun signIn(email: String, password: String): ApiResult<AuthResponse> = try {
        ApiResult.Success(authClient.signIn(email, password))
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "Sign in failed")
    }

    suspend fun signOut(accessToken: String): ApiResult<Unit> = try {
        authClient.signOut(accessToken)
        ApiResult.Success(Unit)
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "Sign out failed")
    }

    suspend fun refreshToken(refreshToken: String): ApiResult<AuthResponse> = try {
        ApiResult.Success(authClient.refreshToken(refreshToken))
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "Token refresh failed")
    }

    suspend fun getCurrentUser(accessToken: String): ApiResult<User> = try {
        ApiResult.Success(authClient.getUser(accessToken))
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "Failed to get user")
    }
}

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>()
}