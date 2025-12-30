package com.agrosmart.view.article

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.agrosmart.databinding.ActivityPlantsBinding
import com.example.agrosmart.R
import kotlin.jvm.java

class PlantsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlantsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.plant1.setOnClickListener {
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("title", "Snake Plant")
            intent.putExtra("content", "A hardy indoor plant with upright, sword-shaped green leaves. It is very low-maintenance and improves air quality by filtering toxins. It can survive in low light and needs minimal watering..")
            startActivity(intent)
        }

        binding.plant2.setOnClickListener {
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("title", "Peace Lily")
            intent.putExtra("content", "A graceful plant with glossy green leaves and white flowers. Peace Lily prefers indirect light and slightly moist soil. It is known for improving indoor air quality and has an elegant look.")
            startActivity(intent)
        }

        binding.plant3.setOnClickListener {
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("title", "Rubber Plant")
            intent.putExtra("content", "This plant has thick, shiny, dark-green leaves and grows well indoors. It likes bright, indirect light and moderate watering. Rubber plants are popular for home and office décor.")
            startActivity(intent)
        }

        binding.plant4.setOnClickListener {
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("title", "Dwarf Umbrella Plant")
            intent.putExtra("content", "A compact plant with umbrella-shaped leaf clusters. It grows well in bright, indirect light and adds a lush, bushy look to indoor spaces. Easy to maintain and fast-growing.")
            startActivity(intent)
        }
    }
}