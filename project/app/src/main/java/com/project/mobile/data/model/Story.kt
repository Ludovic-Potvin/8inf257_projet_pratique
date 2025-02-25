package com.project.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stories")
data class Story (
    @PrimaryKey() val id : Int? = null,
    val title: String,
    val description: String,
    val category: String,
    val hour: String,
    val days: String,
    val priority: Int
)