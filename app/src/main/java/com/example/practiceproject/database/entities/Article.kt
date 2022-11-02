package com.example.practiceproject.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article(
    @PrimaryKey(autoGenerate = false)
    val title: String
)
