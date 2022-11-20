package com.julian.buzzerbeater.android.composables

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BluetoothPermissionCheck(
    content: @Composable () -> Unit,
) {
    val permissionState =
        rememberMultiplePermissionsState(getAskedPermissions())

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionState.launchMultiplePermissionRequest()
                }
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    if (permissionState.allPermissionsGranted) {
        content()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (permissionState.shouldShowRationale) { // ask for permission through launch permission request
                Text(
                    textAlign = TextAlign.Center,
                    text = "Nous utilisons la technologie Bluetooth afin de communiquer entre les différentes équipes. Nous avons besoin de votre autorisation pour l'activer"
                )
                Button(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = { permissionState.launchMultiplePermissionRequest() },
                ) {
                    Text("Donner mon autorisation", color = Color.White)
                }
            } // else { // redirect to app settings
        }
    }
}

fun getAskedPermissions() = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
    listOf(Manifest.permission.BLUETOOTH)
} else {
    listOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_CONNECT,
    )
}