package com.majelan.androidtechnicaltest.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.majelan.androidtechnicaltest.ui.navigation.TopLevelDestination
import com.majelan.androidtechnicaltest.ui.scenes.album.navigation.addAlbumDetails
import com.majelan.androidtechnicaltest.ui.scenes.home.navigation.HomeRoute
import com.majelan.androidtechnicaltest.ui.scenes.home.navigation.addHome
import com.majelan.androidtechnicaltest.ui.scenes.player.navigation.addPlayer

fun NavGraphBuilder.addHomeTopLevel(navController: NavController) {

    val root = TopLevelDestination.Home

    navigation(
        route = root.route,
        startDestination = HomeRoute.createRoute(root),
    ) {
        addHome(navController, root)
        addAlbumDetails(navController, root)
        addPlayer(navController, root)
    }

}