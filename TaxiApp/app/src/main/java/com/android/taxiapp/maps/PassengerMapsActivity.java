package com.android.taxiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;


import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import util.Constants;

public class PassengerMapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsButtonsListener {
    HandleMapsActivity handleMapsActivity;
    DatabaseReference driversDB;
    private int searchRadius = 1;
    private boolean isDriverFound = false;
    private String nearestDriverId;

    private Button bookTaxiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_passenger_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        driversDB = FirebaseDatabase.getInstance().getReference().child("drivers");

        bookTaxiButton = findViewById(R.id.book_taxi_button);

        handleMapsActivity = new HandleMapsActivity(PassengerMapsActivity.this, Constants.PASSENGER_ROLE);

        bookTaxiButton.setOnClickListener(v -> {
            bookTaxiButton.setText("Getting your taxi...");

            gettingNearestTaxi();
        });
    }

    private void gettingNearestTaxi() {

        GeoFire geoFire = new GeoFire(driversDB);
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(
                handleMapsActivity.location.getLatitude(),
                handleMapsActivity.location.getLongitude()
        ),searchRadius);

        geoQuery.removeAllListeners();
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                if (!isDriverFound) {
                    isDriverFound = true;
                    nearestDriverId = key;
                }
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                if (!isDriverFound) {
                    searchRadius++;
                    gettingNearestTaxi();
                }
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
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