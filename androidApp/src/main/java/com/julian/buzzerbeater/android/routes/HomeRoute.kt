package com.julian.buzzerbeater.android.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.julian.buzzerbeater.android.home.HomeScreen

class HomeRoute(
) : NavigationRoute() {
    override val route = "home"

    @Composable
    override fun Content(backStackEntry: NavBackStackEntry) {
        HomeScreen()
    }
}