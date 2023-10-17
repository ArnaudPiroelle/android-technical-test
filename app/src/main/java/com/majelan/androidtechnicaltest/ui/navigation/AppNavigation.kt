package com.majelan.androidtechnicaltest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.majelan.androidtechnicaltest.ui.navigation.graph.addHomeTopLevel

sealed class TopLevelDestination(val route: String) {
    data object Home : TopLevelDestination("home_root")
}

@Composable
fun AppNavigation(modifier: Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TopLevelDestination.Home.route,
    ) {
        addHomeTopLevel(navController)
    }
}