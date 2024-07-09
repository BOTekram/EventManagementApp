package com.fit2081.assignment1.provider;
// EventEntity class storing the Event Details as objects

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "events") // SQLite Table name in the database
public class EventEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull //Cannot be null
    private int id;

    @ColumnInfo(name = "eventId")
    private String eventId;
    @ColumnInfo(name = "categoryId")
    private String categoryId;
    @ColumnInfo(name = "eventName")
    private String eventName;
    @ColumnInfo(name = "ticketsAvailable")
    private int ticketsAvailable;
    @ColumnInfo(name = "eventActive")
    private String eventActive;


    //Constructor for the events entity class
    public EventEntity(String eventId, String categoryId, String eventName, int ticketsAvailable, String eventActive) {

        this.eventId = eventId;
        this.categoryId = categoryId;
        this.eventName = eventName;
        this.ticketsAvailable = ticketsAvailable;
        this.eventActive = eventActive;
    }

    public int getId() {
        return id; //Getter for the id
    }

    public void setId(@NotNull int id) {
        this.id = id; //Setter for the id, cannot be null
    }

    public String getEventId() {
        return eventId; //Getter for the event id
    }

    public void setEventId(String eventId) {
        this.eventId = eventId; //Setter for the event id
    }

    public String getCategoryId() {
        return categoryId; //Getter for the category id
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId; //Setter for the category id
    }

    public String getEventName() {
        return eventName; //Getter for the event name
    }

    public void setEventName(String eventName) {
        this.eventName = eventName; //Setter for the event name
    }

    public int getTicketsAvailable() {
        return ticketsAvailable; //Getter for the tickets available
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable; //Setter for the tickets available
    }

    public String getEventActive() {
        return eventActive; //Getter for the event active
    }

    public void setEventActive(String eventActive) {
        this.eventActive = eventActive; //Setter for the event active
    }
}
