package com.nighty.testbed.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
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
        NavHost(navController = navController, startDestination = "random-text") {
            composable("random-text") { RandomText(viewModel) }
            composable("user-list") { UserList(viewModel) }
            composable("ar-view") { arView.root }
        }
    }
}