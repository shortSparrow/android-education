package com.android.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class PassengerSignInActivity extends AppCompatActivity implements LoginSignInListener {
    HandleSignIn handleSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_sign_in);

        handleSignIn = new HandleSignIn(this);
        handleSignIn.signUpButton.setBackgroundTintList(PassengerSignInActivity.this.getResources().getColorStateList(R.color.green));
    }

    @Override
    public void signUpButtonOnClick(View v) {
        handleSignIn.validateAllFields();
    }

    @Override
    public void loginButtonOnClick(View v) {

    }

    @Override
    public void navigateToLoginButtonOnClick(View v) {
        handleSignIn.toggleMode();
    }
}