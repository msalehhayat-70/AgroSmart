package com.example.agrosmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agrosmart.model.PAMRARepository
import com.example.agrosmart.model.PamraCustomRecords
import com.example.agrosmart.model.PamraMain

class PamraViewModel : ViewModel() {

    private val repository = PAMRARepository()

    private val _pamraData = MutableLiveData<List<PamraCustomRecords>?>()
    val pamraData: LiveData<List<PamraCustomRecords>?> = _pamraData

    fun fetchPamraData(district: String) {
        repository.getPamraDataByDistrict(district)
    }

    init {
        repository.pamraDataByDistrict.observeForever {
            if (it != null) {
                _pamraData.postValue(processPamraData(it))
            } else {
                _pamraData.postValue(null)
            }
        }
    }

    private fun processPamraData(pamraMain: PamraMain): List<PamraCustomRecords> {
        val customRecords = mutableListOf<PamraCustomRecords>()
        val recordsByMarket = pamraMain.records.groupBy { it.market }

        for ((market, records) in recordsByMarket) {
            if (records.isNotEmpty()) {
                val firstRecord = records[0]
                val commodities = records.mapNotNull { it.commodity }
                val minPrices = records.mapNotNull { it.minPrice }
                val maxPrices = records.mapNotNull { it.maxPrice }

                customRecords.add(
                    PamraCustomRecords(
                        market = market ?: "",
                        district = firstRecord.district ?: "",
                        state = firstRecord.state ?: "",
                        commodity = commodities,
                        min_price = minPrices,
                        max_price = maxPrices
                    )
                )
            }
        }
        return customRecords
    }
}
