package com.example.agrosmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrosmart.model.CurrentWeather
import com.example.agrosmart.model.ForecastItem
import com.example.agrosmart.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather

    private val _forecast = MutableLiveData<List<ForecastItem>>()
    val forecast: LiveData<List<ForecastItem>> = _forecast

    fun getCurrentWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val weather = repository.getCurrentWeather(lat, lon)
                _currentWeather.postValue(weather)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun getForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val forecastResponse = repository.getForecast(lat, lon)
                val dailyForecasts = forecastResponse.list.filter {
                    it.dtTxt.contains("12:00:00")
                }
                _forecast.postValue(dailyForecasts)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
