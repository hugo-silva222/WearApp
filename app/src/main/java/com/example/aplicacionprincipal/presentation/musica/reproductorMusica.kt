package com.example.aplicacionprincipal.presentation.musica

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material3.IconButton
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import com.example.aplicacionprincipal.R

@Composable
fun MusicPlayerScreen(
    initialSongIndex: Int? = null,
    songList: List<TrackInfo>,
    onNavigateToPlaylist: (currentIndex: Int, songList: List<TrackInfo>) -> Unit,
    onBack: () -> Unit
) {
    if (songList.isEmpty()) {
        Scaffold(timeText = { TimeText() }) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay canciones disponibles.")
            }
        }
        return
    }

    val context = LocalContext.current
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    var currentIndex by remember(songList.size) {
        mutableStateOf(initialSongIndex?.takeIf { it >= 0 && it < songList.size } ?: 0)
    }
    var isPlaying by remember { mutableStateOf(true) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    val currentVolume = remember { mutableStateOf(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)) }
    val maxVolume = remember { audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) }
    val wearTypography = MaterialTheme.typography
    val wearColors = MaterialTheme.colors

    val currentTrack = remember(currentIndex, songList) {
        songList.getOrElse(currentIndex) { songList.first() }
    }
    val scrollState = rememberScrollState()

    LaunchedEffect(initialSongIndex, songList.size) {
        initialSongIndex?.let { newIndex ->
            if (newIndex >= 0 && newIndex < songList.size && newIndex != currentIndex) {
                currentIndex = newIndex
                isPlaying = true
            }
        }
    }

    LaunchedEffect(currentTrack.resourceId, currentIndex) {
        Log.d(
            "MusicPlayerScreen",
            "LaunchedEffect for MediaPlayer. Track: ${currentTrack.title}, Index: $currentIndex, isPlaying: $isPlaying"
        )
        mediaPlayer?.release()
        mediaPlayer = null

        if (currentTrack.resourceId != 0) {
            mediaPlayer = MediaPlayer.create(context, currentTrack.resourceId)?.apply {
                setOnErrorListener { _, what, extra ->
                    Log.e(
                        "MusicPlayerScreen",
                        "MediaPlayer Error: what $what, extra $extra. Track: ${currentTrack.title}"
                    )
                    isPlaying = false
                    true
                }
                setOnCompletionListener {
                    Log.d(
                        "MusicPlayerScreen",
                        "MediaPlayer OnCompletionListener: ${currentTrack.title}"
                    )
                    if (currentIndex < songList.size - 1) {
                        currentIndex++
                    } else {
                        isPlaying = false
                    }
                }
            }

            if (mediaPlayer == null) {
                Log.e(
                    "MusicPlayerScreen",
                    "MediaPlayer.create devolvió null para resourceId: ${currentTrack.resourceId}"
                )
                isPlaying = false
            } else if (isPlaying) {
                try {
                    startAndLog(mediaPlayer, currentTrack.title)
                } catch (e: IllegalStateException) {
                    Log.e(
                        "MusicPlayerScreen",
                        "MediaPlayer start error in LaunchedEffect: ${e.message}",
                        e
                    )
                    isPlaying = false
                }
            }
        } else {
            Log.w(
                "MusicPlayerScreen",
                "Pista actual con resourceId 0 (inválido): ${currentTrack.title}"
            )
            isPlaying = false
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            Log.d("MusicPlayerScreen", "DisposableEffect: Releasing MediaPlayer")
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    Scaffold(
        timeText = { TimeText(modifier = Modifier.padding(top = 6.dp)) },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = { PositionIndicator(scrollState = scrollState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(wearColors.background)
                .verticalScroll(scrollState)
                .padding(horizontal = 8.dp, vertical = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(90.dp)
                    .aspectRatio(1f)
                    .background(wearColors.surface, CircleShape)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = currentTrack.coverArtResourceId),
                    contentDescription = "Carátula de ${currentTrack.title}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = currentTrack.title,
                style = wearTypography.title3,
                color = wearColors.onBackground,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )

            Text(
                text = currentTrack.artist,
                style = wearTypography.body2,
                color = wearColors.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            IconButton(
                onClick = { onNavigateToPlaylist(currentIndex, songList) },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.play),
                    contentDescription = "Playlist",
                    modifier = Modifier.fillMaxSize(0.8f),
                    tint = wearColors.onSurface
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (songList.isNotEmpty()) {
                            val currentlyPlaying = isPlaying
                            currentIndex = (currentIndex - 1).mod(songList.size)
                            if (currentlyPlaying) isPlaying = true
                        }
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.previous),
                        contentDescription = "Anterior",
                        modifier = Modifier.fillMaxSize(0.7f),
                        tint = wearColors.onSurface
                    )
                }

                IconButton(
                    onClick = {
                        if (currentTrack.resourceId == 0) {
                            Log.w("MusicPlayerScreen", "Play/Pause: No valid track resource.")
                            return@IconButton
                        }
                        if (mediaPlayer != null) {
                            if (isPlaying) {
                                mediaPlayer?.pause()
                                Log.d("MusicPlayerScreen", "MediaPlayer paused by button")
                            } else {
                                try {
                                    startAndLog(mediaPlayer, currentTrack.title, "button")
                                } catch (e: IllegalStateException) {
                                    Log.e(
                                        "MusicPlayerScreen",
                                        "MediaPlayer start error on button: ${e.message}",
                                        e
                                    )
                                }
                            }
                        }
                        isPlaying = !isPlaying
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .background(wearColors.primary, CircleShape)
                ) {
                    Icon(
                        painter = painterResource(if (isPlaying) R.drawable.pause else R.drawable.play),
                        contentDescription = if (isPlaying) "Pausar" else "Reproducir",
                        modifier = Modifier.fillMaxSize(0.7f),
                        tint = wearColors.onPrimary
                    )
                }

                IconButton(
                    onClick = {
                        if (songList.isNotEmpty()) {
                            val currentlyPlaying = isPlaying
                            currentIndex = (currentIndex + 1) % songList.size
                            if (currentlyPlaying) isPlaying = true
                        }
                    },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.next),
                        contentDescription = "Siguiente",
                        modifier = Modifier.fillMaxSize(0.7f),
                        tint = wearColors.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Volumen",
                style = wearTypography.caption1,
                color = wearColors.onSurface.copy(alpha = 0.7f)
            )

            Slider(
                value = currentVolume.value.toFloat(),
                onValueChange = { newVolume ->
                    val newVolInt = newVolume.toInt()
                    currentVolume.value = newVolInt
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolInt, 0)
                    Log.d("MusicPlayerScreen", "Volume (Slider) changed to: $newVolInt")
                },
                valueRange = 0f..maxVolume.toFloat(),
                steps = if (maxVolume > 0) maxVolume - 1 else 0,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(36.dp),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colors.primary,
                    activeTrackColor = MaterialTheme.colors.primary,
                    inactiveTrackColor = MaterialTheme.colors.onSurface.copy(alpha = 0.24f)
                )
            )

            if (songList.size > 1) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "A continuación:",
                    style = wearTypography.caption2,
                    fontWeight = FontWeight.Bold,
                    color = wearColors.onBackground,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                val nextIndex = (currentIndex + 1) % songList.size
                val nextTrack = songList[nextIndex]
                Text(
                    text = "${nextTrack.title} - ${nextTrack.artist}",
                    style = wearTypography.caption2.copy(fontSize = 11.sp),
                    color = wearColors.onBackground.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 1.dp)
                )
            }
        }
    }
}

