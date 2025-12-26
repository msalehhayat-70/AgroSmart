package com.example.agrosmart.repository

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class ChatRepository {

    private val client = OkHttpClient()

    suspend fun askGemini(apiKey: String, message: String): String {
        val url =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateMessage?key=$apiKey"

        val json = JSONObject().apply {
            put("model", "gemini-2.5-flash")
            put("temperature", 0.7)
            put("prompt", JSONObject().apply {
                put("messages", listOf(JSONObject().apply {
                    put("content", message)
                    put("author", "user")
                }))
            })
        }

        val body = json.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                return "Error: ${response.code} ${response.message}"
            }

            val responseBody = response.body?.string() ?: return "Error: empty response"

            // Parse AI reply
            val jsonResponse = JSONObject(responseBody)
            val reply =
                jsonResponse.getJSONArray("candidates").getJSONObject(0).getString("content")
            return reply
        }
    }
}
