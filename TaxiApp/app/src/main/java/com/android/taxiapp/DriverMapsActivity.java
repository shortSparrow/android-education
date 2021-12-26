package com.android.taxiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.android.taxiapp.databinding.ActivityDriverMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.Date;

public class DriverMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int CHECK_SETTING_CODE = 111;
    private static final int REQUEST_LOCATION_PERMISSION = 222;

    private GoogleMap mMap;
    private ActivityDriverMapsBinding binding;

    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private Location location;

    private Boolean isLocationUpdatesActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDriverMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);
        buildLocationRequest();
        buildLocationCallback();
        buildLocationSettingRequest();

        startLocationUpdates();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // Add a marker in Sydney and move the camera
//        LatLng driverLocation = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(driverLocation).title("Driver location"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(driverLocation));

        if (location != null) {
            LatLng driverLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(driverLocation).title("Driver location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(driverLocation));
        }
    }

    private void stopLocationUpdates() {
        if (!isLocationUpdatesActive) return;

        fusedLocationClient.removeLocationUpdates(locationCallback).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                isLocationUpdatesActive = false;
            }
        });
    }

    private void startLocationUpdates() {
        isLocationUpdatesActive = true;

        settingsClient.checkLocationSettings(locationSettingsRequest).addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                if (ActivityCompat.checkSelfPermission(DriverMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DriverMapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                updateLocationUI();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();


                switch (statusCode) {
                    case LocationSettingsStatusCodes
                            .RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult(DriverMapsActivity.this, CHECK_SETTING_CODE);
                        } catch (IntentSender.SendIntentException sendIntentException) {
                            sendIntentException.printStackTrace();
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        String message = "adjust location settings on your device";
                        Toast.makeText(DriverMapsActivity.this, message, Toast.LENGTH_LONG).show();

                        isLocationUpdatesActive = false;
                }

                updateLocationUI();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHECK_SETTING_CODE:
                switch (resultCode) {
                    case Activity
                            .RESULT_OK:
                        Log.d("MAinActivity", "user has agreed to change location settings");
                        startLocationUpdates();
                        break;

                    case Activity
                            .RESULT_CANCELED:
                        Log.d("MAinActivity", "user has NOT agreed to change location settings");
                        isLocationUpdatesActive = false;
                        updateLocationUI();
                        break;
                }
        }
    }

    private void buildLocationSettingRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();
    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                location = locationResult.getLastLocation();
                updateLocationUI();
            }
        };
    }

    private void updateLocationUI() {
        if (location != null) {
            LatLng driverLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(driverLocation).title("Driver location"));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(driverLocation));
        }

    }

    private void buildLocationRequest() {

        locationRequest = LocationRequest.create()
                .setInterval(10000)
                .setFastestInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onResume() {
        Log.d("XXX", "XXX" + isLocationUpdatesActive + checkLocationPermissions());
        super.onResume();
        if (isLocationUpdatesActive && checkLocationPermissions()) {
            startLocationUpdates();
        } else if (!checkLocationPermissions()) {
            requestLocationPermission();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void requestLocationPermission() {
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            showSnackBar("Location permission is needed fpr app functionality", "OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(DriverMapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                }
            });
        } else {
            ActivityCompat.requestPermissions(DriverMapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    private void showSnackBar(final String mainText, final String action, View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                mainText,
                Snackbar.LENGTH_INDEFINITE
        ).setAction(
                action,
                listener
        ).show();
    }

    private boolean checkLocationPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length <= 0) {
                Log.d("PermissionResult", "Request was canceled");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isLocationUpdatesActive) {
                    startLocationUpdates();
                }
            } else {
                showSnackBar(
                        "Turn on location in settings",
                        "Settings",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

                                Uri uri = Uri.fromParts(
                                        "package",
                                        "com.android.uraall.locationapi",
                                        null
                                );
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                );
            }
        }
    }
}