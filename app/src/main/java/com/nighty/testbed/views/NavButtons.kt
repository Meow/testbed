package com.nighty.testbed.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun NavButtons(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = {
            navController.navigate("ar-view")
        }) {
            Text(text = "AR View")
        }

        Button(onClick = {
            navController.navigate("user-list")
        }) {
            Text(text = "Profiles")
        }

        Button(onClick = {
            navController.navigate("random-text")
        }) {
            Text(text = "Settings")
        }
    }
}