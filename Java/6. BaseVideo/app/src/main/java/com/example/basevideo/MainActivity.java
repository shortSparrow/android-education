package com.example.basevideo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.videoView);
    }

    public void setLocalVideo(View view) {
//        android.resource:// - пространство имен
//        getPackageName() - название нашего пакета ("com.example.basevideo")
//        videoView.setVideoURI(Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4?"));

        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.lemon);
        startPlayVideo();
    }

    public void setUriVideo(View view) {
        videoView.setVideoPath("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4?");
        startPlayVideo();
    }

    public void startPlayVideo() {
        videoView.setAlpha(1);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }
}