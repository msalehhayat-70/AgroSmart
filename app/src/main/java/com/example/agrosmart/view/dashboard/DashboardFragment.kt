package com.example.agrosmart.view.dashboard

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
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.adapter.DashboardEcomItemAdapter
import com.example.agrosmart.databinding.FragmentDashboardBinding
import com.example.agrosmart.model.DashboardEcomItem
import com.example.agrosmart.utilities.CellClickListener
import com.example.agrosmart.view.articles.ArticleListFragment
import com.example.agrosmart.view.ecommerce.EcommerceItemFragment
import com.example.agrosmart.view.scheme.SchemeListFragment
import com.example.agrosmart.view.weather.WeatherFragment
import com.example.agrosmart.viewmodel.EcommViewModel
import com.example.agrosmart.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices

class DashboardFragment : Fragment(), CellClickListener {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var weatherFragment: WeatherFragment
    private lateinit var schemeListFragment: SchemeListFragment
    private lateinit var articleListFragment: ArticleListFragment

    private val weatherViewModel: WeatherViewModel by viewModels()
    private val eCommerceViewModel: EcommViewModel by viewModels()

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
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.action_bar_title)

        setupWeather()
        setupECommerce()
        setupNavigation()
        checkLocationPermission()
    }

    private fun setupWeather() {
        weatherViewModel.currentWeather.observe(viewLifecycleOwner) { weather ->
            if (_binding != null) {
                binding.weathTempTextWeathFrag.text = getString(R.string.weather_temp, weather.main.temp.toInt())
                binding.humidityTextWeathFrag.text = getString(R.string.weather_humidity, weather.main.humidity)
                binding.windTextWeathFrag.text = getString(R.string.weather_wind, weather.wind.speed)
                binding.weatherCityTitle.text = weather.name
                val iconCode = weather.weather[0].icon
                val iconUrl = "https://openweathermap.org/img/w/$iconCode.png"
                if (context != null) {
                    Glide.with(requireContext()).load(iconUrl).into(binding.weathIconImageWeathFrag)
                }
            }
        }
    }

    private fun setupECommerce() {
        eCommerceViewModel.products.observe(viewLifecycleOwner) { products ->
            val dashboardEcomItems = products.map { product ->
                DashboardEcomItem(product.id, product.title, product.price.toString(), product.imageUrl)
            }
            val itemsToShow = dashboardEcomItems.indices.toList().shuffled().take(4)
            val adapterECommerce = DashboardEcomItemAdapter(requireContext(), dashboardEcomItems, itemsToShow, this)
            binding.dashboardEcommRecycler.adapter = adapterECommerce
            binding.dashboardEcommRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        }
        eCommerceViewModel.loadAllEcommItems()
    }

    private fun setupNavigation() {
        weatherFragment = WeatherFragment()
        schemeListFragment = SchemeListFragment()
        articleListFragment = ArticleListFragment()

        binding.weatherCard.setOnClickListener {
            navigateTo(weatherFragment, "weatherFrag")
        }

        binding.cat4.setOnClickListener {
            navigateTo(schemeListFragment, "schemeListFrag")
        }

        binding.cat5.setOnClickListener {
            navigateTo(articleListFragment, "articlesListFrag")
        }
    }

    private fun navigateTo(fragment: Fragment, tag: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment, tag)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(tag)
            .commit()
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
                weatherViewModel.getCurrentWeather(location.latitude, location.longitude)
            }
        }
    }

    override fun onCellClickListener(data: String) {
        val eCommerceItemFragment = EcommerceItemFragment()
        val bundle = Bundle()
        bundle.putString("productId", data)
        eCommerceItemFragment.arguments = bundle
        navigateTo(eCommerceItemFragment, "ecommerceItemFrag")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
