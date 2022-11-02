package com.example.practiceproject.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.practiceproject.database.entities.Article
import com.example.practiceproject.database.entities.Images

data class ArticleWithImages(
    @Embedded val article: Article,
    @Relation(
        parentColumn = "title",
        entityColumn = "title"
    )
    val images : List<Images>
)
