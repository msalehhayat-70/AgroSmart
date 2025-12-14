package com.example.agrosmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.agrosmart.model.CartItem
import com.example.agrosmart.model.EcommRepository
import com.example.agrosmart.model.Product

class EcommViewModel : ViewModel() {

    private val repository = EcommRepository()

    val products: LiveData<List<Product>> = repository.products
    val product: LiveData<Product?> = repository.product
    private val cartItems: LiveData<List<CartItem>> = repository.cartItems

    val cartItemsWithProducts = MediatorLiveData<List<Pair<CartItem, Product>>>()

    init {
        cartItemsWithProducts.addSource(cartItems) { items ->
            products.value?.let { prods ->
                processCartItems(items, prods)
            }
        }
        cartItemsWithProducts.addSource(products) { prods ->
            cartItems.value?.let { items ->
                processCartItems(items, prods)
            }
        }
    }

    fun loadAllEcommItems() {
        repository.getAllProducts()
    }

    fun getProductById(id: String) {
        repository.getProductById(id)
    }

    fun getCartItems() {
        repository.getCartItems()
        repository.getAllProducts()
    }

    private fun processCartItems(cartItems: List<CartItem>, products: List<Product>) {
        val productsById = products.associateBy { it.id }
        val detailedCartItems = cartItems.mapNotNull { cartItem ->
            productsById[cartItem.key]?.let { product ->
                Pair(cartItem, product)
            }
        }
        cartItemsWithProducts.postValue(detailedCartItems)
    }
}
