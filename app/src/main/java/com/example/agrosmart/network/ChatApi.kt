package com.example.agrosmart.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatApi {
    @POST("v1beta/models/gemini-1.5-flash-latest:generateContent")
    suspend fun sendMessage(
        @Body request: GoogleChatRequest,
        @Query("key") apiKey: String
    ): Response<GoogleChatResponse>
}
