package com.example.resolutionlist

import android.app.Application
import com.example.resolutionlist.data.AppDatabase

class ResolutionApp : Application() {
    // Lazy-initialize the database
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}