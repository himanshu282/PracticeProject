package com.example.practiceproject.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.practiceproject.api.RetrofitBuilder
import com.example.practiceproject.model.Article
import com.example.practiceproject.repository.Repository

class ArticleViewModel : ViewModel() {

    private lateinit var repository :Repository
    var article : Article? = null

    fun getData(context: Context) : LiveData<PagingData<Article>>{
        repository = Repository(context,RetrofitBuilder.api)
        return repository.getArticles().cachedIn(viewModelScope)

    }

}