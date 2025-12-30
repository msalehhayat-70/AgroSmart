package com.example.agrosmart.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PamraApi {

    @GET("resource/9ef84268-d588-465a-a308-a864a43d0070")
    fun getPamraData(
        @Query("api-key") apiKey: String = "579b464db66ec23bdd000001987c65666f9c49656f0f9ef4fa3650e7",
        @Query("format") format: String = "json",
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 7000
    ): Call<PamraMain>

    @GET("resource/9ef84268-d588-465a-a308-a864a43d0070")
    fun getPamraDataByDistrict(
        @Query("api-key") apiKey: String = "579b464db66ec23bdd000001987c65666f9c49656f0f9ef4fa3650e7",
        @Query("format") format: String = "json",
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 7000,
        @Query("filters[district]") district: String
    ): Call<PamraMain>
}

object PamraService {
    private const val BASE_URL = "https://api.data.gov.in/"

    val pamraInstance: PamraApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(PamraApi::class.java)
    }
}
