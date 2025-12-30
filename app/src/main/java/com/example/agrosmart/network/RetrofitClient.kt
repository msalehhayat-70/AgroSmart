package com.example.agrosmart.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
<<<<<<< HEAD

    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    val api: GeminiApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeminiApi::class.java)
=======
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val instance: WeatherApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(WeatherApiService::class.java)
>>>>>>> main
    }
}
