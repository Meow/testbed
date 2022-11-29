package com.nighty.testbed

import android.net.sip.SipErrorCode.TIME_OUT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import io.ktor.client.HttpClient
import com.nighty.testbed.ui.theme.TestbedTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private const val HTTP_TIMEOUT = 60_000

class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                engine {
                    connectTimeout = HTTP_TIMEOUT
                    socketTimeout = HTTP_TIMEOUT
                }
            })
        }
    }

    private val mainViewModelFactory = MainViewModelFactory(httpClient)
    private val viewModel: MainViewModel by viewModels(factoryProducer = { mainViewModelFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestbedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String) {
        var inputTextField by remember { mutableStateOf("") }
        var bodyText by remember { mutableStateOf("") }

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
                    .border(1.dp, Color.Red)
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

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        TestbedTheme {
            Greeting("Android")
        }
    }
}