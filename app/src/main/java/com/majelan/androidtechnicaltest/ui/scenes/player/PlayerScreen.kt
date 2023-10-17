package com.majelan.androidtechnicaltest.ui.scenes.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.majelan.androidtechnicaltest.ui.architecture.watchAsState
import com.majelan.androidtechnicaltest.ui.scenes.player.components.PlayerControls
import com.majelan.androidtechnicaltest.ui.scenes.player.components.PlayerHeader
import com.majelan.androidtechnicaltest.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = getViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.watchAsState()

    PlayerScreen(
        state = state,
        onBack = onBack,
        onPlayPause = { play ->
            if (play) {
                viewModel.handle(PlayerViewModel.Action.Play)
            } else {
                viewModel.handle(PlayerViewModel.Action.Pause)
            }
        },
        onNext = {
            viewModel.handle(PlayerViewModel.Action.Next)
        },
        onPrevious = {
            viewModel.handle(PlayerViewModel.Action.Previous)
        },
        onSeek = {
            viewModel.handle(PlayerViewModel.Action.Seek(it))
        }
    )
}

@Composable
private fun PlayerScreen(
    state: PlayerViewModel.State,
    onBack: () -> Unit,
    onPlayPause: (play: Boolean) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onSeek: (progress: Float) -> Unit,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(
                        alpha = .8f
                    )
                )
            )
        }
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = 16.dp
                ),
        ) {
            PlayerHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                title = state.mediaTitle,
                artist = state.mediaArtist,
                thumbnail = state.mediaThumbnail,
            )

            PlayerControls(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                progress = state.progress,
                isPlaying = state.isPlaying,
                totalDuration = state.totalDuration,
                currentPosition = state.currentPosition,
                onPlayPause = onPlayPause,
                onNext = onNext,
                onPrevious = onPrevious,
                onSeek = onSeek
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDark() {
    AppTheme(darkTheme = true) {
        Surface {
            PlayerScreen(
                state = PlayerViewModel.State(),
                onSeek = {},
                onPrevious = {},
                onNext = {},
                onPlayPause = {},
                onBack = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    AppTheme(darkTheme = false) {
        Surface {
            PlayerScreen(
                state = PlayerViewModel.State(),
                onSeek = {},
                onPrevious = {},
                onNext = {},
                onPlayPause = {},
                onBack = {}
            )
        }
    }
}