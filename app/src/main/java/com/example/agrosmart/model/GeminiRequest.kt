package com.example.agrosmart.model

data class GeminiRequest(
    val messages: List<GeminiMessage>,
    val temperature: Double = 0.7
)

data class GeminiMessage(
    val role: String, // "user" or "system"
    val content: String
)
