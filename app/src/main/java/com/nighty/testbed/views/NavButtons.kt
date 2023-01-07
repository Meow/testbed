package com.nighty.testbed.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.nighty.testbed.viewmodels.MainViewModel

@Composable
fun NavButtons(viewModel: MainViewModel, navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = {
            viewModel.requestQrCodeScanner()
            navController.navigate("ar-view")
        }) {
            Text(text = "Scan QRC")
        }

        Button(onClick = {
            navController.navigate("user-list")
        }) {
            Text(text = "Profiles")
        }

        Button(onClick = {
            navController.navigate("settings")
        }) {
            Text(text = "Settings")
        }
    }
}