package com.nighty.testbed

import com.nighty.testbed.models.User

sealed interface MainViewState {
    object Loading : MainViewState
    object UserDeleted : MainViewState
    object QrCodeScannerRequested : MainViewState

    data class Ready(val loremIpsum: String) : MainViewState
    data class UserCreated(val usr: User?) : MainViewState
    data class UsersLoaded(val users: List<User>) : MainViewState
    data class QrCodeScanned(val code: String) : MainViewState
    data class UserLoadedFromQr(val usr: User?) : MainViewState
}
