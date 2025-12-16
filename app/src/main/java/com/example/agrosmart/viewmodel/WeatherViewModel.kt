package com.example.agrosmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agrosmart.model.WeatherRepository
import com.example.agrosmart.model.WeatherRootList

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    val weatherData: LiveData<WeatherRootList?> = repository.weatherData
    val coordinates = MutableLiveData<List<String>>()

    fun updateCoordinates(newCoordinates: List<String>) {
        coordinates.value = newCoordinates
        // Fetch new weather data whenever coordinates change
        repository.getWeather(newCoordinates[0], newCoordinates[1])
    }
}
