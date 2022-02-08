package com.android.taxiapp.maps;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.android.taxiapp.auth.ChooseModeActivity;
import com.android.taxiapp.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import util.Constants;

interface MapsButtonsListener {
    public void signOutButtonOnClick(View v);
    public void settingsButtonOnClick(View v);
}


public class HandleMapsActivity {

    private static final int CHECK_SETTING_CODE = 111;
    private static final int REQUEST_LOCATION_PERMISSION = 222;
    private static String TAG = "HandleMapsActivity";

    public GoogleMap mMap;

    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    public Location location;

    private Boolean isLocationUpdatesActive = false;

    Button signOutButton, settingsButton;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference coordinateDB;
    FirebaseUser currentUser;

    Activity activity;


    public HandleMapsActivity(Activity activity, String role) {
        this.activity = activity;
        TAG = Constants.getTAGByRole(role);
        
        signOutButton = activity.findViewById(R.id.sign_out_button);
        settingsButton = activity.findViewById(R.id.settings_button);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        coordinateDB = database.getReference(Constants.getBDNameByRole(role));
        currentUser = mAuth.getCurrentUser();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        settingsClient = LocationServices.getSettingsClient(activity);
        buildLocationRequest();
        buildLocationCallback();
        buildLocationSettingRequest();

        startLocationUpdates();

        MapsButtonsListener myListener = (MapsButtonsListener) activity;

        settingsButton.setOnClickListener(v -> myListener.signOutButtonOnClick(v));

        signOutButton.setOnClickListener(v -> {
            myListener.settingsButtonOnClick(v);
            stopLocationUpdates();
            mAuth.signOut();
            signOutClient();
            Intent intent = new Intent(activity, ChooseModeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
            activity.finish();
        });
    }

    private void signOutClient() {
        GeoFire geoFire = new GeoFire(coordinateDB);
        geoFire.removeLocation(currentUser.getUid());
    }

    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (location != null) {
            LatLng clientLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(clientLocation).title("Driver or passenger location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(clientLocation));
        } else {
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }

    private void stopLocationUpdates() {
        if (!isLocationUpdatesActive) return;

        fusedLocationClient.removeLocationUpdates(locationCallback).addOnCompleteListener(activity, task -> isLocationUpdatesActive = false);
    }

    private void startLocationUpdates() {
        isLocationUpdatesActive = true;

        settingsClient.checkLocationSettings(locationSettingsRequest).addOnSuccessListener(activity, locationSettingsResponse -> {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        }).addOnFailureListener(e -> {
            int statusCode = ((ApiException) e).getStatusCode();


            switch (statusCode) {
                case LocationSettingsStatusCodes
                        .RESOLUTION_REQUIRED:
                    try {
                        ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                        resolvableApiException.startResolutionForResult(activity, CHECK_SETTING_CODE);
                    } catch (IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();
                    }
                    break;

                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    String message = "adjust location settings on your device";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();

                    isLocationUpdatesActive = false;
            }

            updateLocationUI();

        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CHECK_SETTING_CODE) {
            switch (resultCode) {
                case Activity
                        .RESULT_OK:
                    Log.d(TAG, "user has agreed to change location settings");
                    startLocationUpdates();
                    break;

                case Activity
                        .RESULT_CANCELED:
                    Log.d(TAG, "user has NOT agreed to change location settings");
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
        Log.d(TAG, "updateLocationUI");
        if (location != null) {
            LatLng clientLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(clientLocation).title("Driver or passenger location"));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(clientLocation));

            GeoFire geoFire = new GeoFire(coordinateDB);

            geoFire.setLocation(currentUser.getUid(), new GeoLocation(location.getLatitude(), location.getLongitude()), (key, error) -> {
                if (error != null) {
                    Log.d(TAG, "Error!!! There was an error saving the location to GeoFire: " + error.getMessage());
                } else {
                    System.out.println("Location saved on server successfully!");
                    Log.d(TAG, "Location saved on server successfully!");
                }
            });
        }

    }

    private void buildLocationRequest() {
        locationRequest = LocationRequest.create()
                .setInterval(10000)
                .setFastestInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void onResume() {
        if (isLocationUpdatesActive && checkLocationPermissions()) {
            startLocationUpdates();
        } else if (!checkLocationPermissions()) {
            requestLocationPermission();
        }
    }

    protected void onPause() {
        stopLocationUpdates();
    }

    private void requestLocationPermission() {
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            showSnackBar("Location permission is needed fpr app functionality", "OK", v -> ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION));
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    private void showSnackBar(final String mainText, final String action, View.OnClickListener listener) {
        Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainText,
                Snackbar.LENGTH_INDEFINITE
        ).setAction(
                action,
                listener
        ).show();
    }

    private boolean checkLocationPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
                        v -> {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

                            Uri uri = Uri.fromParts(
                                    "package",
                                    "com.android.taxiapp",
                                    null
                            );
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);
                        }
                );
            }
        }
    }
}
