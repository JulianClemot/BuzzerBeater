package com.julian.buzzerbeater.android.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julian.buzzerbeater.BluetoothHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import timber.log.Timber

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

    fun startBluetoothStateTracking() {
        Timber.e("is bluetooth enabled : ${bluetoothHelper.isBluetoothEnabled}")
        bluetoothHelper.bluetoothState.map { isBluetoothEnabled ->
            Timber.e("is bluetooth enabled #2: $isBluetoothEnabled")
            _homeScreenUiState.update {
                it.copy(
                    shouldStartHostEnabled = isBluetoothEnabled,
                    shouldStartGameEnabled = isBluetoothEnabled,
                    shouldDisplaySnackbar = !isBluetoothEnabled
                )
            }
        }.launchIn(viewModelScope)
        bluetoothHelper.startListeningBluetoothStatus()
    }

    fun stopBluetoothStateTracking() {
        bluetoothHelper.stopListeningBluetoothStatus()
    }
}