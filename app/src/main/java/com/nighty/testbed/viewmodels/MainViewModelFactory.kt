package com.nighty.testbed.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nighty.testbed.AppDatabase
import io.ktor.client.*

class MainViewModelFactory(private val client: HttpClient, private val db: AppDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(client, db) as T
    }
}