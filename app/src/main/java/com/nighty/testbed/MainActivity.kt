package com.nighty.testbed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.nighty.testbed.ui.theme.TestbedTheme
import com.nighty.testbed.viewmodels.MainViewModel
import com.nighty.testbed.viewmodels.MainViewModelFactory
import com.nighty.testbed.views.RandomText
import com.nighty.testbed.views.UserList
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

    override fun onCreate(savedInstanceState: Bundle?) {
        val db: AppDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app.db"
        ).allowMainThreadQueries().build()

        val mainViewModelFactory = MainViewModelFactory(httpClient, db)
        val viewModel: MainViewModel by viewModels(factoryProducer = { mainViewModelFactory })

        super.onCreate(savedInstanceState)
        setContent {
            TestbedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainView(viewModel)
                }
            }
        }
    }

    @Composable
    fun MainView(viewModel: MainViewModel) {
        val navController = rememberNavController()

        Column(verticalArrangement = Arrangement.SpaceBetween) {
            NavButtons(navController)
            NavHost(navController = navController, startDestination = "random-text") {
                composable("random-text") { RandomText(viewModel) }
                composable("user-list") { UserList(viewModel) }
            }
        }
    }

    @Composable
    fun NavButtons(navController: NavController) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                navController.navigate("random-text")
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

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        TestbedTheme {
            Text("Preview text")
        }
    }
}