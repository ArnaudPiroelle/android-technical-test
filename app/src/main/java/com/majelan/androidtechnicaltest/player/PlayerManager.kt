package com.majelan.androidtechnicaltest.player

import android.content.ComponentName
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.majelan.androidtechnicaltest.domain.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.Single
import java.util.concurrent.Executors

@Single
class PlayerManager(context: Context) : MediaController.Listener, Player.Listener {

    private val _internalState = MutableStateFlow(State())
    val state = _internalState.asStateFlow()

    private val sessionToken = SessionToken(context, ComponentName(context, PlaybackService::class.java))
    private val controller = MediaController.Builder(context, sessionToken)
        .buildAsync()

    private val handler = Handler(Looper.getMainLooper())
    private fun checkPlaybackPosition(delayMs: Long): Boolean = handler.postDelayed({
        val mediaController = controller.get()
        val progress = mediaController.currentPosition.toFloat() / mediaController.duration
        _internalState.value = _internalState.value.copy(progress = progress, currentPosition = mediaController.currentPosition, totalDuration = mediaController.duration)
        checkPlaybackPosition(delayMs)
    }, delayMs)

    init {
        controller.addListener({
            controller.get().addListener(this)
            checkPlaybackPosition(1000)
        }, Executors.newSingleThreadExecutor())
    }


    fun playTracks(tracks: List<Track>) {
        val mediaItems = tracks.map {
            MediaItem.fromUri(it.source).buildUpon()
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setArtist(it.artist.name)
                        .setTitle(it.title)
                        .setArtworkUri(it.thumbnail.toUri())
                        .build()
                )
                .build()
        }

        with(controller.get()) {
            setMediaItems(mediaItems)
            playWhenReady = true
            prepare()
        }
    }

    fun play() {
        controller.get().play()
    }

    fun pause() {
        controller.get().pause()
    }

    fun next() {
        if (controller.get().hasNextMediaItem()) {
            controller.get().seekToNext()
        }
    }

    fun previous() {
        if (controller.get().hasPreviousMediaItem()) {
            controller.get().seekToPrevious()
        }
    }

    fun seek(progress: Float) {
        val duration = controller.get().duration
        val newPosition = duration * progress

        controller.get().seekTo(newPosition.toLong())
    }

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        _internalState.value = _internalState.value.copy(isPlaying = playWhenReady)
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if (playbackState == Player.STATE_ENDED) {
            _internalState.value = _internalState.value.copy(hasTracks = false)
        } else if (playbackState != Player.STATE_IDLE) {
            _internalState.value = _internalState.value.copy(hasTracks = true)
        }
    }

    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
        _internalState.value = _internalState.value.copy(mediaMetadata = mediaMetadata)
    }

    data class State(
        val isPlaying: Boolean = false,
        val hasTracks: Boolean = false,
        val progress: Float = 0f,
        val mediaMetadata: MediaMetadata? = null,
        val currentPosition: Long = 0,
        val totalDuration: Long = 0,
    )
}