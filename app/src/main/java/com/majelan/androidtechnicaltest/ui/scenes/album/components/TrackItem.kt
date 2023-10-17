package com.majelan.androidtechnicaltest.ui.scenes.album.components

import android.text.format.DateUtils
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.majelan.androidtechnicaltest.ui.theme.AppTheme
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


@Composable
internal fun TrackItem(
    modifier: Modifier,
    thumbnail: String,
    name: String,
    duration: Duration,
    number: Int,
    total: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 4.dp),
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

        Column {
            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.weight(1f))
            Row {
                Text(
                    text = "$number/$total",
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = DateUtils.formatElapsedTime(duration.inWholeSeconds),
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f)
                )
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
                .height(70.dp)
        ) {
            TrackItem(
                modifier = Modifier,
                thumbnail = "https://storage.googleapis.com/automotive-media/album_art.jpg",
                name = "The Messenger",
                duration = 132.toDuration(DurationUnit.SECONDS),
                number = 2,
                total = 6,
                onClick = {}
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
                .height(70.dp)
        ) {
            TrackItem(
                modifier = Modifier,
                thumbnail = "https://storage.googleapis.com/automotive-media/album_art.jpg",
                name = "The Messenger",
                duration = 132.toDuration(DurationUnit.SECONDS),
                number = 2,
                total = 6,
                onClick = {}
            )
        }
    }
}