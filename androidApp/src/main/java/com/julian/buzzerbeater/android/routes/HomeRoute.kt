package com.julian.buzzerbeater.android.routes

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavBackStackEntry
import com.julian.buzzerbeater.android.home.HomeScreen
import com.julian.buzzerbeater.android.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

class HomeRoute : NavigationRoute() {
    override val route = "home"

    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        val homeViewModel = koinViewModel<HomeViewModel>()
        val uiState = homeViewModel.homeScreenUiState.collectAsState()

        val startBluetoothListener = {
            homeViewModel.startBluetoothStateTracking()
        }
        val stopBluetoothListener = {
            homeViewModel.stopBluetoothStateTracking()
        }
        val onClickTurnOnBluetooth =
            { launcher: ManagedActivityResultLauncher<Intent, ActivityResult> ->
                launcher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
            }
        HomeScreen(
            uiState.value,
            startBluetoothListener,
            stopBluetoothListener,
            onClickTurnOnBluetooth
        )
    }
}