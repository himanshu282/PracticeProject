package com.example.practiceproject.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practiceproject.database.entities.Images
import com.example.practiceproject.database.relations.ArticleWithImages
import com.example.practiceproject.model.Article
import com.example.practiceproject.repository.Repository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    private lateinit var repository :Repository
    val responseArticles : MutableLiveData<List<Article>> = MutableLiveData()

    fun getTeslaArticle(){
        viewModelScope.launch {
            Repository.getTeslaArticles()
                .catch { e ->
                    Log.d("TAG", "getTeslaArticle: ${e.message}")
                }
                .collect{ response ->
                    responseArticles.value = response.articles
                }
        }
    }

    fun insertArticle(article: com.example.practiceproject.database.entities.Article,context: Context){
        repository = Repository(context)
        return repository.insertArticleInDatabase(article)
    }

    fun insertImages(images: Images,context: Context){
        repository= Repository(context)
        return repository.insertImagesInDatabase(images)
    }

    fun getArticleImages(title:String,context: Context)   {
        repository= Repository(context)
        viewModelScope.launch {
            repository.getArticlesWithImages(title)
            Log.d("TAG", "getArticleImages: ${repository.getArticlesWithImages(title)}")
        }

    }
}