package com.example.agrosmart.model

data class ChatMessage(
    val message: String,
    val isUser: Boolean // true = user, false = AI
)
