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
            postsLiveData.value = emptyList()
            Log.e("UserProfilePostsVM", "User ID is null or empty")
            return
        }

        FirebaseFirestore.getInstance().collection("posts")
            .whereEqualTo("userID", userId)
            .get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot ->
                postsLiveData.value = querySnapshot.documents
                Log.d("UserProfilePostsVM", "Fetched ${querySnapshot.size()} posts")
            }
            .addOnFailureListener { e ->
                Log.e("UserProfilePostsVM", "Error fetching posts", e)
                postsLiveData.value = emptyList()
            }
    }
}
