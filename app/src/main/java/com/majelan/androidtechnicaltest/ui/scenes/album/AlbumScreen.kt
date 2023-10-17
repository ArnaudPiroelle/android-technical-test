package com.majelan.androidtechnicaltest.ui.scenes.album

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.majelan.androidtechnicaltest.R
import com.majelan.androidtechnicaltest.domain.model.Album
import com.majelan.androidtechnicaltest.domain.model.Artist
import com.majelan.androidtechnicaltest.domain.model.Track
import com.majelan.androidtechnicaltest.ui.architecture.watchAsState
import com.majelan.androidtechnicaltest.ui.scenes.album.AlbumViewModel.Action.LoadAlbum
import com.majelan.androidtechnicaltest.ui.scenes.album.components.AlbumHeader
import com.majelan.androidtechnicaltest.ui.scenes.album.components.TrackItem
import com.majelan.androidtechnicaltest.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun AlbumScreen(
    albumId: String,
    viewModel: AlbumViewModel = getViewModel(
        parameters = { parametersOf(AlbumViewModel.Params(albumId)) }
    ),
    onBack: () -> Unit,
) {

    val state by viewModel.watchAsState()

    LaunchedEffect(Unit) {
        viewModel.handle(LoadAlbum)
    }

    AlbumScreen(
        state = state,
        onBack = onBack,
        onPlayAlbum = {
            viewModel.handle(AlbumViewModel.Action.PlayAlbum)
        }
    )

}

@Composable
private fun AlbumScreen(
    state: AlbumViewModel.State,
    onBack: () -> Unit,
    onPlayAlbum: () -> Unit
) {
    val listState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(
                        alpha = .8f
                    )
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = true,
                shape = FloatingActionButtonDefaults.smallShape,
                text = { Text(text = stringResource(id = R.string.action_play)) },
                icon = { Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null) },
                onClick = onPlayAlbum
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { contentPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding(),
                bottom = 80.dp
            ),
        ) {
            item {
                AlbumHeader(
                    modifier = Modifier.fillMaxWidth(),
                    title = state.album.title,
                    artist = state.album.artist.name,
                    thumbnail = state.album.thumbnail
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            items(state.tracks) { track ->
                TrackItem(
                    modifier = Modifier,
                    thumbnail = track.thumbnail,
                    name = track.title,
                    duration = track.duration.toDuration(DurationUnit.SECONDS),
                    number = track.trackNumber,
                    total = state.album.totalTrackCount,
                    onClick = {}
                )
            }
        }
    }
}

private val artist = Artist("id", "artist name")
private val album = Album(
    id = "id1",
    title = "album title",
    artist = artist,
    genre = "genre",
    thumbnail = "image",
    totalTrackCount = 1,
    site = "site"
)
private val track = Track(
    id = "id",
    title = "title",
    artist = artist,
    thumbnail = "image",
    trackNumber = 1,
    duration = 10,
    source = "source"
)

@Preview
@Composable
private fun PreviewDark() {
    AppTheme(darkTheme = true) {
        Surface {
            AlbumScreen(
                state = AlbumViewModel.State(
                    album = album,
                    tracks = listOf(track)
                ),
                onBack = {},
                onPlayAlbum = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    AppTheme(darkTheme = false) {
        Surface {
            AlbumScreen(
                state = AlbumViewModel.State(
                    album = album,
                    tracks = listOf(track)
                ),
                onBack = {},
                onPlayAlbum = {}
            )
        }
    }
}