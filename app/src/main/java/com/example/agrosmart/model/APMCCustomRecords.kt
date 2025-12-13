package com.example.agrosmart.model

data class APMCCustomRecords(
    val market: String,
    val district: String,
    val state: String,
    val commodity: List<String>,
    val min_price: List<String>,
    val max_price: List<String>
)