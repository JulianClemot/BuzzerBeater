package com.julian.buzzerbeater.android.home

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.julian.buzzerbeater.android.composables.BluetoothPermissionCheck
import com.julian.myapplication.ui.theme.BuzzerBeaterTheme
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun HomeScreen(
    homeScreenUiState: HomeScreenUiState,
    startBluetoothListener: () -> Unit,
    stopBluetoothListener: () -> Unit,
    onClickTurnOnBluetooth: (launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) -> Unit
) {
    Timber.e("ui state : $homeScreenUiState")
    BluetoothPermissionCheck {
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        val lifecycleOwner = LocalLifecycleOwner.current
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {}

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { paddingValues ->
            DisposableEffect(key1 = lifecycleOwner) {
                startBluetoothListener()
                onDispose {
                    stopBluetoothListener()
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
                            enabled = homeScreenUiState.shouldStartGameEnabled,
                            onClick = { /*TODO*/ }) {
                            Text(text = "Rejoindre une partie")
                        }
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .padding(horizontal = 4.dp),
                            shape = RoundedCornerShape(8.dp),
                            enabled = homeScreenUiState.shouldStartHostEnabled,
                            onClick = { /*TODO*/ }) {
                            Text(text = "Héberger une partie")
                        }
                    }
                }

                SideEffect {
                    if (homeScreenUiState.shouldDisplaySnackbar) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Vous devez activer votre Bluetooth pour jouer",
                                "Activer",
                                duration = SnackbarDuration.Indefinite,
                                withDismissAction = false
                            ).also { snackbarResult ->
                                when (snackbarResult) {
                                    SnackbarResult.ActionPerformed -> {
                                        onClickTurnOnBluetooth(launcher)
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
        HomeScreen(
            HomeScreenUiState(
                shouldStartHostEnabled = true,
                shouldStartGameEnabled = true,
                shouldDisplaySnackbar = true
            ),
            {},
            {},
            {},
        )
    }
}