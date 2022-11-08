package com.example.practiceproject.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id : Int =0,
    val title: String?
)
