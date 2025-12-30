package com.example.agrosmart.view.article

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.agrosmart.R
import com.example.agrosmart.databinding.ActivityDiseasesBinding

class DiseasesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiseasesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseasesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.disease1.setOnClickListener {
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("title", "Leaf Spot Disease")
            intent.putExtra("content", "Leaf Spot appears as yellow, brown, or black spots on leaves. Over time, the spots expand, causing leaves to yellow and fall off. It is usually caused by fungal or bacterial infection and spreads in wet, humid conditions.\n" +
                    "\n" +
                    "Solution:\n" +
                    "\n" +
                    "Remove and destroy infected leaves\n" +
                    "\n" +
                    "Avoid overhead watering\n" +
                    "\n" +
                    "Improve air circulation\n" +
                    "\n" +
                    "Apply fungicide (e.g., Mancozeb or Copper-based fungicide)\n" +
                    "\n" +
                    "Use disease-resistant plant varieties")
            intent.putExtra("image", R.drawable.p1)
            startActivity(intent)
        }

        binding.disease2.setOnClickListener {
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("title", "Powdery Mildew")
            intent.putExtra("content", "Powdery Mildew forms white or gray powder-like patches on leaves and stems. It reduces photosynthesis and weakens the plant, especially in warm and humid weather.\n" +
                    "\n" +
                    "Solution:\n" +
                    "\n" +
                    "Prune infected plant parts\n" +
                    "\n" +
                    "Increase sunlight and airflow\n" +
                    "\n" +
                    "Spray sulfur-based fungicide or neem oil\n" +
                    "\n" +
                    "Avoid excess nitrogen fertilizer")
            intent.putExtra("image", R.drawable.p2)
            startActivity(intent)
        }

        binding.disease3.setOnClickListener {
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("title", "Stem Rust")
            intent.putExtra("content", "Stem Rust causes reddish-brown pustules on stems and leaves, commonly affecting cereal crops. It weakens nutrient transport and lowers crop yield.\n" +
                    "\n" +
                    "Solution:\n" +
                    "\n" +
                    "Use rust-resistant crop varieties\n" +
                    "\n" +
                    "Apply systemic fungicides (e.g., Propiconazole)\n" +
                    "\n" +
                    "Remove infected crop residues\n" +
                    "\n" +
                    "Practice crop rotation")
            intent.putExtra("image", R.drawable.p3)
            startActivity(intent)
        }

        binding.disease4.setOnClickListener {
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("title", "Bacterial Leaf Blight")
            intent.putExtra("content", "Bacterial Leaf Blight creates water-soaked spots that turn brown and spread along leaf edges. It spreads quickly through rain, wind, and contaminated tools.\n" +
                    "\n" +
                    "Solution:\n" +
                    "\n" +
                    "Remove infected plants immediately\n" +
                    "\n" +
                    "Use copper-based bactericides\n" +
                    "\n" +
                    "Disinfect farming tools\n" +
                    "\n" +
                    "Avoid working in wet fields\n" +
                    "\n" +
                    "Use certified disease-free seeds")
            intent.putExtra("image", R.drawable.p4)
            startActivity(intent)
        }
    }
}