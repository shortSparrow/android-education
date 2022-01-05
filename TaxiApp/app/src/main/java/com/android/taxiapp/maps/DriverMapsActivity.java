package com.android.taxiapp.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.taxiapp.HandleMapsActivity;
import com.android.taxiapp.MapsButtonsListener;
import com.android.taxiapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import util.Constants;

public class DriverMapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsButtonsListener {
    HandleMapsActivity handleMapsActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_driver_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        handleMapsActivity = new HandleMapsActivity(DriverMapsActivity.this, Constants.DRIVER_ROLE);

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        handleMapsActivity.onMapReady(googleMap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleMapsActivity.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleMapsActivity.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handleMapsActivity.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        handleMapsActivity.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void signOutButtonOnClick(View v) {

    }

    @Override
    public void settingsButtonOnClick(View v) {

    }
}