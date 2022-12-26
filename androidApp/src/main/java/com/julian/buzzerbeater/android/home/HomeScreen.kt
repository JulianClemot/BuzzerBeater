package com.julian.buzzerbeater.android.home

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.julian.buzzerbeater.BluetoothHelper
import com.julian.buzzerbeater.android.composables.BluetoothPermissionCheck
import com.julian.myapplication.ui.theme.BuzzerBeaterTheme
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(context: Context = LocalContext.current) {
    BluetoothPermissionCheck {
        val scope = rememberCoroutineScope()
        val bluetoothHelper = BluetoothHelper(context)
        val isBluetoothEnabled = bluetoothHelper.bluetoothState.collectAsState(bluetoothHelper.isBluetoothEnabled)
        val snackbarHostState = remember { SnackbarHostState() }
        val lifecycleOwner = LocalLifecycleOwner.current
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) {
            DisposableEffect(key1 = lifecycleOwner) {
                bluetoothHelper.startListeningBluetoothStatus()
                onDispose {
                    bluetoothHelper.stopListeningBluetoothStatus()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
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
                    Timber.e("isBluetoothEnabled : ${isBluetoothEnabled.value}")
                    if (!isBluetoothEnabled.value) {
                        scope.launch {
                            val snackbarResult = snackbarHostState.showSnackbar(
                                "Vous devez activer votre Bluetooth pour jouer",
                                "Activer",
                                duration = SnackbarDuration.Indefinite,
                                withDismissAction = false
                            )
                            when (snackbarResult) {
                                SnackbarResult.Dismissed -> Timber.e("snackbar was dismissed")
                                SnackbarResult.ActionPerformed -> Timber.e("we clicked on activate")
                            }
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                        }
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