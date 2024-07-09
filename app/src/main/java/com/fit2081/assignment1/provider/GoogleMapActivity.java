package com.fit2081.assignment1.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fit2081.assignment1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.fit2081.assignment1.databinding.ActivityGoogleMapBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityGoogleMapBinding binding;
    private String location;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGoogleMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the location and category name from the intent
        Intent intent = getIntent();
        location = intent.getStringExtra("location");
        categoryName = intent.getStringExtra("categoryName");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set the initial location based on the category location
        if (location != null && !location.isEmpty()) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocationName(location, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0); // Get the first address
                    LatLng categoryLocation = new LatLng(address.getLatitude(), address.getLongitude()); // Get the latitude and longitude of the address

                    // Add a marker at the category location and move the camera
                    Marker marker = mMap.addMarker(new MarkerOptions().position(categoryLocation).title(categoryName)); // Add a marker at the category location
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(categoryLocation, 10)); // Move the camera to the category location, zoom level 10

                    // Set an info window adapter to display the city name
                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                        @Nullable
                        @Override
                        public View getInfoWindow(@NonNull Marker marker) {
                            return null; // Use the default info window frame
                        }

                        @Nullable // Return null to use the default info window frame
                        @Override // Set the title of the marker to the city name
                        public View getInfoContents(@NonNull Marker marker) {
                            Geocoder geocoder = new Geocoder(GoogleMapActivity.this, Locale.getDefault()); // Get the city name from the marker's latitude and longitude
                            try {
                                List<Address> addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1); // Get the city name from the marker's latitude and longitude
                                if (addresses != null && !addresses.isEmpty()) { // If the city name is found, set the marker's title to the city name
                                    Address address = addresses.get(0); // Get the first address
                                    String cityName = address.getLocality(); // Get the city name
                                    if (cityName != null && !cityName.isEmpty()) {
                                        marker.setTitle(cityName); // Set the marker's title to the city name
                                    } else {
                                        marker.setTitle("City name not found"); // Set the marker's title to "City name not found"
                                    }
                                } else {
                                    marker.setTitle("City name not found"); // Set the marker's title to "City name not found"
                                }
                            } catch (IOException e) {
                                e.printStackTrace(); // Print the stack trace
                                marker.setTitle("Error finding city name"); // Set the marker's title to "Error finding city name"
                            }
                            return null; // Use the default info window frame
                        }
                    });

                    // Show the info window immediately
                    marker.showInfoWindow();
                } else {
                    Toast.makeText(this, "Category address not found", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error finding location", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Location not specified", Toast.LENGTH_LONG).show();
        }

        // Set a click listener on the map to show a Toast with the country name
        mMap.setOnMapClickListener(latLng -> showCountryName(latLng));
    }

    private void showCountryName(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String countryName = address.getCountryName();
                if (countryName != null && !countryName.isEmpty()) {
                    Toast.makeText(this, "The selected Country is: " + countryName, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Country name not found", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Country name not found", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error finding country name", Toast.LENGTH_LONG).show();
        }
    }
}
