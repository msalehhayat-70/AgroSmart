package com.example.agrosmart.view.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.agrosmart.R
import com.example.agrosmart.databinding.ActivityArticleDetailBinding

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val imageResId = intent.getIntExtra("image", R.drawable.placeholder)

        binding.articleDetailTitle.text = title
        binding.articleDetailContent.text = content

        Glide.with(this)
            .load(imageResId)
            .placeholder(R.drawable.placeholder)
            .into(binding.articleDetailImage)
    }
}