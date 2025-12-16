package com.example.agrosmart.view.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrosmart.R
import com.example.agrosmart.adapter.CurrentWeatherAdapter
import com.example.agrosmart.adapter.WeatherAdapter
import com.example.agrosmart.databinding.FragmentWeatherBinding
import com.example.agrosmart.model.CurrentWeather
import com.example.agrosmart.viewmodel.WeatherViewModel

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Weather Forecast"
        setupMenu()
        setupObservers()

        // Fetches weather for a default location. You can update this based on user's location.
        viewModel.updateCoordinates(listOf("28.6139", "77.2090", "Delhi"))
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = true
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupObservers() {
        viewModel.coordinates.observe(viewLifecycleOwner) { coordinates ->
            if (coordinates.size > 2) {
                binding.weatherCity.text = coordinates[2]
            } else {
                binding.weatherCity.text = getString(R.string.unknown_city)
            }
        }

        viewModel.weatherData.observe(viewLifecycleOwner) { weatherRootList ->
            weatherRootList?.let { root ->
                Log.d("WeatherFragment", "Received weather data: ${root.list.size} items")

                // Filter for daily forecasts (one forecast per day around noon)
                val dailyForecasts = root.list.filter {
                    it.dt_txt.contains("12:00:00")
                }

                // Take the first 8 forecasts for an hourly view (next 24 hours)
                val hourlyForecasts = root.list.take(8).map { weatherList ->
                    CurrentWeather(
                        main = CurrentWeather.Main(
                            temp = weatherList.main.temp,
                            tempMin = weatherList.main.tempMin.toString(),
                            tempMax = weatherList.main.tempMax.toString(),
                            humidity = weatherList.main.humidity
                        ),
                        weather = weatherList.weather.map { weatherData ->
                            CurrentWeather.Weather(
                                description = weatherData.description,
                                icon = weatherData.icon
                            )
                        },
                        dtTxt = weatherList.dt_txt
                    )
                }

                binding.rcylrWeather.adapter = WeatherAdapter(dailyForecasts)
                binding.rcylrWeather.layoutManager = LinearLayoutManager(requireContext())

                binding.currentWeatherRcycl.adapter = CurrentWeatherAdapter(requireContext(), hourlyForecasts)
                binding.currentWeatherRcycl.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
