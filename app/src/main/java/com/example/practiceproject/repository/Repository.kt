package com.example.practiceproject.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.practiceproject.ArticlePagingSource
import com.example.practiceproject.api.APIInterface

class Repository(var context: Context, private val apiInterface: APIInterface) {

    fun getArticles() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 200),
        pagingSourceFactory = {ArticlePagingSource(apiInterface,context)}
    ).liveData

}