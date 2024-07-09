package com.fit2081.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fit2081.assignment1.provider.CategoryEntity;
import com.fit2081.assignment1.provider.DatabaseViewModel;
import com.fit2081.assignment1.provider.GoogleMapActivity;

import java.util.List;

public class FragmentListCategory extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseViewModel mDatabaseViewModel;

    public FragmentListCategory() {
        // Required empty public constructor
    }

    public static FragmentListCategory newInstance(String param1, String param2) {
        FragmentListCategory fragment = new FragmentListCategory();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class); //Initialize the database ViewModel
        mDatabaseViewModel.getAllCategories().observe(getViewLifecycleOwner(), new Observer<List<CategoryEntity>>() { //Observe the list of categories from the ViewModel and update the UI accordingly
            @Override
            public void onChanged(List<CategoryEntity> categoryEntities) {
                CategoryAdapter adapter = new CategoryAdapter(categoryEntities, new CategoryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(CategoryEntity category, View view) {
                        Toast.makeText(getContext(), "Location: " + category.getEventLocation(), Toast.LENGTH_SHORT).show(); //Toast message to display the location of the event
                        Intent intent = new Intent(getActivity(), GoogleMapActivity.class); //Intent to start the GoogleMapActivity
                        intent.putExtra("location", category.getEventLocation()); //Pass the location of the event to the GoogleMapActivity
                        intent.putExtra("categoryName", category.getCategoryName()); //Pass the category name to the GoogleMapActivity
                        startActivity(intent); //Start the GoogleMapActivity
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }
}
