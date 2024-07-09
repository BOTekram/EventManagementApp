package com.fit2081.assignment1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fit2081.assignment1.provider.EventEntity;

import java.util.List;

// Adapter class for the RecyclerView to display the rows of recycler view that are visible so only a few rows are loaded each time
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<EventEntity> db; // A list of EventEntity objects, representing the data to be displayed.
    private OnItemClickListener listener; //An interface instance to handle item click events.


    // An interface for handling item click events.
    public interface OnItemClickListener {
        void onItemClick(EventEntity event);
    }


    // A constructor to initialize the list of events and listener.
    public EventAdapter(List<EventEntity> db, OnItemClickListener listener) {
        this.db = db;
        this.listener = listener;
    } // constructor to initialize the list of events and listener

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventEntity event = db.get(position);
        holder.tvIdEvent.setText(event.getEventId());
        holder.tvEventName.setText(event.getEventName());
        holder.tvCategoryIDEvent.setText(event.getCategoryId());
        holder.tvTicketsAvailable.setText(String.valueOf(event.getTicketsAvailable()));
        holder.tvActiveEvent.setText(event.getEventActive());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(event);
            }
        });
    }

    // Returns the number of items in the list, null list returns 0
    @Override
    public int getItemCount() {
        if (db == null)
            return 0;
        return db.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIdEvent, tvEventName, tvCategoryIDEvent, tvTicketsAvailable, tvActiveEvent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvIdEvent = itemView.findViewById(R.id.tvIdEvent);
            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvCategoryIDEvent = itemView.findViewById(R.id.tvCategoryIDEvent);
            tvTicketsAvailable = itemView.findViewById(R.id.tvTicketsAvailable);
            tvActiveEvent = itemView.findViewById(R.id.tvActiveEvent);
        }
    }
}
