package com.android.taxiapp.auth;

import static util.Constants.DRIVER_ROLE;
import static util.Constants.PASSENGER_ROLE;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.taxiapp.R;
import com.android.taxiapp.maps.DriverMapsActivity;
import com.android.taxiapp.maps.PassengerMapsActivity;
import com.android.taxiapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HandleAuthUser {
    private String TAG = "HandleAuthUser";
    private Activity activity;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference usersDB;

    private TextView authTextError;

    public HandleAuthUser(Activity activity) {
        this.activity = activity;

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersDB = database.getReference("users");

        authTextError = activity.findViewById(R.id.auth_message_error);
    }


    public void createUser(User user, String password) {

        mAuth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            authTextError.setVisibility(TextView.INVISIBLE);

                            Toast.makeText(activity, "Authentication successfully.",
                                    Toast.LENGTH_SHORT).show();

                            usersDB.child(currentUser.getUid()).setValue(user);

                            if (user.getRole() == DRIVER_ROLE) {
                                activity.startActivity(new Intent(activity, DriverMapsActivity.class));
                            }

                            if (user.getRole() == PASSENGER_ROLE) {
                                activity.startActivity(new Intent(activity, PassengerMapsActivity.class));
                            }

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            authTextError.setVisibility(TextView.VISIBLE);
                            authTextError.setText(task.getException().getMessage());
                        }
                    }
                });
    }


    public void loginUser(User user, String password) {
        mAuth.signInWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            Toast.makeText(activity, "User successfully logged.",
                                    Toast.LENGTH_SHORT).show();

                            if (user.getRole() == DRIVER_ROLE) {
                                activity.startActivity(new Intent(activity, DriverMapsActivity.class));
                            }

                            if (user.getRole() == PASSENGER_ROLE) {
                                activity.startActivity(new Intent(activity, PassengerMapsActivity.class));
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
