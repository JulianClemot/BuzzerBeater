package com.julian.buzzerbeater.android.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val homeRoute = HomeRoute()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = homeRoute.route
    ) {
        addDestination(homeRoute)
    }
}

private fun NavGraphBuilder.addDestination(
    navigationRoute: NavigationRoute,
) = composable(
    route = navigationRoute.route,
    arguments = navigationRoute.arguments
) { backStackEntry ->
    navigationRoute.Content(backStackEntry)
}