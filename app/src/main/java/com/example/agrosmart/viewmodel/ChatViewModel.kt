package com.example.agrosmart.viewmodel

import androidx.lifecycle.*
import com.example.agrosmart.repository.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val repository = ChatRepository()

    private val _reply = MutableLiveData<String>()
    val reply: LiveData<String> = _reply

    fun sendMessage(apiKey: String, message: String) {
        viewModelScope.launch {
            try {
                _reply.value = repository.askGemini(apiKey, message)
            } catch (e: Exception) {
                _reply.value = e.message ?: "Error"
            }
        }
    }
}

