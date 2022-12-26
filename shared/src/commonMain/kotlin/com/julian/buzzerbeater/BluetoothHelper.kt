package com.julian.buzzerbeater

expect class BluetoothHelper {
    var isBluetoothEnabled : Boolean
    fun toggleBluetooth(shouldActivate: Boolean)
    fun startListeningBluetoothStatus()
    fun stopListeningBluetoothStatus()
}