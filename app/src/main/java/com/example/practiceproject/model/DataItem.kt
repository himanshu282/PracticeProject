package com.example.practiceproject.model

data class DataItem(
    val status: String,
    val totalResults: Long,
    val articles: List<Article>
)
