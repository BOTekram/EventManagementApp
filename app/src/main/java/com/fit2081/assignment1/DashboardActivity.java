package com.fit2081.assignment1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fit2081.assignment1.provider.CategoryEntity;
import com.fit2081.assignment1.provider.DatabaseViewModel;
import com.fit2081.assignment1.provider.EventEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

public class DashboardActivity extends AppCompatActivity {

    private EditText edtEventName, edtCategoryId, edtTicketsAvailable;
    private Switch switchIsActive1;
    private TextView edtEventId;

    private DrawerLayout drawer;
    private DatabaseViewModel mDatabaseViewModel;
    private List<CategoryEntity> categoryList;

    private View touchPad;
    private TextView gestureTapTv;

    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        initializeViews(); //initialize the views
        setupToolbarAndDrawer(); //setup the toolbar and drawer
        setupFab(); //setup the floating action button

        MyGestureListener listner = new MyGestureListener(); //create a gesture listener
        mDetector = new GestureDetector(this, listner); //create a gesture detector

        touchPad.setOnTouchListener(new View.OnTouchListener() { //set the touch listener
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event); //detect the gesture


                return true; //returning false wont detect the gesture
            }
        });


        mDatabaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class); //initialize the database ViewModel
        observeCategories();  //observe the categories
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            gestureTapTv.setText("Long Pressd");
            //clearFields();
            clearFields();
            super.onLongPress(e);
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            gestureTapTv.setText("Double Tapped");
            validateAndSaveEventDetails(); //validate and save the event details
            return super.onDoubleTap(e);
        }
    }

    private void initializeViews() {
        edtEventName = findViewById(R.id.edtEventName);
        edtCategoryId = findViewById(R.id.edtCategoryId);
        edtTicketsAvailable = findViewById(R.id.edtTicketsAvailable);
        switchIsActive1 = findViewById(R.id.switchIsActive);
        edtEventId = findViewById(R.id.edtEventId);
        touchPad = findViewById(R.id.touchPad);
        gestureTapTv = findViewById(R.id.gestureTap);
        drawer = findViewById(R.id.drawer_layout);
    }

    private void setupToolbarAndDrawer() {
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Dashboard");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setupDrawer();
    }

    private void setupFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateAndSaveEventDetails()) {
                    Snackbar.make(view, "Event Saved Successfully", Snackbar.LENGTH_LONG)
                            .setAction("Undo", undoOnClickListener).show();
                }
            }
        });
    }

    private void observeCategories() {
        mDatabaseViewModel.getAllCategories().observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(List<CategoryEntity> categories) {
                categoryList = categories;
            }
        });
    }



    private void clearFields() {
        edtEventName.setText("");
        edtCategoryId.setText("");
        edtEventId.setText("");
        edtTicketsAvailable.setText("");
        switchIsActive1.setChecked(false);
    }

    private View.OnClickListener undoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Undo functionality not implemented", Snackbar.LENGTH_LONG).show();
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.option_refresh) {
            refreshUI();
        } else if (id == R.id.option_clear_event_form) {
            clearFields();
        } else if (id == R.id.option_del_all_cat) {
            deleteAllCategory();
        } else if (id == R.id.option_del_all_events) {
            deleteAllEvents();
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshUI() {
        clearFields();
        mDatabaseViewModel.getAllCategories().observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(List<CategoryEntity> categories) {
                categoryList = categories;
            }
        });
    }

    private void deleteAllCategory() {
        mDatabaseViewModel.deleteAllCategories();
        Toast.makeText(this, "All categories deleted", Toast.LENGTH_SHORT).show();
    }

    private void deleteAllEvents() {
        mDatabaseViewModel.deleteAllEvents();
        Toast.makeText(this, "All events deleted", Toast.LENGTH_SHORT).show();
    }

    private boolean validateAndSaveEventDetails() {
        String name = edtEventName.getText().toString().trim();
        String categoryId = edtCategoryId.getText().toString().trim();
        String ticketsAvailableString = edtTicketsAvailable.getText().toString().trim();
        String isActive = switchIsActive1.isChecked() ? "Active" : "Inactive";

        if (!name.matches(".*[a-zA-Z]+.*") || name.matches(".*[%]+.*")) {
            Toast.makeText(this, "Invalid event name", Toast.LENGTH_LONG).show();
            return false;
        }

        int ticketsAvailable;
        try {
            ticketsAvailable = Integer.parseInt(ticketsAvailableString);
            if (ticketsAvailable < 0) {
                Toast.makeText(this, "Invalid 'Tickets available': this field can only contain 0 or positive numbers.", Toast.LENGTH_LONG).show();
                ticketsAvailable = 0;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid 'Tickets available': this field must be a number.", Toast.LENGTH_LONG).show();
            return false;
        }

        CategoryEntity category = validateCategoryId(categoryId);
        if (category == null) {
            Toast.makeText(this, "Category does not exist", Toast.LENGTH_LONG).show();
            return false;
        }

        String eventId = generateEventId();
        EventEntity newEvent = new EventEntity(eventId, categoryId, name, ticketsAvailable, isActive);
        mDatabaseViewModel.insert(newEvent);

        incrementEventCount(category);

        edtEventId.setText(eventId);
        Toast.makeText(this, "Event saved: " + eventId + " to " + categoryId, Toast.LENGTH_LONG).show();
        return true;
    }

    private CategoryEntity validateCategoryId(String categoryId) {
        if (categoryList != null) {
            for (CategoryEntity category : categoryList) {
                if (category.getCategoryId().equals(categoryId)) {
                    return category;
                }
            }
        }
        return null;
    }

    private void incrementEventCount(CategoryEntity category) {
        int eventCount = Integer.parseInt(category.getEventCount());
        eventCount++;
        category.setEventCount(String.valueOf(eventCount));
        mDatabaseViewModel.updateCategory(category);
    }

    private String generateEventId() {
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder("E");
        Random rnd = new Random();
        for (int i = 0; i < 2; i++) {
            builder.append(alphaNumericString.charAt(rnd.nextInt(alphaNumericString.length())));
        }
        builder.append("-");
        for (int i = 0; i < 5; i++) {
            builder.append(rnd.nextInt(10));
        }
        return builder.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupDrawer() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                Intent intent = null;

                if (id == R.id.nav_view_all_cat) {
                    intent = new Intent(DashboardActivity.this, ListCategoryActivity.class);
                } else if (id == R.id.nav_add_cat) {
                    intent = new Intent(DashboardActivity.this, NewEventCategoryActivity.class);
                } else if (id == R.id.nav_view_all_events) {
                    intent = new Intent(DashboardActivity.this, ListEventActivity.class);
                } else if (id == R.id.nav_logout) {
                    finish();
                }

                if (intent != null) {
                    startActivity(intent);
                    drawer.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
    }
}
