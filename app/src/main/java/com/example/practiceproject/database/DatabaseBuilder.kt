package com.example.practiceproject.database

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var INSTANCE: ArticleDatabase? = null
    fun getInstance(context: Context): ArticleDatabase {
        if (INSTANCE == null) {
            synchronized(ArticleDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }
    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            ArticleDatabase::class.java,
            "article-images"
        ).build()
}
