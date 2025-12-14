package com.example.agrosmart.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ArticleRepository {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _article = MutableLiveData<Article?>()
    val article: LiveData<Article?> = _article

    fun getAllArticles() {
        // This is placeholder data since Firebase is removed.
        // In a real app, this would come from a local database or a different backend.
        val placeholderArticles = listOf(
            Article(title = "The Ultimate Guide to Tomato Farming", images = listOf("")),
            Article(title = "Advanced Techniques for Wheat Cultivation", images = listOf("")),
            Article(title = "Organic Pesticides for a Healthy Garden", images = listOf(""))
        )
        _articles.postValue(placeholderArticles)
    }

    fun getSpecificFruitArticle(name: String) {
        // This is placeholder data since Firebase is removed.
        val placeholderArticle = Article(
            title = name,
            images = listOf("https://via.placeholder.com/400x200.png?text=$name")
        )
        _article.postValue(placeholderArticle)
    }
}
