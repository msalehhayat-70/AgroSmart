package com.example.agrosmart.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {

    var name = ""
    var mobNo = ""
    var email = ""
    var password = ""
    var city = ""
    var loginEmail = ""
    var loginPassword = ""

    val user = MutableLiveData<FirebaseUser?>()
    val errorMessage = MutableLiveData<String>()

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun signup() {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            errorMessage.postValue("Name, email, and password cannot be empty.")
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AuthViewModel", "Signup successful.")
                    user.postValue(firebaseAuth.currentUser)
                } else {
                    Log.e("AuthViewModel", "Signup failed", task.exception)
                    errorMessage.postValue(task.exception?.message ?: "Signup failed.")
                }
            }
    }

    fun login() {
        if (loginEmail.isEmpty() || loginPassword.isEmpty()) {
            errorMessage.postValue("Email and password cannot be empty.")
            return
        }

        firebaseAuth.signInWithEmailAndPassword(loginEmail, loginPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AuthViewModel", "Login successful.")
                    user.postValue(firebaseAuth.currentUser)
                } else {
                    Log.e("AuthViewModel", "Login failed", task.exception)
                    when (task.exception) {
                        is FirebaseAuthInvalidUserException -> {
                            errorMessage.postValue("Invalid Email Address.")
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            errorMessage.postValue("Invalid Password.")
                        }
                        else -> {
                            errorMessage.postValue(task.exception?.message ?: "Login failed.")
                        }
                    }
                }
            }
    }
}
