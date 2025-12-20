package com.example.agrosmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agrosmart.model.AuthRepository

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    // Signup fields
    var name: String? = null
    var mobNo: String? = null
    var email: String? = null
    var city: String? = null
    var password: String? = null
    var userType: String? = "normal"

    // Login fields
    var loginEmail: String? = null
    var loginPassword: String? = null

    private val _authResult = MutableLiveData<String>()
    val authResult: LiveData<String> = _authResult

    // Signup function
    fun signup() {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            _authResult.value = "Email or password cannot be empty"
            return
        }

        val data = hashMapOf<String, Any>(
            "name" to (name ?: ""),
            "mobNo" to (mobNo ?: ""),
            "email" to email!!,
            "city" to (city ?: ""),
            "password" to password!!,
            "userType" to (userType ?: "normal"),
            "posts" to arrayListOf<String>(),
            "profileImage" to ""
        )

        repository.signUpUser(email!!, password!!, data).observeForever { result ->
            _authResult.value = result
        }
    }

    // Login function
    fun login() {
        if (loginEmail.isNullOrEmpty() || loginPassword.isNullOrEmpty()) {
            _authResult.value = "Email or password cannot be empty"
            return
        }

        repository.loginUser(loginEmail!!, loginPassword!!).observeForever { result ->
            _authResult.value = result
        }
    }
}
