package com.julian.buzzerbeater.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.julian.buzzerbeater.android.home.HomeScreen
import com.julian.buzzerbeater.android.home.HomeScreenUiState
import com.julian.buzzerbeater.android.routes.AppNavHost
import com.julian.myapplication.ui.theme.BuzzerBeaterTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContent {
                BuzzerBeaterTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavHost()
                    }
                }
            }
        }
    }
}

@Preview
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
