package com.julian.buzzerbeater.android.home

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.julian.buzzerbeater.BluetoothHelper
import com.julian.buzzerbeater.android.composables.BluetoothPermissionCheck
import com.julian.myapplication.ui.theme.BuzzerBeaterTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(bluetoothHelper: BluetoothHelper = get()) {
    BluetoothPermissionCheck {
        val scope = rememberCoroutineScope()
        val isBluetoothEnabled =
            bluetoothHelper.bluetoothState.collectAsState(bluetoothHelper.isBluetoothEnabled)
        val snackbarHostState = remember { SnackbarHostState() }
        val lifecycleOwner = LocalLifecycleOwner.current
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {}

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { paddingValues ->
            DisposableEffect(key1 = lifecycleOwner) {
                bluetoothHelper.startListeningBluetoothStatus()
                onDispose {
                    bluetoothHelper.stopListeningBluetoothStatus()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(all = 10.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Bienvenue sur Buzzer Beater")
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .padding(horizontal = 4.dp),
                            shape = RoundedCornerShape(8.dp),
                            enabled = isBluetoothEnabled.value,
                            onClick = { /*TODO*/ }) {
                            Text(text = "Rejoindre une partie")
                        }
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .padding(horizontal = 4.dp),
                            shape = RoundedCornerShape(8.dp),
                            enabled = isBluetoothEnabled.value,
                            onClick = { /*TODO*/ }) {
                            Text(text = "Héberger une partie")
                        }
                    }
                }

                SideEffect {
                    if (!isBluetoothEnabled.value) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Vous devez activer votre Bluetooth pour jouer",
                                "Activer",
                                duration = SnackbarDuration.Indefinite,
                                withDismissAction = false
                            ).also { snackbarResult ->
                                when (snackbarResult) {
                                    SnackbarResult.ActionPerformed -> {
                                        launcher.launch(
                                            Intent(
                                                BluetoothAdapter.ACTION_REQUEST_ENABLE
                                            )
                                        )
                                    }
                                    SnackbarResult.Dismissed -> Unit
                                }
                            }
                        }
                    } else {
                        snackbarHostState.currentSnackbarData?.dismiss()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BuzzerBeaterTheme {
        HomeScreen()
    }
}