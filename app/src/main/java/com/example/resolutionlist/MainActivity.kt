package com.example.resolutionlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resolutionlist.ui.ResolutionScreen
import com.example.resolutionlist.ui.theme.ResolutionListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResolutionListTheme {
                val application = LocalContext.current.applicationContext as ResolutionApp
                val viewModel: ResolutionViewModel = viewModel(
                    factory = ResolutionViewModel.Factory(application)
                )
                ResolutionScreen(viewModel = viewModel)
            }
        }
    }
}