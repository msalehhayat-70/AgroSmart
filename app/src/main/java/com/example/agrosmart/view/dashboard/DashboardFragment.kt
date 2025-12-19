package com.example.agrosmart.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
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

class DashboardFragment : Fragment(), CellClickListener {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var weatherFragment: WeatherFragment
    private lateinit var schemeListFragment: SchemeListFragment
    private lateinit var articleListFragment: ArticleListFragment

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var ecommViewModel: EcommViewModel

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

        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        ecommViewModel = ViewModelProvider(requireActivity()).get(EcommViewModel::class.java)

        setupWeather()
        setupEcommerce()
        setupNavigation()
    }

    private fun setupWeather() {
        weatherViewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
            if (_binding != null && weatherData != null && weatherData.list.isNotEmpty()) {
                val firstWeather = weatherData.list[0]
                binding.weathTempTextWeathFrag.text = getString(R.string.weather_temp, (firstWeather.main.temp - 273).toInt())
                binding.humidityTextWeathFrag.text = getString(R.string.weather_humidity, firstWeather.main.humidity)
                binding.windTextWeathFrag.text = getString(R.string.weather_wind, firstWeather.wind.speed)
                val city = weatherViewModel.coordinates.value?.getOrNull(2) ?: getString(R.string.unknown_city)
                binding.weatherCityTitle.text = city
                val iconCode = firstWeather.weather[0].icon
                val iconUrl = "https://openweathermap.org/img/w/$iconCode.png"
                if (context != null) {
                    Glide.with(requireContext()).load(iconUrl).into(binding.weathIconImageWeathFrag)
                }
            }
        }
        // Set initial coordinates to trigger weather fetch
        weatherViewModel.setCoordinates(listOf("23.0225", "72.5714", "Ahmedabad"))
    }

    private fun setupEcommerce() {
        ecommViewModel.products.observe(viewLifecycleOwner) { products ->
            val dashboardEcomItems = products.map { product ->
                DashboardEcomItem(product.id, product.title, product.price.toString(), product.imageUrl)
            }
            val itemsToShow = dashboardEcomItems.indices.toList().shuffled().take(4)
            val adapterEcomm = DashboardEcomItemAdapter(requireContext(), dashboardEcomItems, itemsToShow, this)
            binding.dashboardEcommRecycler.adapter = adapterEcomm
            binding.dashboardEcommRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        }
        ecommViewModel.loadAllEcommItems()
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

    override fun onCellClickListener(name: String) {
        val ecommerceItemFragment = EcommerceItemFragment()
        val bundle = Bundle()
        bundle.putString("productId", name)
        ecommerceItemFragment.arguments = bundle
        navigateTo(ecommerceItemFragment, "ecommerceItemFrag")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
