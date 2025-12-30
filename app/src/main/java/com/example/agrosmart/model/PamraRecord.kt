package com.example.agrosmart.model

import com.google.gson.annotations.SerializedName

data class PamraRecord(
    val state: String?,
    val district: String?,
    val market: String?,
    val commodity: String?,
    val variety: String?,
    @SerializedName("arrival_date")
    val arrivalDate: String?,
    @SerializedName("min_price")
    val minPrice: String?,
    @SerializedName("max_price")
    val maxPrice: String?,
    @SerializedName("modal_price")
    val modalPrice: String?
)
