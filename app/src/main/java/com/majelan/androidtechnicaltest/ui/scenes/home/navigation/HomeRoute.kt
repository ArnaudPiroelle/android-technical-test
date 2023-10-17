package com.majelan.androidtechnicaltest.ui.scenes.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.majelan.androidtechnicaltest.ui.navigation.TopLevelDestination
import com.majelan.androidtechnicaltest.ui.navigation.route.LeafScreen
import com.majelan.androidtechnicaltest.ui.scenes.album.navigation.navigateToAlbum
import com.majelan.androidtechnicaltest.ui.scenes.home.HomeScreen

data object HomeRoute : LeafScreen("home")

fun NavController.navigateToHome(root: TopLevelDestination, builder: NavOptionsBuilder.() -> Unit) =
    navigate(HomeRoute.createRoute(root), builder)

fun NavGraphBuilder.addHome(navController: NavController, root: TopLevelDestination) {
    composable(HomeRoute.createRoute(root)) {
        HomeScreen(
            onOpenAlbum = { albumId ->
                navController.navigateToAlbum(root = root, albumId = albumId)
            }
        )
    }
}

