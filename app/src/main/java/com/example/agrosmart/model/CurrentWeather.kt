package com.example.agrosmart.model

data class CurrentWeather(
    val main: Main,
    val weather: List<Weather>,
    val dt_txt: String
) {
    data class Main(
        val temp: Double,
        val temp_min: String,
        val temp_max: String,
        val humidity: Int
    )

    data class Weather(
        val description: String,
        val icon: String
    )
}