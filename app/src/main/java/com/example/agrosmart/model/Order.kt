package com.example.agrosmart.model

data class Order(
    val name: String,
    val locality: String,
    val city: String,
    val state: String,
    val pincode: String,
    val mobile: String,
    val currentDate: String,
    val productId: String,
    val itemCost: Int,
    val quantity: Int,
    val deliveryCost: Int,
    val deliveryStatus: String
)
