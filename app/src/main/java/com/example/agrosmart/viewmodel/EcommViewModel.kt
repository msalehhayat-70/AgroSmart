package com.example.agrosmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class EcommViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun getSpecificItem(itemId: String): LiveData<DocumentSnapshot> {
        val item = MutableLiveData<DocumentSnapshot>()
        firestore.collection("products").document(itemId).get()
            .addOnSuccessListener {
                item.value = it
            }
        return item
    }

    fun removeCartItem(itemId: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.getReference(userId).child("cart").child(itemId).removeValue()
        }
    }

    fun updateCartItemQuantity(itemId: String, quantity: Int) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.getReference(userId).child("cart").child(itemId).child("quantity").setValue(quantity)
        }
    }
}