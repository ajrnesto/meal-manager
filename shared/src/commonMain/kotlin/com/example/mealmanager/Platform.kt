package com.example.mealmanager

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform