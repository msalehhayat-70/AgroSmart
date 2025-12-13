package com.example.agrosmart.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.adapter.DashboardEcomItemAdapter
import com.example.agrosmart.databinding.FragmentDashboardBinding
import com.example.agrosmart.model.Product
import com.example.agrosmart.model.WeatherRootList
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

        (activity as AppCompatActivity).supportActionBar?.title = "Agri India"

        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        ecommViewModel = ViewModelProvider(requireActivity()).get(EcommViewModel::class.java)

        setupWeather()
        setupEcommerce()
        setupNavigation()
    }

    private fun setupWeather() {
        weatherViewModel.getCoordinates().observe(viewLifecycleOwner) { coordinates ->
            weatherViewModel.updateNewData()
            val city = coordinates[2]
            weatherViewModel.newDataTrial.observe(viewLifecycleOwner) { weatherData ->
                binding.weathTempTextWeathFrag.text = "${(weatherData.list[0].main.temp - 273).toInt()}\u2103"
                binding.humidityTextWeathFrag.text = "Humidity: ${weatherData.list[0].main.humidity} %"
                binding.windTextWeathFrag.text = "Wind: ${weatherData.list[0].wind.speed} km/hr"
                binding.weatherCityTitle.text = city
                val iconCode = weatherData.list[0].weather[0].icon
                val iconUrl = "https://openweathermap.org/img/w/$iconCode.png"
                Glide.with(requireContext()).load(iconUrl).into(binding.weathIconImageWeathFrag)
            }
        }
    }

    private fun setupEcommerce() {
        ecommViewModel.ecommLiveData.observe(viewLifecycleOwner) { products ->
            val itemsToShow = products.shuffled().take(4)
            val adapterEcomm = DashboardEcomItemAdapter(requireContext(), itemsToShow, this)
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
            .setTransition(Fragment.TRANSIT_FRAGMENT_OPEN)
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
