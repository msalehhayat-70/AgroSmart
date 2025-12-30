package com.agrosmart.view.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.agrosmart.databinding.ActivityArticleDetailBinding

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")

        binding.articleDetailTitle.text = title
        binding.articleDetailContent.text = content
    }
}