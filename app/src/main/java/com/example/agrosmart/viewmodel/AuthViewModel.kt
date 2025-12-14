package com.example.agrosmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.agrosmart.model.AuthRepository
import java.io.Serializable

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    var name: String? = null
    var mobNo: String? = null
    var email: String? = null
    var city: String? = null
    var password: String? = null
    var userType: String? = "normal"

    //login
    var loginmail: String? = null
    var loginpwd: String? = null

    fun signup(): LiveData<String> {
        val data = hashMapOf(
            "name" to name,
            "mobNo" to mobNo,
            "email" to email,
            "city" to city,
            "userType" to userType,
            "posts" to arrayListOf<String>(),
            "profileImage" to ""
        )
        return repository.signInWithEmail(email!!, password!!, data)
    }

    fun login(): LiveData<String> {
        return repository.logInWithEmail(loginmail!!, loginpwd!!)
    }

    fun googleSignIn(idToken: String, email: String): LiveData<String> {
        val data = hashMapOf(
            "userType" to userType,
            "posts" to arrayListOf<String>(),
            "profileImage" to ""
        )
        return repository.signInToGoogle(idToken, email, data)
    }
}
