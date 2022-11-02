package com.example.practiceproject.database

import androidx.room.*
import com.example.practiceproject.database.entities.Article
import com.example.practiceproject.database.entities.Images
import com.example.practiceproject.database.relations.ArticleWithImages

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: Images)

    @Transaction
    @Query("SELECT * FROM article where title = :title")
    suspend fun getArticleWithImages(title : String) : List<ArticleWithImages>

}