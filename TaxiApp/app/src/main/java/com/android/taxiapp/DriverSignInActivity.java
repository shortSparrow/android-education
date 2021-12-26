package com.android.taxiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DriverSignInActivity extends AppCompatActivity implements LoginSignInListener {
    HandleSignUp handleSignUp;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_in);

        handleSignUp = new HandleSignUp(this);
        handleSignUp.signUpButton.setBackgroundTintList(DriverSignInActivity.this.getResources().getColorStateList(R.color.yellow));
    }

    @Override
    public void signUpButtonOnClick(View v) {
        if (handleSignUp.validateAllFields()) {
            Toast.makeText(this, "YES", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void navigateToLoginButtonOnClick(View v) {
        handleSignUp.toggleMode();
    }
}