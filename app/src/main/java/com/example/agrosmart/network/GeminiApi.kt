package com.example.agrosmart.network

import com.example.agrosmart.model.GeminiRequest
import com.example.agrosmart.model.GeminiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApi {

    @POST("v1beta/models/gemini-2.5-flash:generateMessage")
    suspend fun sendMessage(
        @Body request: GeminiRequest,
        @Query("key") apiKey: String
    ): GeminiResponse
}
