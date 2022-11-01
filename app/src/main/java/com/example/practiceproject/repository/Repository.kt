package com.example.practiceproject.repository

import com.example.practiceproject.api.RetrofitBuilder
import com.example.practiceproject.model.Article
import com.example.practiceproject.model.DataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository {
    companion object{
        fun getTeslaArticles(): Flow<DataItem> = flow{
            val response = RetrofitBuilder.api.getTeslaArticles()
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}