package com.example.agrosmart.model

import com.google.gson.annotations.SerializedName

data class WeatherMain(
    val temp: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val humidity: Int
)
