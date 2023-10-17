package com.majelan.androidtechnicaltest.ui.scenes.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
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
import com.majelan.androidtechnicaltest.ui.architecture.watchAsState
import com.majelan.androidtechnicaltest.ui.scenes.home.HomeViewModel.Action.LoadCatalog
import com.majelan.androidtechnicaltest.ui.scenes.home.components.VinylEntry
import com.majelan.androidtechnicaltest.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = getViewModel(),
    onOpenAlbum: (albumId: String) -> Unit
) {
    val state by viewModel.watchAsState()

    LaunchedEffect(Unit) {
        viewModel.handle(LoadCatalog)
    }

    HomeScreen(
        state = state,
        onOpenAlbum = onOpenAlbum,
        onPlayAlbum = { viewModel.handle(HomeViewModel.Action.PlayAlbum(it)) }
    )
}

@Composable
private fun HomeScreen(
    state: HomeViewModel.State,
    onOpenAlbum: (albumId: String) -> Unit,
    onPlayAlbum: (albumId: String) -> Unit,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.home_title))
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(
                        alpha = .8f
                    )
                )
            )
        }
    ) { contentPadding ->
        val listState = rememberLazyListState()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding(),
                bottom = 16.dp
            ),
        ) {
            items(state.albums) { album ->
                VinylEntry(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(horizontal = 16.dp),
                    id = album.id,
                    albumName = album.title,
                    artist = album.artist.name,
                    thumbnail = album.thumbnail,
                    onVinylClick = { onOpenAlbum(album.id) },
                    onPlayAlbum = {
                        onPlayAlbum(album.id)
                    }
                )
            }
        }
    }

}

private val artist = Artist("id", "artist name")
private val album1 = Album(
    id = "id1",
    title = "album title",
    artist = artist,
    genre = "genre",
    thumbnail = "image",
    totalTrackCount = 1,
    site = "site"
)

private val album2 = Album(
    id = "id2",
    title = "album title",
    artist = artist,
    genre = "genre",
    thumbnail = "image",
    totalTrackCount = 1,
    site = "site"
)


@Preview
@Composable
private fun PreviewDark() {
    AppTheme(darkTheme = true) {
        Surface {
            HomeScreen(
                state = HomeViewModel.State(
                    albums = listOf(album1, album2)
                ),
                onOpenAlbum = {},
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
            HomeScreen(
                state = HomeViewModel.State(
                    albums = listOf(album1, album2)
                ),
                onOpenAlbum = {},
                onPlayAlbum = {}
            )
        }
    }
}