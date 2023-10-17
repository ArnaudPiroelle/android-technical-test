package com.majelan.androidtechnicaltest.ui.scenes.player.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.majelan.androidtechnicaltest.ui.navigation.TopLevelDestination
import com.majelan.androidtechnicaltest.ui.navigation.Transition
import com.majelan.androidtechnicaltest.ui.navigation.route.LeafScreen
import com.majelan.androidtechnicaltest.ui.scenes.player.PlayerScreen

data object PlayerRoute : LeafScreen("player")

fun NavController.navigateToPlayer(root: TopLevelDestination, builder: NavOptionsBuilder.() -> Unit = {}) =
    navigate(PlayerRoute.createRoute(root), builder)

fun NavGraphBuilder.addPlayer(navController: NavController, root: TopLevelDestination) {
    composable(
        route = PlayerRoute.createRoute(root),
        enterTransition = Transition.verticalEnterTransition,
        popExitTransition = Transition.verticalPopExitTransition
    ) {
        PlayerScreen(
            onBack = {
                navController.navigateUp()
            }
        )
    }
}

