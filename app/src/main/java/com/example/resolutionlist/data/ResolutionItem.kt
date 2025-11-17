package com.example.resolutionlist.data

import androidxTo.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resolutions")
data class ResolutionItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val isCompleted: Boolean = false
)