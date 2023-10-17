package com.majelan.androidtechnicaltest.ui.scenes.album.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.majelan.androidtechnicaltest.ui.theme.AppTheme

@Composable
internal fun AlbumHeader(
    modifier: Modifier,
    title: String,
    artist: String,
    thumbnail: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ElevatedCard(
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(.2f), RoundedCornerShape(8.dp)),
                model = thumbnail,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title.uppercase(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = artist.uppercase(),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f)
            )
        }

    }
}

@Preview
@Composable
private fun PreviewDark() {
    AppTheme(darkTheme = true) {
        Surface {
            AlbumHeader(
                modifier = Modifier,
                title = "Title",
                artist = "Artist",
                thumbnail = ""
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    AppTheme(darkTheme = false) {
        Surface {
            AlbumHeader(
                modifier = Modifier,
                title = "Title",
                artist = "Artist",
                thumbnail = ""
            )
        }
    }
}