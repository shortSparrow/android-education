package com.example.servicemusic

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.servicemusic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var musicService: MusicService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setListeners()
    }

    private fun setListeners() = with(binding) {
        play.setOnClickListener { startMusicService() }
        resume.setOnClickListener { musicService?.resume() }
        pause.setOnClickListener { musicService?.pauseMusic() }
        stop.setOnClickListener { stopMusicService() }
    }

    private fun stopMusicService() {
        stopService(MusicService.newIntent(applicationContext))
    }

    private fun startMusicService() {
        ContextCompat.startForegroundService(
            this,
            MusicService.newIntent(this)
        )
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = (service as? MusicService.LocalBinder) ?: return
            musicService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(
            MusicService.newIntent(this),
            serviceConnection,
            0
        )
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
    }
}