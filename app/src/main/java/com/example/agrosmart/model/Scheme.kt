package com.example.agrosmart.model

data class Scheme(
    val title: String,
    val status: String,
    val launch: String,
    val image: String?,
    val description: String,
    val budget: String,
    val headedBy: String,
    val eligibility: List<String>,
    val objectives: List<String>,
    val documents: List<String>
)
