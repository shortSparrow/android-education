package com.android.taxiapp.auth;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.taxiapp.R;
import com.android.taxiapp.model.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import util.Validate;


interface LoginSignInListener {
    public void signUpButtonOnClick(View v);
    public void loginButtonOnClick(View v);
    public void navigateToLoginButtonOnClick(View v);
}


public class HandleSignIn {
    private static final String TAG = "HandleSignUp";
    Activity activity;
    FirebaseDatabase database;
    DatabaseReference usersDB;
    private FirebaseAuth mAuth;

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputFirstName;
    private TextInputLayout textInputLastName;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;
    private TextView authTextError;

    public Button signUpButton;
    public Button loginButton;
    private TextView navigateToLoginButton;

    private boolean signUpMode = true;

    ArrayList inputFields;

    public HandleSignIn(Activity activity) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersDB = database.getReference("users");


        textInputEmail = activity.findViewById(R.id.email_text_input);
        textInputFirstName = activity.findViewById(R.id.first_name_text_input);
        textInputLastName = activity.findViewById(R.id.last_name_text_input);
        textInputPassword = activity.findViewById(R.id.password_text_input);
        textInputConfirmPassword = activity.findViewById(R.id.confirm_password_text_input);
        authTextError = activity.findViewById(R.id.auth_message_error);

        signUpButton = activity.findViewById(R.id.sign_up_button);
        loginButton = activity.findViewById(R.id.login_button);
        navigateToLoginButton = activity.findViewById(R.id.navigate_to_login_button);

        LoginSignInListener myListener = (LoginSignInListener) activity;

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.signUpButtonOnClick(v);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.loginButtonOnClick(v);
            }
        });

        navigateToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.navigateToLoginButtonOnClick(v);
            }
        });

        inputFields = new ArrayList();
        inputFields.add(textInputEmail);
        inputFields.add(textInputFirstName);
        inputFields.add(textInputLastName);
        inputFields.add(textInputPassword);
        inputFields.add(textInputConfirmPassword);

        // clear error on change text
        for (Object textInput : inputFields) {
            TextInputLayout item = (TextInputLayout) textInput;
            item.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (item.getError() != null) {
                        item.setError(null);
                    }
                }
            });
        }
    }


    public boolean validateEmail() {
        String value = textInputEmail.getEditText().getText().toString().trim();

        String errorMessage = null;

        if (value.isEmpty()) {
            errorMessage = "this filed is required";
        } else if (!Validate.isEmail(value)) {
            errorMessage = "invalid email";
        }

        textInputEmail.setError(errorMessage);

        return returnIsValid(errorMessage);
    }

    public boolean validateFirstName() {
        String value = textInputFirstName.getEditText().getText().toString().trim();

        String errorMessage = null;

        if (value.isEmpty()) {
            errorMessage = "this filed is required";
        } else if (value.length() > 20) {
            errorMessage = "max length for name are 20 characters";
        }

        textInputFirstName.setError(errorMessage);

        return returnIsValid(errorMessage);
    }

    public boolean validateLastName() {
        String value = textInputLastName.getEditText().getText().toString().trim();

        String errorMessage = null;

       if (value.length() > 20) {
            errorMessage = "max length for name are 20 characters";
        }

        textInputLastName.setError(errorMessage);

        return returnIsValid(errorMessage);
    }

    public boolean validatePasswordFiled(String value, TextInputLayout target) {
        String errorMessage = null;

        if (value.isEmpty()) {
            errorMessage = "this filed is required";
        } else if (value.length() < 7) {
            errorMessage = "min length are 7 characters";
        }

        target.setError(errorMessage);

        return returnIsValid(errorMessage);
    }

    public boolean validatePasswords() {
        String passwordValue = textInputPassword.getEditText().getText().toString().trim();
        String confirmPasswordValue = textInputConfirmPassword.getEditText().getText().toString().trim();
        boolean passwordMatched = false;

        boolean passwordIsValid = validatePasswordFiled(passwordValue, textInputPassword);
        boolean confirmIsValid = validatePasswordFiled(confirmPasswordValue, textInputConfirmPassword);

        if (passwordIsValid && confirmIsValid) {
            if (passwordValue.equals(confirmPasswordValue)) {
                passwordMatched = true;
            } else {
                textInputConfirmPassword.setError("password doesn't match");
                passwordMatched = false;
            }
        }

        return passwordIsValid && confirmIsValid && passwordMatched;
    }

    public boolean validateAllFields() {
        boolean validEmail = validateEmail();
        boolean validFirstName = validateFirstName();
        boolean validLastName = validateLastName();
        boolean validPasswordFields = validatePasswords();

        return validEmail && validFirstName && validLastName && validPasswordFields;
    }

    public boolean returnIsValid(Object errorMessage) {
        if (errorMessage == null) {
            return true;
        }

        return false;
    }

    private void clearAllFields() {
        for (Object textInput : inputFields) {
            TextInputLayout item = (TextInputLayout) textInput;
            item.getEditText().setText("");
            item.setError(null);
        }
    }

    public void toggleMode() {
        clearAllFields();

        if (signUpMode) {
            signUpMode = false;
            textInputFirstName.setVisibility(TextInputLayout.GONE);
            textInputLastName.setVisibility(TextInputLayout.GONE);
            textInputConfirmPassword.setVisibility(TextInputLayout.GONE);

            signUpButton.setVisibility(Button.GONE);
            loginButton.setVisibility(Button.VISIBLE);
            navigateToLoginButton.setText("Tap to Sign Up");
        } else {
            signUpMode = true;
            textInputFirstName.setVisibility(TextInputLayout.VISIBLE);
            textInputLastName.setVisibility(TextInputLayout.VISIBLE);
            textInputConfirmPassword.setVisibility(TextInputLayout.VISIBLE);

            signUpButton.setVisibility(Button.VISIBLE);
            loginButton.setVisibility(Button.GONE);
            navigateToLoginButton.setText("Tap to Login");
        }
    }

    public void createUser(String userRole) {
        String password = textInputPassword.getEditText().getText().toString().trim();
        User user = prepareUser(userRole);

        HandleAuthUser handleAuthUser = new HandleAuthUser(activity);
        handleAuthUser.createUser(user, password);
    }


    public void loginUser(String userRole) {
        String password = textInputPassword.getEditText().getText().toString().trim();
        User user = prepareUser(userRole);

        HandleAuthUser handleAuthUser = new HandleAuthUser(activity);
        handleAuthUser.loginUser(user, password);
    }

    public User prepareUser(String userRole) {
        User user = new User();
        user.setFirstName(textInputFirstName.getEditText().getText().toString().trim());
        user.setLastName(textInputLastName.getEditText().getText().toString().trim());
        user.setEmail(textInputEmail.getEditText().getText().toString().trim());
        user.setRole(userRole);

        return user;
    }
}
