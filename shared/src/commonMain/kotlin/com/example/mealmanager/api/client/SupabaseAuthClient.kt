package com.example.mealmanager.api.client

import com.example.mealmanager.api.models.AuthResponse
import com.example.mealmanager.api.models.RefreshTokenRequest
import com.example.mealmanager.api.models.SignInRequest
import com.example.mealmanager.api.models.SignUpRequest
import com.example.mealmanager.api.models.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class SupabaseAuthClient(
    private val supabaseUrl: String,
    private val supabaseKey: String
) {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    private val authUrl = "$supabaseUrl/auth/v1"

    private fun HttpRequestBuilder.applyHeaders() {
        headers {
            append("apikey", supabaseKey)
            append("Content-Type", "application/json")
        }
    }

    suspend fun signUp(email: String, password: String): AuthResponse {
        val response = client.post("$authUrl/signup") {
            applyHeaders()
            setBody(SignUpRequest(email, password))
        }

        val bodyText = response.bodyAsText()
        println("Supabase AuthClient - signUp Response: $bodyText")

        if (!response.status.isSuccess()) {
            val error = Json.decodeFromString<SupabaseError>(bodyText)
            println("Supabase AuthClient - signUp Json Decoder: $error")
            throw SupabaseException(error.msg)
        }

        return response.body()
    }

    suspend fun signIn(email: String, password: String): AuthResponse {
        val response = client.post("$authUrl/token?grant_type=password") {
            applyHeaders()
            setBody(SignInRequest(email, password))
        }

        val bodyText = response.bodyAsText()
        println("Supabase AuthClient - signIn Response: $bodyText")

        if (!response.status.isSuccess()) {
            val error = Json.decodeFromString<SupabaseError>(bodyText)
            println("Supabase AuthClient - signIn Json Decoder: $error")
            throw SupabaseException(error.msg)
        }

        return response.body()
    }

    suspend fun signOut(accessToken: String) {
        client.post("$authUrl/logout") {
            applyHeaders()
            headers {
                append(HttpHeaders.Authorization, "Bearer $accessToken")
            }
        }
    }

    suspend fun refreshToken(refreshToken: String): AuthResponse {
        return client.post("$authUrl/token?grant_type=refresh_token") {
            applyHeaders()
            setBody(RefreshTokenRequest(refreshToken))
        }.body()
    }

    suspend fun getUser(accessToken: String): User {
        return client.get("$authUrl/user") {
            applyHeaders()
            headers {
                append(HttpHeaders.Authorization, "Bearer $accessToken")
            }
        }.body()
    }

    fun close() {
        client.close()
    }
}