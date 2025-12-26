package com.example.agrosmart.view

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agrosmart.R
import com.example.agrosmart.adapter.ChatAdapter
import com.example.agrosmart.viewmodel.ChatViewModel

class ChatbotActivity : AppCompatActivity() {

    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter

    // Replace with your actual Gemini API key
    private val apiKey = "AIzaSyAtTEYUkVHU40uH75wf0wArB0zN83m_STs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Initialize UI elements
        val recyclerView = findViewById<RecyclerView>(R.id.chatRecyclerView)
        val messageEditText = findViewById<EditText>(R.id.messageEditText)
        val sendButton = findViewById<ImageButton>(R.id.btnSend)

        // Setup RecyclerView
        chatAdapter = ChatAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter

        // Send button click listener
        sendButton.setOnClickListener {
            val message = messageEditText.text.toString().trim()
            if (message.isNotEmpty()) {
                // Add user message
                chatAdapter.addMessage("You: $message")
                messageEditText.text.clear()

                // Send message to AI
                chatViewModel.sendMessage(apiKey, message)
            }
        }

        // Observe AI reply
        chatViewModel.reply.observe(this) { reply ->
            chatAdapter.addMessage("AI: $reply")
            recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
        }
    }
}
