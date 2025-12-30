package com.example.agrosmart.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * Signup: Store user data in Firestore
     * @param email User email (used as document ID)
     * @param password User password (stored in Firestore)
     * @param data Other user details as HashMap
     * @return LiveData<String> with result ("Success" or error message)
     */
    fun signUpUser(email: String, password: String, data: HashMap<String, Any>): LiveData<String> {
        val result = MutableLiveData<String>()

        // Make sure password is included in the data map
        data["password"] = password

        firestore.collection("users")
            .document(email) // Using email as document ID
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    result.value = "User already exists"
                } else {
                    firestore.collection("users")
                        .document(email)
                        .set(data)
                        .addOnSuccessListener { result.value = "Success" }
                        .addOnFailureListener { e ->
                            result.value = e.message ?: "Signup failed"
                        }
                }
            }
            .addOnFailureListener { e ->
                result.value = e.message ?: "Error accessing Firestore"
            }

        return result
    }

    /**
     * Login: Check email and password in Firestore
     * @param email User email
     * @param password User password
     * @return LiveData<String> with result ("Success" or error message)
     */
    fun loginUser(email: String, password: String): LiveData<String> {
        val result = MutableLiveData<String>()

        firestore.collection("users")
            .document(email)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val storedPassword = doc.getString("password")
                    if (storedPassword == password) {
                        result.value = "Success"
                    } else {
                        result.value = "Incorrect password"
                    }
                } else {
                    result.value = "User does not exist"
                }
            }
            .addOnFailureListener { e ->
                result.value = e.message ?: "Error accessing Firestore"
            }

        return result
    }
}
