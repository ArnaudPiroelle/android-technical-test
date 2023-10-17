package com.majelan.androidtechnicaltest.ui.scenes.album.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.majelan.androidtechnicaltest.ui.navigation.TopLevelDestination
import com.majelan.androidtechnicaltest.ui.navigation.Transition
import com.majelan.androidtechnicaltest.ui.navigation.route.LeafScreen
import com.majelan.androidtechnicaltest.ui.scenes.album.AlbumScreen

private const val NavArgAlbumId = "albumId"

data object AlbumRoute : LeafScreen("album?$NavArgAlbumId={$NavArgAlbumId}") {
    fun createRoute(root: TopLevelDestination, albumId: String): String {
        val escapedAlbumId = albumId
            .replace("&", "_")
            .replace(" ", "+")
        return createRoute(root).replace("{$NavArgAlbumId}", escapedAlbumId)
    }
}

fun NavController.navigateToAlbum(root: TopLevelDestination, albumId: String, builder: NavOptionsBuilder.() -> Unit = {}) =
    navigate(AlbumRoute.createRoute(root, albumId), builder)

fun NavGraphBuilder.addAlbumDetails(navController: NavController, root: TopLevelDestination) {
    composable(
        route = AlbumRoute.createRoute(root),
        arguments = listOf(
            navArgument(NavArgAlbumId) { type = NavType.StringType },
        ),
        enterTransition = Transition.horizontalEnterTransition,
        popExitTransition = Transition.horizontalPopExitTransition
    ) { entry ->

        val arguments = entry.arguments
        val albumId = arguments?.getString(NavArgAlbumId)!!
            .replace("_", "&")
            .replace("+", " ")

        AlbumScreen(
            albumId = albumId,
            onBack = {
                navController.navigateUp()
            }
        )
    }
}

