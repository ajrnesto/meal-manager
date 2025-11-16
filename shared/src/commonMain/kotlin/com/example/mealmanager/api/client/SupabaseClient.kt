package com.example.mealmanager.api.client

import com.example.mealmanager.api.models.Meal
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private val baseUrl = "https://fdtfbujviuemnddmigwk.supabase.co"

    suspend fun getMeals(): List<Meal> {
        return client.get("$baseUrl/meals").body()
    }

    suspend fun getMealById(id: String): Meal {
        return client.get("$baseUrl/meals/$id").body()
    }

    suspend fun createMeal(meal: Meal): Meal {
        return client.post("$baseUrl/meals") {
            contentType(ContentType.Application.Json)
            setBody(meal)
        }.body()
    }

    fun close() {
        client.close()
    }
}