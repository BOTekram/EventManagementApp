package com.fit2081.assignment1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fit2081.assignment1.provider.DatabaseViewModel;
import com.fit2081.assignment1.provider.EventEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListEvent extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseViewModel mDatabaseViewModel; // ViewModel for the database

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentListEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListEvent newInstance(String param1, String param2) {
        FragmentListEvent fragment = new FragmentListEvent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * This method will create the view for the fragment
     * It will inflate the layout and set the adapter for the recycler view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_event, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the database ViewModel
        mDatabaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);

        // Observe the LiveData from the ViewModel
        mDatabaseViewModel.getAllEvents().observe(getViewLifecycleOwner(), new Observer<List<EventEntity>>() { // Observe the LiveData from the ViewModel, When the data changes, it updates the adapter for the RecyclerView with the new list of events.
            @Override
            public void onChanged(List<EventEntity> eventEntities) {
                // Set the adapter with the updated data and handle item clicks
                EventAdapter adapter = new EventAdapter(eventEntities, new EventAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(EventEntity event) {
                        Intent intent = new Intent(getActivity(), EventGoogleResult.class); // Start the EventGoogleResult activity
                        intent.putExtra("eventName", event.getEventName()); // Pass the event name to the EventGoogleResult activity
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }
}