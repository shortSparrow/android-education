package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView songTitle;
    ImageView playPauseImage;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;

    Map[] trackList = {
            new HashMap() {{
                put("file", R.raw.love_of_my_life);
                put("title", "love of my life");
            }},
            new HashMap() {{
                put("file", R.raw.festival_paketov);
                put("title", "Фестиваль пакетов");
            }},

            new HashMap() {{
                put("file", R.raw.jessy_and_jayn);
                put("title", "Джесси и Джейн");
            }},
            new HashMap() {{
                put("file", R.raw.sov_govnarya);
                put("title", "Сон говноря");
            }},
            new HashMap() {{
                put("file", R.raw.tem_kro_slishit_i_chuvstvuet);
                put("title", "Тем кто слышит и чувствует");
            }}
    };
    int currentPlayIndex = 0;
    boolean startSeek = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        songTitle = findViewById(R.id.song_title);
        playPauseImage = findViewById(R.id.play_pause);
        seekBar = findViewById(R.id.seekBar);

        mediaPlayer = MediaPlayer.create(this, (Integer) trackList[currentPlayIndex].get("file"));
        songTitle.setText((String) trackList[currentPlayIndex].get("title"));
        seekBar.setMax(mediaPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!startSeek && mediaPlayer.isPlaying()) {
                    int progressTrack = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(progressTrack);
                }
            }
        }, 0, 600);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                startSeek = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                startSeek = false;
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    public void previousTrack(View view) {
        currentPlayIndex--;
        if (currentPlayIndex < 0) {
            currentPlayIndex = trackList.length - 1;
        }
        selectTrack();
    }

    public void nextTrack(View view) {
        currentPlayIndex++;
        if (currentPlayIndex >= trackList.length) {
            currentPlayIndex = 0;
        }
        selectTrack();
    }

    public void handlePlayPauseTrack(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playPauseImage.setImageResource(R.drawable.play_arrow_24);
        } else {
            mediaPlayer.start();
            playPauseImage.setImageResource(R.drawable.pause);
        }
    }

    public void selectTrack() {
        if (mediaPlayer.isPlaying()) {
            changeTrack();
            mediaPlayer.start();
        } else {
            changeTrack();
        }
    }

    public void changeTrack() {
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(this, (Integer) trackList[currentPlayIndex].get("file"));
        seekBar.setMax(mediaPlayer.getDuration());
        songTitle.setText((String) trackList[currentPlayIndex].get("title"));
    }
}