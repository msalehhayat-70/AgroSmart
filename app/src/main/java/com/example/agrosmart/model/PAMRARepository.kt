package com.example.agrosmart.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PAMRARepository {

    private val pamraApi = PamraService.pamraInstance

    val pamraData = MutableLiveData<PamraMain?>()
    val pamraDataByDistrict = MutableLiveData<PamraMain?>()

    fun getPamraData() {
        pamraApi.getPamraData().enqueue(object : Callback<PamraMain> {
            override fun onResponse(call: Call<PamraMain>, response: Response<PamraMain>) {
                if (response.isSuccessful) {
                    pamraData.postValue(response.body())
                } else {
                    Log.e("PAMRARepository", "Error fetching data: ${response.code()}")
                    pamraData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PamraMain>, t: Throwable) {
                Log.e("PAMRARepository", "Failure fetching data", t)
                pamraData.postValue(null)
            }
        })
    }

    fun getPamraDataByDistrict(district: String) {
        pamraApi.getPamraDataByDistrict(district = district).enqueue(object : Callback<PamraMain> {
            override fun onResponse(call: Call<PamraMain>, response: Response<PamraMain>) {
                if (response.isSuccessful) {
                    pamraDataByDistrict.postValue(response.body())
                } else {
                    Log.e("PAMRARepository", "Error fetching district data: ${response.code()}")
                    pamraDataByDistrict.postValue(null)
                }
            }

            override fun onFailure(call: Call<PamraMain>, t: Throwable) {
                Log.e("PAMRARepository", "Failure fetching district data", t)
                pamraDataByDistrict.postValue(null)
            }
        })
    }
}
