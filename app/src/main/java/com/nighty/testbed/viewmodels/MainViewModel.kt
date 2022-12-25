package com.nighty.testbed.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nighty.testbed.MainViewState
import com.nighty.testbed.api.ApplicationApi
import io.ktor.client.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(client: HttpClient) : ViewModel() {
    private val internalStateFlow = MutableStateFlow<MainViewState>(MainViewState.Ready(""))
    private val api = ApplicationApi(client)
    val stateFlow: StateFlow<MainViewState> = internalStateFlow

    fun loadLoremIpsum(count: Int, onFinish: (response: String) -> Unit) {
        viewModelScope.launch {
            internalStateFlow.value = MainViewState.Loading
            val response = api.loremIpsum(count)
            internalStateFlow.value = MainViewState.Ready(response)
        }
    }
}