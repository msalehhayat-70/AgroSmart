package com.example.agrosmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class SocialMediaViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    fun getUserProfileImage(userId: String): LiveData<String?> {
        val profileImage = MutableLiveData<String?>()

        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                profileImage.value = document.getString("profileImage")
            }
            .addOnFailureListener {
                profileImage.value = null
            }

        return profileImage
    }
}
