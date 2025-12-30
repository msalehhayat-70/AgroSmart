package com.example.agrosmart.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    fun getWeather(lat: String, lon: String): LiveData<WeatherRootList> {
        val data = MutableLiveData<WeatherRootList>()
        val response: Call<WeatherRootList> =
            WeatherService.weatherInstance.getWeather(lat, lon)

        response.enqueue(object : Callback<WeatherRootList> {
            override fun onFailure(call: Call<WeatherRootList>, t: Throwable) {
                Log.d("WeatherRepository", "Error Occurred: ${t.message}")
            }

            override fun onResponse(
                call: Call<WeatherRootList>,
                response: Response<WeatherRootList>
            ) {
                if (response.isSuccessful) {
                    data.postValue(response.body())
                } else {
                    Log.d("WeatherRepository", "Failed to get weather, code: ${response.code()}")
                }
            }
        })
        return data
    }
}
