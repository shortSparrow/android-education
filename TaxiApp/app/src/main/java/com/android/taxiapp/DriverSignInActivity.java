package com.android.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

public class DriverSignInActivity extends AppCompatActivity implements LoginSignInListener {
    HandleSignIn handleSignIn;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_in);

        handleSignIn = new HandleSignIn(this);
        handleSignIn.signUpButton.setBackgroundTintList(DriverSignInActivity.this.getResources().getColorStateList(R.color.yellow));
    }

    @Override
    public void signUpButtonOnClick(View v) {
        if (handleSignIn.validateAllFields()) {
            handleSignIn.createUser("driver");
        }
    }

    @Override
    public void loginButtonOnClick(View v) {
        handleSignIn.loginUser("driver");
    }

    @Override
    public void navigateToLoginButtonOnClick(View v) {
        handleSignIn.toggleMode();
    }
}