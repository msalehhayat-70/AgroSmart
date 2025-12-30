package com.example.agrosmart.model

// WARNING: Storing plain text passwords is a major security risk.
data class User(
    val name: String = "",
    val email: String = "",
    val city: String = "",
    val password: String = "",
    val profileImageString: String = ""
)
