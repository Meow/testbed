package com.nighty.testbed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RandomText(viewModel: MainViewModel = viewModel()) {
    var inputTextField by remember { mutableStateOf("1") }
    var bodyText by remember { mutableStateOf("(text will be displayed here once fetched)") }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = inputTextField,
            onValueChange = { inputTextField = it },
            label = { Text("Amount of paragraphs") }
        )
        Button(
            onClick = {
                viewModel.loadLoremIpsum(
                    inputTextField.toInt(),
                    onFinish = { response ->
                        bodyText = response
                    })
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Load")
        }

        val state = viewModel.stateFlow.collectAsState().value

        when (val s = state) {
            MainViewState.Loading -> {
                CircularProgressIndicator()
            }
            is MainViewState.Ready -> {
                Text(text = s.loremIpsum)
            }
        }
    }
}