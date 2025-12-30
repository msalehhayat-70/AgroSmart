package com.example.agrosmart.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class UserDataViewModel : ViewModel() {

    var userliveData = MutableLiveData<DocumentSnapshot?>()

    fun getUserData(userId: String) {
        val firebaseFireStore = FirebaseFirestore.getInstance()

        firebaseFireStore.collection("users").document(userId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userliveData.value = task.result
                } else {
                    Log.e("UserDataViewModel", "Error getting user data", task.exception)
                    userliveData.value = null // Notify observers of failure
                }
            }
    }

    fun updateUserField(context: Context, userID: String, about: String?, city: String?) {
        val fieldsToUpdate = mutableMapOf<String, Any>()
        about?.let { fieldsToUpdate["about"] = it }
        city?.let { fieldsToUpdate["city"] = it }

        if (fieldsToUpdate.isNotEmpty()) {
            FirebaseFirestore.getInstance().collection("users").document(userID)
                .update(fieldsToUpdate)
                .addOnSuccessListener {
                    Log.d("UserDataViewModel", "User data updated successfully")
                    getUserData(userID) // Refresh the user data
                    Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.e("UserDataViewModel", "Failed to update user data", e)
                    Toast.makeText(context, "Failed to update profile. Please try again!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun deleteUserPost(userId: String, postId: String) {
        val firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("posts").document(postId)
            .delete()
            .addOnSuccessListener {
                Log.d("UserDataViewModel", "Post document deleted successfully")
                // Now, remove the post ID from the user's 'posts' array
                firebaseFirestore.collection("users").document(userId)
                    .update("posts", FieldValue.arrayRemove(postId))
                    .addOnSuccessListener {
                        Log.d("UserDataViewModel", "Post ID removed from user's posts array")
                        // Refresh the user data to update post count, etc.
                        getUserData(userId)
                        // The UserFragment is responsible for refreshing the actual post list.
                    }
                    .addOnFailureListener { e ->
                        Log.e("UserDataViewModel", "Failed to remove post ID from user's array", e)
                    }
            }
            .addOnFailureListener { e ->
                Log.e("UserDataViewModel", "Failed to delete post document", e)
            }
    }
}
