package com.example.agrosmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.agrosmart.model.WeatherRepository
import com.example.agrosmart.model.WeatherRootList

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _coordinates = MutableLiveData<List<String>>()
    val coordinates: LiveData<List<String>> = _coordinates

    val weatherData: LiveData<WeatherRootList> = _coordinates.switchMap { coords ->
        repository.getWeather(coords[0], coords[1])
    }

    fun setCoordinates(newCoordinates: List<String>) {
        if (_coordinates.value != newCoordinates) {
            _coordinates.value = newCoordinates
        }
    }
}
