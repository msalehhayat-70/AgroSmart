package com.example.agrosmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.agrosmart.model.Article
import com.example.agrosmart.model.ArticleRepository

class ArticleViewModel : ViewModel() {

    private val repository = ArticleRepository()

    val articles: LiveData<List<Article>> = repository.articles
    val article: LiveData<Article?> = repository.article

    fun getAllArticles() {
        repository.getAllArticles()
    }

    fun getSpecificFruitArticle(name: String) {
        repository.getSpecificFruitArticle(name)
    }
}
