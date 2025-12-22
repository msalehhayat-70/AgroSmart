package com.example.agrosmart.model

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    val main: Main,
    val weather: List<Weather>,
    val name: String,
    val wind: Wind,
    @SerializedName("dt_txt")
    val dtTxt: String?
) {
    data class Main(
        val temp: Double,
        @SerializedName("temp_min")
        val tempMin: Double,
        @SerializedName("temp_max")
        val tempMax: Double,
        val humidity: Int
    )

    data class Weather(
        val description: String,
        val icon: String
    )

    data class Wind(
        val speed: Double
    )
}
