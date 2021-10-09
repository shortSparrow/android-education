package com.example.baseaudiio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    TextView volume;
    float volume_level = 0.5f;
    SeekBar seekBar;
    int trackDuration;
    boolean seekBarTouching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // В качестве context можем исользовать this - ссылка на наш activity, или context всего приложения getApplicationContext
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.jerry_heil);

        trackDuration = mediaPlayer.getDuration();
        volume = findViewById(R.id.volume);
        seekBar = findViewById(R.id.seekBar);

        Timer timerId = new Timer();

        timerId.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!seekBarTouching && mediaPlayer.isPlaying()) {
                    updateSeekPlayer();
                }
            }
        }, 0, 100);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                seekBarTouching = false;
                int seekTo = trackDuration * seekBar.getProgress() / 100;
                mediaPlayer.seekTo(seekTo);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                seekBarTouching = true;
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void updateSeekPlayer() {
        int percent = (100 * mediaPlayer.getCurrentPosition()) / trackDuration;
        seekBar.setProgress(percent);
    }

    public void playMusic(View view) {
        setVolume();
        mediaPlayer.start();
    }

    public void pauseMusic(View view) {
        mediaPlayer.pause();
    }

    public void increaseVolume(View view) {
        // Оставляем только одну цифру после запятой, ибо 0.9 + 0.1 = 1.00001. Это не дает зайти в if
        double volume_level_to_fixed = Math.floor((volume_level + 0.1f) * 10) / 10;

        if (volume_level_to_fixed <= 1.0) {
            volume_level += 0.1f;
            setVolume();
        }
    }

    public void decreaseVolume(View view) {
        if (volume_level - 0.1f >= 0.0) {
            volume_level -= 0.1f;
            setVolume();
        }
    }

    public void setVolume() {
        // Оставляем только одну цифру после запятой
        double showVolume = Math.floor(volume_level * 10) / 10;
        volume.setText(String.valueOf(showVolume));
        mediaPlayer.setVolume(volume_level, volume_level);
    }
}