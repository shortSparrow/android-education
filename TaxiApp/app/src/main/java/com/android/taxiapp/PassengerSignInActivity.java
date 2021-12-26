package com.android.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

public class PassengerSignInActivity extends AppCompatActivity implements LoginSignInListener {
    HandleSignUp handleSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_sign_in);

        handleSignUp = new HandleSignUp(this);
        handleSignUp.signUpButton.setBackgroundTintList(PassengerSignInActivity.this.getResources().getColorStateList(R.color.green));
    }

    @Override
    public void signUpButtonOnClick(View v) {
        handleSignUp.validateAllFields();
    }

    @Override
    public void navigateToLoginButtonOnClick(View v) {
        handleSignUp.toggleMode();
    }
}