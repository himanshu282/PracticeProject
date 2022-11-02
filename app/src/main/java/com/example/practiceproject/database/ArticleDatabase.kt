package com.example.practiceproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practiceproject.database.entities.Article
import com.example.practiceproject.database.entities.Images


@Database(
    entities = [Article::class, Images::class],
     version = 1)
abstract class ArticleDatabase : RoomDatabase(){
    abstract fun articleDao() : ArticleDao
}