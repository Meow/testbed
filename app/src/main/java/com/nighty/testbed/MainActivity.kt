package com.nighty.testbed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nighty.testbed.ui.theme.TestbedTheme
import io.ktor.client.*
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
                    RandomText()
                }
            }
        }
    }

    @Composable
    fun RandomText() {
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
            RandomText()
        }
    }
}