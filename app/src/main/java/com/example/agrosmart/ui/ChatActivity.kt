package com.example.agrosmart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.agrosmart.viewmodel.ChatViewModel
import com.example.agrosmart.databinding.ActivityChatBinding


class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var viewModel: ChatViewModel

    private val GEMINI_API_KEY = BuildConfig.GEMINI_API_KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        // Send button click
        binding.btnSend.setOnClickListener {
            val userMessage = binding.messageEditText.text.toString()
            if (userMessage.isNotEmpty()) {
                viewModel.sendMessage(GEMINI_API_KEY, userMessage)
                binding.messageEditText.text.clear()
            }
        }

        // Observe Gemini response
        viewModel.reply.observe(this) { response ->
            // For now just log or toast
            // Later we will show in RecyclerView
            println("Gemini Response: $response")
        }
    }
}
