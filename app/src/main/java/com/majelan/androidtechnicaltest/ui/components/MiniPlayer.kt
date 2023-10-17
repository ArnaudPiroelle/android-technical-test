package com.majelan.androidtechnicaltest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.majelan.androidtechnicaltest.ui.theme.AppTheme


@Composable
fun MiniPlayer(
    progress: Float,
    title: String,
    artist: String,
    thumbnail: String?,
    isPlaying: Boolean,
    onPlayPause: (play: Boolean) -> Unit,
    onOpenPlayer: () -> Unit
) {
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { onOpenPlayer() }
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .padding(bottom = 4.dp, end = 48.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(.1f), RoundedCornerShape(8.dp)),
                model = thumbnail,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = artist,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = { onPlayPause(!isPlaying) }
        ) {
            if (isPlaying) {
                Icon(imageVector = Icons.Default.Pause, contentDescription = null)
            } else {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(4.dp)
                .background(Color.Red)
                .align(Alignment.BottomStart)
        )
    }
}

@Preview
@Composable
private fun PreviewDarkMiniPlayer(){
    AppTheme(darkTheme = true) {
        Surface {
            MiniPlayer(
                progress = .5f,
                title = "Title",
                artist = "Artist",
                thumbnail = "thumbnail",
                isPlaying = false,
                onPlayPause = {}
            ) {

            }
        }
    }
}

@Preview
@Composable
private fun PreviewLightMiniPlayer(){
    AppTheme(darkTheme = false) {
        Surface {
            MiniPlayer(
                progress = .5f,
                title = "Title",
                artist = "Artist",
                thumbnail = "thumbnail",
                isPlaying = false,
                onPlayPause = {}
            ) {

            }
        }
    }
}