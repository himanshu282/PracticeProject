package com.example.practiceproject.api

import com.example.practiceproject.model.Article
import com.example.practiceproject.model.DataItem
import retrofit2.http.GET

interface APIInterface {
    @GET("/v2/everything?q=tesla&from=2022-10-02&sortBy=publishedAt&apiKey=8bd5e3c38d3b4967b350cb46a9972cbd")
    suspend fun getTeslaArticles() : DataItem
}