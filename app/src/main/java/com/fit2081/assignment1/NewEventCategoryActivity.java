package com.fit2081.assignment1;

import static com.fit2081.assignment1.SMSReceiver.SMS_FILTER;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.fit2081.assignment1.provider.CategoryEntity;
import com.fit2081.assignment1.provider.DatabaseViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class NewEventCategoryActivity extends AppCompatActivity {

    private EditText edtCategoryName, edtEventCount, edtLocation;
    private Switch switchIsActive;
    private TextView txtCategoryId;

    private DatabaseViewModel mDatabaseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_category);

        edtCategoryName = findViewById(R.id.edtCategoryName);
        edtEventCount = findViewById(R.id.edtEventCount);
        switchIsActive = findViewById(R.id.switchIsActive);
        txtCategoryId = findViewById(R.id.txtCategoryId);
        edtLocation = findViewById(R.id.edtLocation);

        mDatabaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class); //Initialize the database ViewModel. ViewModel is used to store and manage UI-related data in a lifecycle conscious way

        findViewById(R.id.btnSaveCategory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCategoryDetails();
            }
        });
    }

    private void saveCategoryDetails() {
        String name = edtCategoryName.getText().toString();
        String eventCountInput = edtEventCount.getText().toString();
        String location = edtLocation.getText().toString(); // Capture the location input
        boolean isActive = switchIsActive.isChecked();

        if (!name.matches("[A-Za-z0-9 ]+")) {
            Toast.makeText(this, "Invalid category name", Toast.LENGTH_LONG).show();
            return;
        }

        int eventCount = 0;
        try {
            eventCount = Integer.parseInt(eventCountInput);
            if (eventCount < 0) {
                eventCount = 0;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid Event Count: using default of 0", Toast.LENGTH_LONG).show();
        }

        String categoryId = generateCategoryId();
        String isActiveString = isActive ? "true" : "false";
        CategoryEntity newCategory = new CategoryEntity(categoryId, name, String.valueOf(eventCount), isActiveString, location); // Pass the location to the entity

        mDatabaseViewModel.insert(newCategory); // Insert the new category into the database using the ViewModel

        Toast.makeText(this, "Category saved successfully: " + categoryId, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }

    private String generateCategoryId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder categoryId = new StringBuilder("C");

        for (int i = 0; i < 2; i++) {
            categoryId.append(chars.charAt((int) (Math.random() * chars.length())));
        }

        categoryId.append("-");

        for (int i = 0; i < 4; i++) {
            categoryId.append((int) (Math.random() * 10));
        }

        return categoryId.toString();
    }
}