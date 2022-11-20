package com.julian.buzzerbeater.android.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.julian.buzzerbeater.android.composables.BluetoothPermissionCheck
import com.julian.myapplication.ui.theme.BuzzerBeaterTheme

@Composable
fun HomeScreen() {
    BluetoothPermissionCheck {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 10.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(modifier = Modifier.weight(4f),
                        text = "Nous avons besoin que tu actives le bluetooth pour pouvoir jouer.")
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Bluetooth")
                    Switch(checked = true, onCheckedChange = {

                    }, Modifier.align(Alignment.CenterHorizontally))
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