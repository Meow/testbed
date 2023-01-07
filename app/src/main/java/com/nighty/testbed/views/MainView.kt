package com.nighty.testbed.views

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.journeyapps.barcodescanner.ScanOptions
import com.nighty.testbed.MainViewState
import com.nighty.testbed.viewmodels.MainViewModel

@Composable
fun MainView(viewModel: MainViewModel, barcodeLauncher: ActivityResultLauncher<ScanOptions>) {
    val navController = rememberNavController()
    val state = viewModel.stateFlow.collectAsState().value

    Column(verticalArrangement = Arrangement.SpaceBetween) {
        NavButtons(viewModel, navController)
        NavHost(navController = navController, startDestination = "user-list") {
            composable("settings") { Settings(viewModel) }
            composable("user-list") { UserList(viewModel) }
            composable("ar-view") {
                when (val s = state) {
                    is MainViewState.QrCodeScanned -> {
                        viewModel.findUserByUsername(s.code)
                    }
                    is MainViewState.UserLoadedFromQr -> {
                        User(viewModel, navController, s.usr)
                    }
                    is MainViewState.QrCodeScannerRequested -> {
                        val opts = ScanOptions()

                        opts.setOrientationLocked(false)
                        opts.setDesiredBarcodeFormats(ScanOptions.QR_CODE)

                        barcodeLauncher.launch(opts)
                    }
                    else -> {}
                }
            }
        }
    }
}