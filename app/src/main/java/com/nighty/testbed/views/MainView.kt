package com.nighty.testbed.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nighty.testbed.ar.HelloArView
import com.nighty.testbed.viewmodels.MainViewModel

@Composable
fun MainView(viewModel: MainViewModel, arView: HelloArView) {
    val navController = rememberNavController()

    Column(verticalArrangement = Arrangement.SpaceBetween) {
        NavButtons(navController)
        NavHost(navController = navController, startDestination = "ar-view") {
            composable("random-text") { RandomText(viewModel) }
            composable("user-list") { UserList(viewModel) }
            composable("ar-view") {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        arView.root
                    },
                    update = { view ->

                    }
                )
            }
        }
    }
}