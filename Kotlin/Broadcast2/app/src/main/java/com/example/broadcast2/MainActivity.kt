package com.example.broadcast2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    /**
     * Коли нам треба в змінювати layout в залежності від даних з BroadcastReceiver, ми
     * створюємо анонімний клас, там де будемо змунювати layout
     */
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "loaded") {
                val percent = intent.getIntExtra("percent", 0)
                progressBar.progress = percent
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)

        val intentFiler = IntentFilter().apply {
            addAction("loaded")
        }
        registerReceiver(receiver, intentFiler)

    }

    // Якщо ми підпислися на Receiver, то ми маємо і відписатися від нього, щоб не було відтоку пам'яті
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}