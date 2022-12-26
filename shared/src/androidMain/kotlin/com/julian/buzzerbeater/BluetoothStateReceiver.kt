package com.julian.buzzerbeater

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BluetoothStateReceiver(initialBluetoothStatus : Boolean) : BroadcastReceiver() {
    private val _state = MutableStateFlow(initialBluetoothStatus) // private mutable state flow
    val state = _state.asStateFlow() // publicly exposed as read-only state flow

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
            when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
                BluetoothAdapter.STATE_OFF -> {
                    _state.update { false }
                }
                BluetoothAdapter.STATE_ON -> {
                    _state.update { true }
                }
            }
        }
    }
}