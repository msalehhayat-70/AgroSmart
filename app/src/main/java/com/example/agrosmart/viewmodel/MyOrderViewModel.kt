package com.example.agrosmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agrosmart.model.MyOrder
import com.example.agrosmart.model.Product

class MyOrderViewModel : ViewModel() {

    private val _myOrdersWithProducts = MutableLiveData<List<Pair<MyOrder, Product>>>()
    val myOrdersWithProducts: LiveData<List<Pair<MyOrder, Product>>> = _myOrdersWithProducts

    fun loadMyOrders() {
        // Placeholder data. In a real app, this would come from a repository.
        val placeholderOrders = listOf(
            MyOrder("1", 2, 469, 50, "123456", "Shipped", "01/01/2024"),
            MyOrder("2", 1, 799, 60, "654321", "Delivered", "02/01/2024")
        )
        val placeholderProducts = listOf(
            Product("1", "Organic Fertilizer", 469, 50, "Agro Retailers", "In Stock", listOf("")),
            Product("2", "Pesticide Spray", 799, 60, "Farm Essentials", "In Stock", listOf(""))
        )

        val productsById = placeholderProducts.associateBy { it.id }
        val detailedOrders = placeholderOrders.mapNotNull { order ->
            productsById[order.productId]?.let {
                Pair(order, it)
            }
        }
        _myOrdersWithProducts.postValue(detailedOrders)
    }
}
