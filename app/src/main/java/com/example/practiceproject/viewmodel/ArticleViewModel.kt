package com.example.practiceproject.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.flatMap
import androidx.paging.map
import com.example.practiceproject.api.RetrofitBuilder
import com.example.practiceproject.database.entities.Images
import com.example.practiceproject.database.relations.ArticleWithImages
import com.example.practiceproject.model.Article
import com.example.practiceproject.repository.Repository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    private lateinit var repository :Repository
    var responseArticles : LiveData<PagingData<Article>> = MutableLiveData()
    var article : Article? = null

    fun getData(context: Context) : LiveData<PagingData<Article>>{
        repository = Repository(context,RetrofitBuilder.api)
        return repository.getArticles().cachedIn(viewModelScope)

    }

//    fun getData(context: Context) {
//        repository = Repository(context,RetrofitBuilder.api)
//        val response = repository.getArticles().cachedIn(viewModelScope)
//        responseArticles = response
//        val articles = responseArticles.value?.map {
//           article= it
//        }
//
//    }
//    val list = repository.getArticles().cachedIn(viewModelScope)

//    fun getTeslaArticle(){
//        viewModelScope.launch {
//            Repository.getTeslaArticles()
//                .catch { e ->
//                    Log.d("TAG", "getTeslaArticle: ${e.message}")
//                }
//                .collect{ response ->
//                    responseArticles.value = response.articles
//                }
//        }
//    }

    fun insertArticle(article: com.example.practiceproject.database.entities.Article,context: Context){
        repository = Repository(context,RetrofitBuilder.api)
        return repository.insertArticleInDatabase(article)
    }

    fun insertImages(images: Images,context: Context){
        repository= Repository(context,RetrofitBuilder.api)
        return repository.insertImagesInDatabase(images)
    }

    fun getArticleImages(title:String,context: Context)   {
        repository= Repository(context,RetrofitBuilder.api)
        viewModelScope.launch {
            repository.getArticlesWithImages(title)
            Log.d("TAG", "getArticleImages: ${repository.getArticlesWithImages(title)}")
        }

    }
}