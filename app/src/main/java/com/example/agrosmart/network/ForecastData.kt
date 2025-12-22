package com.example.agrosmart.network

data class ForecastData(
    val list: List<ForecastItem>
)

data class ForecastItem(
    val main: Main,
    val weather: List<Weather>,
    val dt_txt: String
)
