package com.example.agrosmart.network

import com.example.agrosmart.model.CurrentWeather
import com.example.agrosmart.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
<<<<<<< HEAD
    @GET("data/2.5/weather")
=======
    @GET("weather")
>>>>>>> main
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): CurrentWeather

<<<<<<< HEAD
    @GET("data/2.5/forecast")
=======
    @GET("forecast")
>>>>>>> main
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): ForecastResponse
}
