package com.example.agrosmart.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.agrosmart.model.WeatherRootList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    val data = MutableLiveData<WeatherRootList>()

    fun getWeather(lat: String, lon: String): LiveData<String> {
        val response: Call<WeatherRootList> =
            WeatherService.weatherInstance.getWeather(lat, lon)

        val weathRes = MutableLiveData<String>()

        response.enqueue(object : Callback<WeatherRootList> {
            override fun onFailure(call: Call<WeatherRootList>, t: Throwable) {
                Log.d("WeatherRepository", "Error Occured")
            }

            override fun onResponse(
                call: Call<WeatherRootList>,
                response: Response<WeatherRootList>
            ) {
                if (response.isSuccessful) {
                    data.value = response.body()
                    weathRes.value = "DONE"
                } else {
                    weathRes.value = "FAILED"
                }
            }
        })
        return weathRes
    }
}