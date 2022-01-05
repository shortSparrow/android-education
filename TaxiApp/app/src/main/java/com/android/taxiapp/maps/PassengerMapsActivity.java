package com.android.taxiapp.maps;

import static util.Constants.DRIVER_DB_NAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;


import android.content.Intent;

import android.location.Location;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.android.taxiapp.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

import util.Constants;

public class PassengerMapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsButtonsListener {
    HandleMapsActivity handleMapsActivity;
    DatabaseReference driversDB;
    private int searchRadius = 1;
    private boolean isDriverFound = false;
    private String nearestDriverId;
    private Marker driverMarker;

    private Button bookTaxiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_passenger_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        driversDB = FirebaseDatabase.getInstance().getReference().child(DRIVER_DB_NAME);

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
                    gettingNearestDriverLocation();
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

    private void gettingNearestDriverLocation() {
        bookTaxiButton.setText("Getting your driver location...");
        DatabaseReference yourDriverLocation = driversDB.child(nearestDriverId).child("l");

        yourDriverLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Object> driverLocationParameters = (List<Object>) snapshot.getValue();

                    double latitude = 0;
                    double longitude = 0;

                    if (driverLocationParameters.get(0) != null) {
                        latitude = Double.parseDouble(driverLocationParameters.get(0).toString());
                    }

                    if (driverLocationParameters.get(1) != null) {
                        longitude = Double.parseDouble(driverLocationParameters.get(1).toString());
                    }

                    LatLng driverLatLng = new LatLng(latitude, longitude);

                    if (driverMarker != null) {
                        driverMarker.remove();
                    }

                    Location driverLocation = new Location("");
                    driverLocation.setLatitude(latitude);
                    driverLocation.setLongitude(longitude);

                    float distanceToDriver = driverLocation.distanceTo(handleMapsActivity.location);

                    bookTaxiButton.setText("Distance to driver is " + distanceToDriver + "m");

                    driverMarker = handleMapsActivity.mMap.addMarker(
                            new MarkerOptions().position(driverLatLng).title("Your driver")
                    );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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