package com.majelan.androidtechnicaltest.ui.scenes.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.majelan.androidtechnicaltest.R
import com.majelan.androidtechnicaltest.ui.theme.AppTheme

@Composable
internal fun VinylEntry(
    modifier: Modifier,
    id: String,
    albumName: String,
    artist: String,
    thumbnail: String,
    onVinylClick: () -> Unit,
    onPlayAlbum: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var openCover by rememberSaveable(id) {
        mutableStateOf(false)
    }

    LaunchedEffect(id) {
        openCover = true
    }

    Box(
        modifier = Modifier
            .clickable { onVinylClick() }
            .then(modifier)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            //verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            AnimatedVinylCoverWithDisc(
                modifier = Modifier.fillMaxHeight(),
                thumbnail = thumbnail
            )
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = albumName.uppercase(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = artist.uppercase(),
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            IconButton(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    expanded = !expanded
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreHoriz,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = .3f),
                    contentDescription = ""
                )
            }

            DropdownMenu(
                modifier = Modifier.align(Alignment.BottomEnd),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.action_play)) },
                    onClick = {
                        onPlayAlbum()
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.PlayCircle,
                            contentDescription = null
                        )
                    })
            }
        }


    }
}

@Preview
@Composable
private fun PreviewDark() {
    AppTheme(darkTheme = true) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(Color.White)
        ) {
            VinylEntry(
                modifier = Modifier,
                id = "1",
                albumName = "My album",
                artist = "Artist",
                thumbnail = "",
                onVinylClick = {},
                onPlayAlbum = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    AppTheme(darkTheme = false) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(Color.White)
        ) {
            VinylEntry(
                modifier = Modifier,
                id = "1",
                albumName = "My album",
                artist = "Artist",
                thumbnail = "",
                onVinylClick = {},
                onPlayAlbum = {}
            )
        }
    }
}