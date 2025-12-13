package com.example.agrosmart.model

data class MyOrder(
    val productId: String,
    val quantity: Int,
    val itemCost: Int,
    val deliveryCost: Int,
    val pincode: String,
    val deliveryStatus: String,
    val time: String
)
