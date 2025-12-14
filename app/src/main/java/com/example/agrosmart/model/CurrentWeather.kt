package com.example.agrosmart.model

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    val main: Main,
    val weather: List<Weather>,
    @SerializedName("dt_txt")
    val dtTxt: String
) {
    data class Main(
        val temp: Double,
        @SerializedName("temp_min")
        val tempMin: String,
        @SerializedName("temp_max")
        val tempMax: String,
        val humidity: Int
    )

    data class Weather(
        val description: String,
        val icon: String
    )
}
