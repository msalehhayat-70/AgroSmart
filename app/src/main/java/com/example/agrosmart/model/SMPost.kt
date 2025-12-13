package com.example.agrosmart.model

data class SMPost(
    val name: String,
    val title: String,
    val description: String,
    val timeStamp: Long,
    val imageUrl: String?,
    val uploadType: String,
    val userID: String
)