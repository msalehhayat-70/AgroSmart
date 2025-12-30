package com.agrosmart.view.article

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.agrosmart.databinding.ActivityMethodsBinding
import com.example.agrosmart.R
import kotlin.jvm.java

class MethodsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMethodsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMethodsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.method1.setOnClickListener {
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("title", "Forage Harvesting")
            intent.putExtra("content", "This method is used to cut grass or forage crops like alfalfa for animal feed. A mower or forage harvester trims the crop evenly and prepares it for drying")
            startActivity(intent)

        }

        binding.method2.setOnClickListener {
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("title", "Plowing")
            intent.putExtra("content", "Plowing turns and loosens the soil before planting. It helps remove weeds, improve soil aeration, and prepare the land for sowing seeds using tractors and plows.")
            startActivity(intent)
        }

        binding.method3.setOnClickListener {
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("title", "Row Crop Cultivation")
            intent.putExtra("content", "This method is used for crops grown in rows such as vegetables or maize. Machines move between rows to manage soil, control weeds, and sometimes assist with irrigation")
            startActivity(intent)
        }

        binding.method4.setOnClickListener {
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("title", "Crop Harvesting")
            intent.putExtra("content", "A combine harvester is used to harvest mature crops like wheat or cotton. It cuts, threshes, and separates grain from the plant in one efficient process")
            startActivity(intent)
        }
    }
}