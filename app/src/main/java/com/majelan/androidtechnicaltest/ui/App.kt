package com.majelan.androidtechnicaltest.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.majelan.androidtechnicaltest.ui.architecture.watchAsState
import com.majelan.androidtechnicaltest.ui.components.MiniPlayer
import com.majelan.androidtechnicaltest.ui.navigation.AppNavigation
import com.majelan.androidtechnicaltest.ui.navigation.TopLevelDestination
import com.majelan.androidtechnicaltest.ui.scenes.player.navigation.PlayerRoute
import com.majelan.androidtechnicaltest.ui.scenes.player.navigation.navigateToPlayer
import org.koin.androidx.compose.getViewModel

@Composable
fun App(
    viewModel: AppViewModel = getViewModel()
) {
    val state by viewModel.watchAsState()

    val navController = rememberNavController()

    val isPlayer by navController.isPlayer()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (state.displayMiniPlayer) {
                AnimatedVisibility(
                    visible = !isPlayer,
                    enter = expandVertically(expandFrom = Alignment.Top),
                    exit = shrinkVertically(shrinkTowards = Alignment.Top)
                ) {
                    MiniPlayer(
                        progress = state.progress,
                        title = state.mediaTitle,
                        artist = state.mediaArtist,
                        thumbnail = state.mediaThumbnail,
                        onOpenPlayer = {
                            navController.navigateToPlayer(TopLevelDestination.Home)
                        },
                        isPlaying = state.isPlaying,
                        onPlayPause = { play ->
                            if (play) {
                                viewModel.handle(AppViewModel.Action.Play)
                            } else {
                                viewModel.handle(AppViewModel.Action.Pause)
                            }
                        }
                    )
                }
            }
        }
    ) { contentPadding ->
        AppNavigation(
            modifier = Modifier
                .padding(bottom = contentPadding.calculateBottomPadding())
                .fillMaxSize(),
            navController = navController,
        )
    }
}

@Stable
@Composable
private fun NavController.isPlayer(): State<Boolean> {
    val selectedItem = remember { mutableStateOf(false) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            selectedItem.value = when (destination.route) {
                PlayerRoute.createRoute(TopLevelDestination.Home) -> true
                else -> false
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}