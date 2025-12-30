package com.example.agrosmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agrosmart.model.Scheme

class SchemeViewModel : ViewModel() {

    private val _schemes = MutableLiveData<List<Scheme>>()
    val schemes: LiveData<List<Scheme>> = _schemes

    private val _scheme = MutableLiveData<Scheme>()
    val scheme: LiveData<Scheme> = _scheme

    private val placeholderSchemes = listOf(
        Scheme("Kissan Dost Scheme", "Active", "03/03/2023", "plant3", "A subsidy program for farmers.", "3000", "MNFSR", listOf("Pakistani citizen", "Registered farmer","Non-defaulter status"), listOf("Increase crop yield", "Provide financial support"), listOf("CNIC", "Land record")),
        Scheme("Zarkhez Pakistan Scheme", "Active", "04/04/2023", "app_icon4", "A program to improve soil fertility.", "4000", "Provincial Agriculture Departments", listOf("Farmers with land holdings up to 12.5 acres","Pakistani residents with a valid CNIC and registered mobile number"), listOf("Promote balanced use of fertilizers", "Enhance soil health"), listOf("CNIC", "Proof of land ownership"))
    )

    fun getAllSchemes() {
        _schemes.value = placeholderSchemes
    }

    fun getScheme(name: String) {
        _scheme.value = placeholderSchemes.find { it.title == name }
    }
}
