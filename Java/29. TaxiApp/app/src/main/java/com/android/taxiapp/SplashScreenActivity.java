package com.android.taxiapp;

import static util.Constants.DRIVER_ROLE;
import static util.Constants.PASSENGER_ROLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.taxiapp.auth.ChooseModeActivity;
import com.android.taxiapp.maps.DriverMapsActivity;
import com.android.taxiapp.maps.PassengerMapsActivity;
import com.android.taxiapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreenActivity";
    FirebaseDatabase database;
    DatabaseReference usersDB;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersDB = database.getReference("users");

        FirebaseUser currentUser= mAuth.getCurrentUser();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (currentUser != null) {
                        ValueEventListener postListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);

                                if (user.getRole().equals(DRIVER_ROLE)) {
                                    startActivity(new Intent(SplashScreenActivity.this, DriverMapsActivity.class));
                                }

                                if (user.getRole().equals(PASSENGER_ROLE)) {
                                    startActivity(new Intent(SplashScreenActivity.this, PassengerMapsActivity.class));
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                            }
                        };
                        usersDB.child(currentUser.getUid()).addValueEventListener(postListener);
                    } else {
                        startActivity(new Intent(SplashScreenActivity.this, ChooseModeActivity.class));
                    }
                }
            }
        };

        thread.start();
    }


    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }
}