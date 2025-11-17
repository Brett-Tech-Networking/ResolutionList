package com.example.resolutionlist.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.CheckBox
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.collectAsState
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.lazy.LazyColumn
import androidx.glance.lazy.items
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.resolutionlist.data.AppDatabase
import com.example.resolutionlist.data.ResolutionItem

class ResolutionWidget : GlanceAppWidget() {

    companion object {
        val itemIdKey = ActionParameters.Key<Int>("itemId")
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val db = AppDatabase.getDatabase(context)
            val resolutions by db.resolutionDao().getAll().collectAsState(initial = emptyList())

            WidgetContent(resolutions = resolutions)
        }
    }

    @Composable
    private fun WidgetContent(resolutions: List<ResolutionItem>) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .appWidgetBackground()
                .background(ColorProvider(android.R.color.background_light))
                .padding(8.dp)
        ) {
            Text(
                text = "Resolutions",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorProvider(android.R.color.black)
                ),
                modifier = GlanceModifier.padding(bottom = 8.dp)
            )
            if (resolutions.isEmpty()) {
                Text("No resolutions yet. Add some in the app!")
            } else {
                LazyColumn {
                    items(resolutions, itemId = { it.id.toLong() }) { item ->
                        WidgetItem(item = item)
                        Spacer(modifier = GlanceModifier.height(4.dp))
                    }
                }
            }
        }
    }

    @Composable
    private fun WidgetItem(item: ResolutionItem) {
        Row(
            modifier = GlanceModifier
                .fillMaxWidth()
                .background(ColorProvider(android.R.color.white))
                .padding(12.dp)
                .clickable(
                    onClick = actionRunCallback<UpdateResolutionCallback>(
                        parameters = actionParametersOf(
                            itemIdKey to item.id
                        )
                    )
                ),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            CheckBox(
                checked = item.isCompleted,
                onCheckedChange = null // Handled by row click
            )
            Spacer(modifier = GlanceModifier.width(8.dp))
            Text(
                text = item.text,
                style = TextStyle(
                    color = ColorProvider(android.R.color.black),
                    textDecoration = if (item.isCompleted) androidx.glance.text.TextDecoration.LineThrough else null
                ),
                modifier = GlanceModifier.defaultWeight()
            )
        }
    }
}