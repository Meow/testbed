package com.nighty.testbed

sealed interface MainViewState {
    object Loading: MainViewState

    data class Ready(val loremIpsum: String) : MainViewState
}
