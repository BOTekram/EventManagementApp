package com.fit2081.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
//Signup Page
public class MainActivity extends AppCompatActivity {
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsername = findViewById(R.id.txtEditUsername);
        edtPassword = findViewById(R.id.txtEditPassword);
        edtConfirmPassword = findViewById(R.id.txtConfirmPassword);

        // Prefill the username if available
        prefillUsername();
    }

    /**
     * Prefill the username field from SharedPreferences
     */
    private void prefillUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("Username", "");
        edtUsername.setText(savedUsername);
    }

    /**
     * Method triggered when Sign Up button is clicked
     */
    public void onSignUpButtonClick(View view) {
        String userNameString = edtUsername.getText().toString();
        String userPasswordString = edtPassword.getText().toString();
        String userConfirmPasswordString = edtConfirmPassword.getText().toString();
        registerUser(userNameString, userPasswordString, userConfirmPasswordString);
    }

    /**
     * Method to register the user
     */
    private void registerUser(String username, String password, String confirmPassword) {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        saveDataToSharedPreferences(username, password); // Save the data to SharedPreferences

        // Clear the EditText fields
        edtUsername.setText("");
        edtPassword.setText("");
        edtConfirmPassword.setText("");

        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    /**
     * Save the data to SharedPreferences
     */
    private void saveDataToSharedPreferences(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Username", username);
        editor.putString("Password", password);
        editor.apply();
    }

    /**
     * Method triggered when Already Registered? button is clicked
     */
    public void onAlreadyRegisteredClick(View view) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}