package com.example.practiceproject.model

data class Article(
    val source: Source,
    val author: String? = null,
    val title: String?,
    val description: String,
    val url: String?,
    val urlToImage: String? = null,
    val publishedAt: String,
    val content: String
)

//data class Rail(
//    val shape: String,
//    val articles : List<Article>
//)
