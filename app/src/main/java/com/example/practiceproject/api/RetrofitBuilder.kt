package com.example.practiceproject.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitBuilder {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BaseURL.baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api:APIInterface by lazy {
        retrofit.create(APIInterface::class.java)
    }
}