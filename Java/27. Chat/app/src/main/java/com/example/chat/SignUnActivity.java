package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import utils.Validate;

public class SignUnActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";
    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText userNameEditText;
    private Button sighInButton;
    private TextView signUpNavigateButton;

    FirebaseDatabase database;
    DatabaseReference userDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        database = FirebaseDatabase.getInstance();
        userDatabaseReference = database.getReference("users");

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        userNameEditText = findViewById(R.id.username_edit_text);
        sighInButton = findViewById(R.id.sign_in_button);
        signUpNavigateButton = findViewById(R.id.login_navigate_button);

        sighInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }

    }

    private void reload() {
    }

    public void showToast(String message) {
        Toast.makeText(SignUnActivity.this, message,
                Toast.LENGTH_SHORT).show();
    }

    public boolean isValidFields() {
        String email = emailEditText.getText().toString().trim();
        if (!Validate.isValidEmail(email)) {
            showToast("Email is invalid");
            return false;
        }

        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        if (password.length() < 6) {
            showToast("password mush contains at least 6 characters");
            return false;
        }

        if (!confirmPassword.equals(password)) {
            showToast("password don't match");
            return false;
        }

        String username = userNameEditText.getText().toString().trim();
        if (username.length() == 0) {
            showToast("name is required field");
            return false;
        }

        return true;
    }


    private void signUpUser(String email, String password) {
        if (isValidFields()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                createUser(user);
//                            updateUI(user);
                                startActivity(new Intent(SignUnActivity.this, UserListActivity.class));

                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUnActivity.this, task.getException().toString(),
                                        Toast.LENGTH_LONG).show();
//                            updateUI(null);
                            }
                        }
                    });
        }

    }

    private void createUser(FirebaseUser firebaseUser) {
        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(userNameEditText.getText().toString().trim());

        userDatabaseReference.push().setValue(user);
    }


    public void handleSignUp(View view) {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        signUpUser(email, password);
    }

    public void navigateToLogin(View view) {
        startActivity(new Intent(SignUnActivity.this, LoginActivity.class));
    }
}