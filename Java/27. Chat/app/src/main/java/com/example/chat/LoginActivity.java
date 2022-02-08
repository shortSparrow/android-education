package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import utils.Validate;

public class LoginActivity extends AppCompatActivity {
    private static final Integer RC_SIGN_IN = 5;
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private SignInButton googleSignInButton;
    private TextView signUpNavigateButton;

    GoogleSignInClient mGoogleSignInClient;

    FirebaseDatabase database;
    DatabaseReference userDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        userDatabaseReference = database.getReference("users");


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("700598163874-l1klntvmdctpupu4hlv4cq65cijfkt79.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        ScrollView loginLayout = findViewById(R.id.login_layout);
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, UserListActivity.class));
        } else {
            loginLayout.setVisibility(ScrollView.VISIBLE);
        }

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        signUpNavigateButton = findViewById(R.id.sign_up_navigate_button);
        googleSignInButton = findViewById(R.id.google_sign_in_button);
        googleSignInButton = findViewById(R.id.google_sign_in_button);
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);

        googleSignInButton.setOnClickListener(this::signInByGoogle);

    }

    public void showToast(String message) {
        Toast.makeText(LoginActivity.this, message,
                Toast.LENGTH_SHORT).show();
    }

    public boolean isValidFields() {
        String email = emailEditText.getText().toString().trim();
        if (!Validate.isValidEmail(email)) {
            showToast("Email is invalid");
            return false;
        }

        String password = passwordEditText.getText().toString().trim();
        if (password.length() < 6) {
            showToast("password mush contains at least 6 characters");
            return false;
        }

        return true;
    }

    public void logIn(String email, String password) {
        if (isValidFields()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                                startActivity(new Intent(LoginActivity.this, UserListActivity.class));
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }
                        }
                    });
        }

    }

    public void handleLogin(View view) {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        logIn(email, password);
    }

    public void navigateToSignUp(View view) {
        startActivity(new Intent(LoginActivity.this, SignUnActivity.class));
    }


    public void signInByGoogle(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);




                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, "uid" + mAuth.getUid());

                            String uid =  mAuth.getUid();

                            userDatabaseReference.child(uid).addListenerForSingleValueEvent( new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(!dataSnapshot.exists()) {
                                        Log.d(TAG, "user not exist " + uid);
                                        createUser(user);
                                    } else {
                                        Log.d(TAG, "user exist " + uid);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    Log.d(TAG,error.getMessage()); //Don't ignore potential errors!
                                }
                            });

                            startActivity(new Intent(LoginActivity.this, UserListActivity.class));
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    private void createUser(FirebaseUser firebaseUser) {
        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(firebaseUser.getDisplayName());

        userDatabaseReference.child(firebaseUser.getUid()).setValue(user);
    }


}