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

        val placeholderOrders = listOf(
            MyOrder("1", 2, 469, 50, "1", "Shipped", "01/01/2024"),
            MyOrder("2", 1, 799, 60, "2", "Delivered", "02/01/2024")
        )

        val placeholderProducts = listOf(
            Product(
                "1",
                "Organic Fertilizer",
                469,
                50,
                "Agro Retailers",
                "In Stock",
                listOf(""),
                4.5f   // ✅ rating added
            ),
            Product(
                "2",
                "Pesticide Spray",
                799,
                60,
                "Farm Essentials",
                "In Stock",
                listOf(""),
                4.0f   // ✅ rating added
            )
        )

        val productsById = placeholderProducts.associateBy { it.id }

        val detailedOrders = placeholderOrders.mapNotNull { order ->
            productsById[order.productId]?.let { product ->
                Pair(order, product)
            }
        }

        _myOrdersWithProducts.postValue(detailedOrders)
    }
}
