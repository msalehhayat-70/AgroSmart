package com.example.agrosmart.view.introscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.agrosmart.R
import com.example.agrosmart.adapter.IntroAdapter
import com.example.agrosmart.databinding.ActivityIntroBinding
import com.example.agrosmart.model.IntroData
import com.example.agrosmart.view.auth.LoginActivity

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    private val introSliderAdapter = IntroAdapter(
        listOf(
            IntroData(
                "Welcome to AgroSmart",
                "Best Guide and Helper for any Farmer. Provides various features at one place!",
                R.drawable.intro_first
            ),
            IntroData(
                "Read Articles",
                "Read Online articles related to Farming Concepts, Technologies and other useful knowledge.",
                R.drawable.intro_read
            ),
            IntroData(
                "Share Knowledge",
                "Social Media let\'s you share knowledge with other farmers!\nCreate your own posts using Image/Video/Texts.",
                R.drawable.intro_share
            ),
            IntroData(
                "E-Commerce",
                "Buy / Sell Agriculture related products & Manage your Cart Online",
                R.drawable.intro_ecomm
            ),
            IntroData(
                "Weather Forecast",
                "Get Notified for Daily Weather Conditions. 24x7 Data",
                R.drawable.intro_weather
            ),
            IntroData(
                "PAMRA Statistics",
                "Get updates PAMRA Pricing and Commodity details everyday.",
                R.drawable.intro_statistics
            ),
            IntroData(
                "Let\'s Grow Together",
                "- AgroSmart",
                R.drawable.intro_help
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sliderViewPager.adapter = introSliderAdapter
        setupIndicators()
        setCurrentIndicator(0)

        binding.sliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        binding.nextBtn.setOnClickListener {
            if (binding.sliderViewPager.currentItem + 1 < introSliderAdapter.itemCount) {
                binding.sliderViewPager.currentItem += 1
            } else {
                navigateToLogin()
            }
        }

        binding.skipIntro.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
        }
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("firstTime", false).apply()
        finish()
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext).apply {
                setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.indicator_inactive))
                this.layoutParams = layoutParams
            }
            binding.sliderballsContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.sliderballsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.sliderballsContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.indicator_active))
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.indicator_inactive))
            }
        }

        if (index == introSliderAdapter.itemCount - 1) {
            binding.nextBtn.text = "Get Started"
        } else {
            binding.nextBtn.text = "Next"
        }
    }
}
