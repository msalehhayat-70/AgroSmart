package com.example.agrosmart.model

data class Product(
    val id: String,
    val title: String,
    val price: Int,
    val delCharge: Int,
    val retailer: String,
    val availability: String,
    val imageUrl: List<String>
)
