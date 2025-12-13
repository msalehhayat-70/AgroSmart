package com.example.agrosmart.model

data class EcommItem(
    val id: String,
    val title: String,
    val price: String,
    val retailer: String,
    val availability: String,
    val imageUrl: List<String>,
    val rating: Float
)