package com.majelan.androidtechnicaltest.ui.scenes.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.majelan.androidtechnicaltest.ui.theme.AppTheme

@Composable
internal fun AnimatedVinylCoverWithDisc(modifier: Modifier, thumbnail: String) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
    ) {
        VinylCover(
            modifier = Modifier.align(Alignment.CenterStart),
            thumbnail = thumbnail
        )
    }
}

@Composable
internal fun VinylCover(
    modifier: Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    thumbnail: String
) {
    AsyncImage(
        modifier = modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .clip(shape)
            .background(Color.LightGray)
            .border(.5.dp, Color.Black.copy(.2f), shape),
        model = thumbnail,
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

@Preview
@Composable
private fun PreviewDark() {
    AppTheme(darkTheme = true) {
        Surface {
            AnimatedVinylCoverWithDisc(
                modifier = Modifier,
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
            AnimatedVinylCoverWithDisc(
                modifier = Modifier,
                thumbnail = ""
            )
        }
    }
}