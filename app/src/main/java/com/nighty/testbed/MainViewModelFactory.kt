package com.nighty.testbed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.ktor.client.*

class MainViewModelFactory(private val client: HttpClient) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(client) as T
    }
}