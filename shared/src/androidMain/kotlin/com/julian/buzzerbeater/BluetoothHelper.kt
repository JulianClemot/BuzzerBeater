package com.julian.buzzerbeater

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter


actual class BluetoothHelper(private val context: Context) {
    private val bluetoothAdapter =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    actual var isBluetoothEnabled = bluetoothAdapter.isEnabled

    private val bluetoothStateReceiver = BluetoothStateReceiver(isBluetoothEnabled)

    val bluetoothState = bluetoothStateReceiver.state

    actual fun activateBluetooth() {
        //Nothing to do so far
    }

    actual fun startListeningBluetoothStatus() {
        context.registerReceiver(
            bluetoothStateReceiver,
            IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        )
    }

    actual fun stopListeningBluetoothStatus() {
        context.unregisterReceiver(bluetoothStateReceiver)
    }
}