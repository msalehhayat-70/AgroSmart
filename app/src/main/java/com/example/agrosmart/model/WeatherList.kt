package com.example.agrosmart.model

data class WeatherList(
    val dt_txt: String,
    val weather: List<WeatherData>,
    val main: WeatherMain,
    val wind: Wind
)

data class Wind(
    val speed: Double
)
