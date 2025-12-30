package com.example.agrosmart.viewmodel

import androidx.lifecycle.LiveData

interface AuthListener {

    fun onStarted()
    fun onSuccess(authRepo: LiveData<String>)
    fun onFailure(message: String)

}