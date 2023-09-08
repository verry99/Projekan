package com.test.test.presentation.dashboard.gallery

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.SimpleExoPlayer

class AppLifecycleObserver(private val player: SimpleExoPlayer) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        // Stop playback when the app goes into the background
        player.stop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        // Resume playback when the app comes into the foreground
        player.play()
    }
}