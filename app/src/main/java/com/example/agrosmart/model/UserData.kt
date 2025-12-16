package com.example.agrosmart.model

data class UserData(
    val name: String = "",
    val email: String = "",
    val city: String = "",
    val profileImage: String = "",
    val backImage: String = "",
    val about: String = "",
    val posts: List<String> = emptyList()
)
