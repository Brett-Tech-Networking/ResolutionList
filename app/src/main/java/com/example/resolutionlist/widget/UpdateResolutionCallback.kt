package com.example.resolutionlist.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import com.example.resolutionlist.data.AppDatabase

class UpdateResolutionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val id = parameters[ResolutionWidget.itemIdKey] ?: return

        val db = AppDatabase.getDatabase(context)
        val dao = db.resolutionDao()
        val item = dao.getById(id) ?: return

        // Toggle completion status
        dao.update(item.copy(isCompleted = !item.isCompleted))

        // Update the widget state
        ResolutionWidget().update(context, glanceId)
    }
}