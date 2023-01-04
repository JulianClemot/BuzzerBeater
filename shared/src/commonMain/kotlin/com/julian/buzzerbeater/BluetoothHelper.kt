package com.julian.buzzerbeater

expect class BluetoothHelper {
    var isBluetoothEnabled : Boolean
    fun activateBluetooth()
    fun startListeningBluetoothStatus()
    fun stopListeningBluetoothStatus()
}