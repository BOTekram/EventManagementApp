package com.fit2081.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    // Declaring EditText fields
    private EditText edtUsername;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initializing EditText fields
        edtUsername = findViewById(R.id.editTextUsername);
        edtPassword = findViewById(R.id.editTextPassword);

        // Prefilling username field if available
        prefillUsername();
    }

    // Method to prefill the username field from SharedPreferences
    private void prefillUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("Username", "");
        edtUsername.setText(savedUsername);
    }

    // Method triggered when login button is clicked
    public void onLoginButtonClick(View view) {
        // Extracting username and password from EditText fields
        String userNameString = edtUsername.getText().toString();
        String userPasswordString = edtPassword.getText().toString();

        // Validating the entered credentials
        if (validateCredentials(userNameString, userPasswordString)) {
            // Redirect to Dashboard Activity if credentials are valid
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        } else {
            // Showing a toast message for authentication failure
            Toast.makeText(this, "Authentication failure: Username or Password incorrect", Toast.LENGTH_SHORT).show();
        }

        //Clearing the password field
        edtPassword.setText("");
    }

    // Method to validate the entered credentials
    private boolean validateCredentials(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("Username", "");
        String savedPassword = sharedPreferences.getString("Password", "");

        // Comparing entered username and password with saved credentials
        return username.equals(savedUsername) && password.equals(savedPassword);
    }





}