package com.example.agrosmart.model

data class CartItem(
    val productId: String = "",
    val title: String = "",
    val price: String = "",
    val imageUrl: String = "",
    val quantity: Int = 0
)
