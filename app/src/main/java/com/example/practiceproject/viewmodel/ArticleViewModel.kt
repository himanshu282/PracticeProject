package com.example.practiceproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practiceproject.model.Article
import com.example.practiceproject.repository.Repository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {
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
}