package com.nighty.testbed.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nighty.testbed.MainViewState
import com.nighty.testbed.viewmodels.MainViewModel

@Composable
fun Settings(viewModel: MainViewModel) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Button(onClick = {
            viewModel.deleteAllUsers()
        }) {
            Text(text = "Flush Database")
        }
        Button(onClick = {
            viewModel.seedUsers()
        }) {
            Text(text = "Seed Database")
        }

        val state = viewModel.stateFlow.collectAsState().value

        when (val s = state) {
            is MainViewState.AllUsersDeleted -> {
                Text("All users have been successfully deleted.")
            }
            is MainViewState.UsersSeeded -> {
                Text("Successfully seeded database with 10 random users.")
            }
            is MainViewState.Loading -> {
                Text("Loading, please wait...")
            }
            else -> {}
        }
    }
}