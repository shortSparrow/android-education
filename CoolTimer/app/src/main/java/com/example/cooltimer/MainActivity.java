package com.example.cooltimer;

import static java.lang.System.err;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    SeekBar seekBar;
    TextView textView;
    Button startButton;
    int timerStartFrom;
    CountDownTimer myTimer;
    boolean isTimerPlay = false;
    MediaPlayer mediaPlayer;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        seekBar = findViewById(R.id.seekbar);
        textView = findViewById(R.id.textView);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        seekBar.setMax(60000 * 2); // 2 hours

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(convertNumberInTime(progress/1000));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                timerStartFrom = seekBar.getProgress();
            }
        });

        setDefaultTimerStartFrom(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTimerPlay) {

                    startButton.setText("Stop");
                    seekBar.setEnabled(false);
                    createTimer();
                    myTimer.start();
                } else {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                    }
                    myTimer.cancel();
                    startButton.setText("Start");
                    seekBar.setEnabled(true);
                    setDefaultTimerStartFrom(sharedPreferences);
                }

                isTimerPlay = !isTimerPlay;
            }
        });
    }

    public String convertNumberInTime(int value) {
        int min = value / 60;
        int sec = value - min*60;

        String minStr = String.valueOf(min);
        String secStr =  String.valueOf(sec);

        if (min <= 9 ) {
            minStr = "0" + min;
        }

        if (sec <= 9 ) {
            secStr = "0" + sec;
        }

        return minStr + ":" + secStr;
    }

    public void createTimer() {
        myTimer = new CountDownTimer(timerStartFrom, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(convertNumberInTime((int) millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if (sharedPreferences.getBoolean("enable_sound", true)) {
                    String melodyName = sharedPreferences.getString("timer_melody", "ti_v_poradke");
                    int melody = 0;
                    if (melodyName.equals("bell")) {
                        melody = R.raw.bell;
                    } else if (melodyName.equals("ti_v_poradke")) {
                        melody = R.raw.ti_v_poradke;
                    } else if (melodyName.equals("hurdbass")) {
                        melody = R.raw.hurdbass;
                    }

                    mediaPlayer = MediaPlayer.create(getApplicationContext(), melody);
                    mediaPlayer.start();
                }
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.timer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent openSettings = new Intent(this, SettingsActivity.class);
            startActivity(openSettings);

            return true;
        } else if (id == R.id.action_about) {
            Intent openSettings = new Intent(this, AboutActivity.class);
            startActivity(openSettings);

            return true;
        } else if (id == R.id.action_purchase) {
            Intent openSettings = new Intent(this, PurchaseActivity.class);
            startActivity(openSettings);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void  setDefaultTimerStartFrom(SharedPreferences sharedPreferences) {
        // тут try...catch не обязателен, так как мы делаем проверку в SettingsFragment. Я оставил его для примера того что cath может быть несколько и что более абсрактный должен быть внизу.
        try {
            timerStartFrom = Integer.parseInt(sharedPreferences.getString("timerStartFrom", "10")) * 1000;
            textView.setText(convertNumberInTime(timerStartFrom/1000));
            seekBar.setProgress(timerStartFrom);
        } catch (NumberFormatException err) {
            Toast.makeText(this, "Value should be a number", Toast.LENGTH_SHORT).show();
        } catch (Exception err) {
            Toast.makeText(this, String.valueOf(err), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("timerStartFrom")) {
            setDefaultTimerStartFrom(sharedPreferences);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}