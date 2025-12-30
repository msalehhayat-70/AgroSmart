package com.example.agrosmart.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ArticleRepository {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _article = MutableLiveData<Article?>()
    val article: LiveData<Article?> = _article

    private val placeholderArticles = listOf(
        Article(title = "The Ultimate Guide to Tomato Farming", images = listOf("tomato")),
        Article(title = "Advanced Techniques for Wheat Cultivation", images = listOf("wheat")),
        Article(title = "Organic Pesticides for a Healthy Garden", images = listOf("pesticides"))
    )

    fun getAllArticles() {
        _articles.postValue(placeholderArticles)
    }

    fun getSpecificFruitArticle(name: String) {
        _article.postValue(placeholderArticles.find { it.title == name })
    }
}
