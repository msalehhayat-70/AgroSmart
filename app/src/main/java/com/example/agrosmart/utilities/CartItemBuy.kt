package com.example.agrosmart.utilities

interface CartItemBuy {
    fun addToOrders(productId: String, quantity: Int, itemCost: Int, deliveryCost: Int)
    fun removeItem(productId: String)
    fun updateQuantity(productId: String, newQuantity: Int)
}
