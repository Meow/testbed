package com.nighty.testbed

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.nighty.testbed.ui.theme.TestbedTheme
import com.nighty.testbed.viewmodels.MainViewModel
import com.nighty.testbed.viewmodels.MainViewModelFactory
import com.nighty.testbed.views.MainView
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

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val db: AppDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app.db"
        ).allowMainThreadQueries().build()

        val mainViewModelFactory = MainViewModelFactory(httpClient, db)
        val viewModel: MainViewModel by viewModels(factoryProducer = { mainViewModelFactory })
        val barcodeLauncher = registerForActivityResult(
            ScanContract()
        ) { result: ScanIntentResult ->
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                viewModel.setQrCodeContent(result.contents)
            }
        }

        super.onCreate(savedInstanceState)

        setContent {
            TestbedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainView(viewModel, barcodeLauncher)
                }
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