package com.android.magic8ball

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    var askList = listOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val askMeButton: Button = findViewById(R.id.askButton);
        askList = listOf(
            "It is certain", "It is decidedly so", "Without a doubt",
            "Yes - definitely", "You may rely on it", "As I see it, yes",
            "Most likely", "Outlook good", "Yes", "Signs point to yes",
            "Reply hazy, try again", "Ask again later", "Better not tell you now",
            "Cannot predict now", "Concentrate and ask again", "Don't count on it",
            "My reply is no", "My source say no", "Outlook noe so good",
            "Very doubtful"
        )

        askMeButton.setOnClickListener { shakeBall() }
    }

    private fun shakeBall() {
        val randomChoice = Random().nextInt(askList.size)
        val textAnswer:TextView = findViewById(R.id.answerText)
        textAnswer.text = askList[randomChoice]
    }
}