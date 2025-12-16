package com.example.agrosmart.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class UserProfilePostsViewModel : ViewModel() {

    val postsLiveData = MutableLiveData<List<DocumentSnapshot>>()

    fun getAllPosts(userId: String?) {
        if (userId.isNullOrEmpty()) {
            Log.e("UserPrlPostsViewModel", "User ID is null or empty.")
            postsLiveData.value = emptyList()
            return
        }

        val firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("posts").whereEqualTo("userID", userId)
            .get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot ->
                postsLiveData.value = querySnapshot.documents
                Log.d("UserPrlPostsViewModel", "Updated data with ${querySnapshot.size()} documents.")
            }
            .addOnFailureListener { exception ->
                Log.e("UserPrlPostsViewModel", "Error getting documents: ", exception)
                postsLiveData.value = emptyList()
            }
    }
}
