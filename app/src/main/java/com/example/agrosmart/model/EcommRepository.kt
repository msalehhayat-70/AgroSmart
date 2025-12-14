package com.example.agrosmart.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class EcommRepository {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _product = MutableLiveData<Product?>()
    val product: LiveData<Product?> = _product

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    fun getAllProducts() {
        // Placeholder data
        val placeholderProducts = listOf(
            Product("1", "Organic Fertilizer", 469, 50, "Agro Retailers", "In Stock", listOf("")),
            Product("2", "Pesticide Spray", 799, 60, "Farm Essentials", "In Stock", listOf(""))
        )
        _products.postValue(placeholderProducts)
    }

    fun getProductById(id: String) {
        // Placeholder data
        val placeholderProduct = Product(id, "Organic Fertilizer", 469, 50, "Agro Retailers", "In Stock", listOf(""))
        _product.postValue(placeholderProduct)
    }

    fun getCartItems() {
        // Placeholder data
        val placeholderCartItems = listOf(
            CartItem("1", 2),
            CartItem("2", 1)
        )
        _cartItems.postValue(placeholderCartItems)
    }
}
