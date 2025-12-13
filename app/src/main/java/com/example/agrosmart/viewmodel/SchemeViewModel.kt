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

    fun getAllSchemes() {
        // Replace with your actual data source
        val placeholderSchemes = listOf(
            Scheme("Scheme 1", "Active", "01/01/2023", "", "Description 1", "1000", "Ministry of Agriculture", listOf("Eligibility 1"), listOf("Objective 1"), listOf("Document 1")),
            Scheme("Scheme 2", "Inactive", "02/02/2023", "", "Description 2", "2000", "Ministry of Finance", listOf("Eligibility 2"), listOf("Objective 2"), listOf("Document 2"))
        )
        _schemes.value = placeholderSchemes
    }

    fun getScheme(name: String) {
        // Replace with your actual data source
        val placeholderScheme = Scheme(
            name, "Active", "01/01/2023", "",
            "This is a placeholder description for the scheme.",
            "5000",
            "Ministry of Agriculture",
            listOf("Must be a farmer", "Must own land"),
            listOf("To provide financial assistance", "To promote modern farming techniques"),
            listOf("Aadhaar Card", "Land documents")
        )
        _scheme.value = placeholderScheme
    }
}
