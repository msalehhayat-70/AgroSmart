package com.example.agrosmart.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    val list: List<ForecastItem>
)

data class ForecastItem(
    val main: CurrentWeather.Main,
    val weather: List<CurrentWeather.Weather>,
    @SerializedName("dt_txt")
    val dtTxt: String
)