private fun startAndLog(
    mediaPlayer: MediaPlayer?,
    trackTitle: String,
    from: String = "LaunchedEffect"
) {
    try {
        mediaPlayer?.start()
        Log.d("MusicPlayerScreen", "MediaPlayer started for: $trackTitle (from $from)")
    } catch (e: IllegalStateException) {
        Log.e(
            "MusicPlayerScreen",
            "MediaPlayer start error in startAndLog for $trackTitle: ${e.message}",
            e
        )
        throw e
    }
}

data class TrackInfo(
    val id: String,
    @RawRes val resourceId: Int,
    val title: String,
    val artist: String,
    @DrawableRes val coverArtResourceId: Int
)

@Composable
fun PlaylistScreen(
    songList: List<TrackInfo>,
    currentPlayingIndex: Int,
    onSongSelected: (Int) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        timeText = { TimeText() },
        positionIndicator = { PositionIndicator(scalingLazyListState = rememberScalingLazyListState()) }
    ) {
        val listState = rememberScalingLazyListState()
        val wearTypography = MaterialTheme.typography
        val wearColors = MaterialTheme.colors

        ScalingLazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(wearColors.background)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    "Playlist",
                    style = wearTypography.title1,
                    textAlign = TextAlign.Center,
                    color = wearColors.onBackground,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            itemsIndexed(songList) { index, song ->
                Chip(
                    onClick = { onSongSelected(index) },
                    label = {
                        Text(
                            song.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    secondaryLabel = {
                        Text(
                            song.artist,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.musical),
                            contentDescription = null,
                            modifier = Modifier.size(ChipDefaults.IconSize)
                        )
                    },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = if (index == currentPlayingIndex) wearColors.primary else wearColors.surface,
                        contentColor = if (index == currentPlayingIndex) wearColors.onPrimary else wearColors.onSurface,
                        secondaryContentColor = (if (index == currentPlayingIndex) wearColors.onPrimary else wearColors.onSurface).copy(
                            alpha = 0.7f
                        ),
                        iconColor = if (index == currentPlayingIndex) wearColors.onPrimary else wearColors.onSurface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                )
            }

            item {
                Button(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = wearColors.primary,
                        contentColor = wearColors.onPrimary
                    )
                ) {
                    Text("Volver al Reproductor")
                }
            }
        }
    }
}

