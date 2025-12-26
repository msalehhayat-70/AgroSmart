package com.example.agrosmart

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agrosmart.adapter.ChatAdapter
import com.example.agrosmart.viewmodel.ChatViewModel

class ChatbotActivity : AppCompatActivity() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var chatAdapter: ChatAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var btnSend: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView = findViewById(R.id.chatRecyclerView)
        messageEditText = findViewById(R.id.messageEditText)
        btnSend = findViewById(R.id.btnSend)

        chatAdapter = ChatAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatbotActivity)
            adapter = chatAdapter
        }

        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        viewModel.reply.observe(this) { response ->
            chatAdapter.addMessage("Bot: $response")
            recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
        }

        btnSend.setOnClickListener { sendMessage() }
        messageEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage()
                true
            } else false
        }
    }

    private fun sendMessage() {
        val message = messageEditText.text.toString().trim()
        if (message.isNotEmpty()) {
            chatAdapter.addMessage("You: $message")
            recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
            viewModel.sendMessage("AIzaSyAtTEYUkVHU40uH75wf0wArB0zN83m_STs", message)
            messageEditText.text.clear()
        }
    }
}
