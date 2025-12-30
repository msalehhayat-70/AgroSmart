package com.example.agrosmart.repository

import com.example.agrosmart.BuildConfig
import com.example.agrosmart.model.CurrentWeather
import com.example.agrosmart.model.ForecastResponse
<<<<<<< HEAD
import com.example.agrosmart.network.RetrofitClientWeather

class WeatherRepository {
    private val weatherApiService = RetrofitClientWeather.instance
=======
import com.example.agrosmart.network.RetrofitClient

class WeatherRepository {
    private val weatherApiService = RetrofitClient.instance
>>>>>>> main

    suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeather {
        return weatherApiService.getCurrentWeather(lat, lon, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
    }

    suspend fun getForecast(lat: Double, lon: Double): ForecastResponse {
        return weatherApiService.getForecast(lat, lon, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
    }
}
