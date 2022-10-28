package com.example.servicemusic

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MusicService : Service() {
    private lateinit var player: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())

        player = MediaPlayer.create(this, R.raw.color_out_alone)
        player.setVolume(100f, 100f)
    }

    fun pauseMusic() {
        player.pause()
    }

    fun resume() {
        player.start()
    }

    private fun playMusic() {
        player.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        playMusic()
        log("onStartCommand")
        return START_STICKY
    }

    private fun createNotification(): Notification {
        // При тапі на повідомлення відкриваємо нашу апку
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Playing")
            .setContentText("Color Out: Alone")
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onBind(p0: Intent?): IBinder {
        return LocalBinder()
    }

    inner class LocalBinder : Binder() {
        fun getService() = this@MusicService
    }

    private fun log(message: String) {
        Log.d("MusicService", "Message: $message")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        player.stop()
    }

    companion object {
        private const val CHANNEL_ID = "1"
        private const val CHANNEL_NAME = "music_player"
        private const val NOTIFICATION_ID = 1

        fun newIntent(context: Context): Intent {
            return Intent(context, MusicService::class.java)
        }
    }

}