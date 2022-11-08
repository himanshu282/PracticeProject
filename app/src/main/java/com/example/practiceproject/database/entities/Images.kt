package com.example.practiceproject.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Images(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val url: String?,
    val urlToImage: String? = null,
    val title: String?
)
