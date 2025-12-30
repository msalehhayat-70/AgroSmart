package com.example.agrosmart.view.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.agrosmart.adapter.WeatherAdapter
import com.example.agrosmart.databinding.FragmentWeatherDetailsBinding
import com.example.agrosmart.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeatherViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getCurrentLocation()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Weather Forecast"

        setupObservers()
        checkLocationPermission()
    }

    private fun setupObservers() {
        viewModel.currentWeather.observe(viewLifecycleOwner) { weather ->
            binding.weatherCity.text = weather.name
            binding.temperature.text = "${weather.main.temp.toInt()}°C"
            binding.weatherDescription.text = weather.weather.firstOrNull()?.description ?: ""
            binding.minTemp.text = "${weather.main.tempMin.toInt()}°C"
            binding.maxTemp.text = "${weather.main.tempMax.toInt()}°C"
            binding.humidity.text = "${weather.main.humidity}%"

            val iconUrl = "https://openweathermap.org/img/w/${weather.weather.firstOrNull()?.icon}.png"
            Glide.with(this).load(iconUrl).into(binding.weatherIcon)
        }

        viewModel.forecast.observe(viewLifecycleOwner) { forecast ->
            val adapter = WeatherAdapter(forecast)
            binding.forecastRecyclerView.adapter = adapter
            binding.forecastRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getCurrentLocation()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                viewModel.getCurrentWeather(location.latitude, location.longitude)
                viewModel.getForecast(location.latitude, location.longitude)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
