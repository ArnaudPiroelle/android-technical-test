package com.majelan.androidtechnicaltest.ui.scenes.player.components

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.majelan.androidtechnicaltest.ui.theme.AppTheme
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
internal fun PlayerControls(
    modifier: Modifier,
    isPlaying: Boolean,
    progress: Float,
    totalDuration: Long,
    currentPosition: Long,
    onPlayPause: (play: Boolean) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onSeek: (progress: Float) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var sliderPosition by remember(progress) { mutableFloatStateOf(progress) }
        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.onSurface,
                activeTrackColor = MaterialTheme.colorScheme.onSurface
            ),
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            onValueChangeFinished = { onSeek(sliderPosition) }
        )
        Row(modifier = Modifier.padding(horizontal = 32.dp)) {
            Text(
                text = DateUtils.formatElapsedTime(currentPosition.toDuration(DurationUnit.MILLISECONDS).inWholeSeconds)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = DateUtils.formatElapsedTime(totalDuration.toDuration(DurationUnit.MILLISECONDS).inWholeSeconds)
            )
        }

        Row(modifier = Modifier.height(72.dp)) {
            Icon(
                modifier = Modifier
                    .size(72.dp)
                    .clickable { onPrevious() },
                imageVector = Icons.Default.SkipPrevious,
                contentDescription = null
            )
            val playPauseIcon = if (isPlaying) {
                Icons.Default.Pause
            } else {
                Icons.Default.PlayArrow
            }
            Icon(
                modifier = Modifier
                    .size(72.dp)
                    .clickable { onPlayPause(!isPlaying) },
                imageVector = playPauseIcon,
                contentDescription = null
            )
            Icon(
                modifier = Modifier
                    .size(72.dp)
                    .clickable { onNext() },
                imageVector = Icons.Default.SkipNext,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDark() {
    AppTheme(darkTheme = true) {
        Surface {
            PlayerControls(
                modifier = Modifier,
                isPlaying = false,
                progress = .5f,
                totalDuration = 60,
                currentPosition = 30,
                onSeek = {},
                onPrevious = {},
                onNext = {},
                onPlayPause = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLight() {
    AppTheme(darkTheme = false) {
        Surface {
            PlayerControls(
                modifier = Modifier,
                isPlaying = false,
                progress = .5f,
                totalDuration = 60,
                currentPosition = 30,
                onSeek = {},
                onPrevious = {},
                onNext = {},
                onPlayPause = {}
            )
        }
    }
}