package com.julian.buzzerbeater.android.home

import androidx.lifecycle.ViewModel
import com.julian.buzzerbeater.BluetoothHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class HomeViewModel(private val bluetoothHelper: BluetoothHelper) : ViewModel() {

    private val _homeScreenUiState = MutableStateFlow(
        HomeScreenUiState(
            shouldStartHostEnabled = bluetoothHelper.isBluetoothEnabled,
            shouldStartGameEnabled = bluetoothHelper.isBluetoothEnabled,
            shouldDisplaySnackbar = !bluetoothHelper.isBluetoothEnabled
        )
    )
    val homeScreenUiState: StateFlow<HomeScreenUiState>
        get() = _homeScreenUiState

    init {
        bluetoothHelper.bluetoothState.map { isBluetoothEnabled ->
            _homeScreenUiState.update {
                it.copy(
                    shouldStartHostEnabled = isBluetoothEnabled,
                    shouldStartGameEnabled = isBluetoothEnabled,
                    shouldDisplaySnackbar = !isBluetoothEnabled
                )
            }
        }
        bluetoothHelper.startListeningBluetoothStatus()
    }
}