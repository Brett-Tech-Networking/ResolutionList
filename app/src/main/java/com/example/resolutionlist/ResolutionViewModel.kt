package com.example.resolutionlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.resolutionlist.data.ResolutionDao
import com.example.resolutionlist.data.ResolutionItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ResolutionViewModel(private val dao: ResolutionDao) : AndroidViewModel(Application()) {

    val resolutions: StateFlow<List<ResolutionItem>> = dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun addResolution(text: String) {
        if (text.isNotBlank()) {
            viewModelScope.launch {
                dao.insert(ResolutionItem(text = text))
            }
        }
    }

    fun toggleCompleted(item: ResolutionItem) {
        viewModelScope.launch {
            dao.update(item.copy(isCompleted = !item.isCompleted))
        }
    }

    fun deleteResolution(item: ResolutionItem) {
        viewModelScope.launch {
            dao.delete(item)
        }
    }

    // Factory to create the ViewModel with the DAO dependency
    companion object {
        fun Factory(application: Application): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ResolutionViewModel::class.java)) {
                        val dao = (application as ResolutionApp).database.resolutionDao()
                        return ResolutionViewModel(dao) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}