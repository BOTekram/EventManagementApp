package com.fit2081.assignment1.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    private int id;
    @ColumnInfo(name = "categoryId")
    private String categoryId;
    @ColumnInfo(name = "categoryName")
    private String categoryName;
    @ColumnInfo(name = "eventCount")
    private String eventCount;
    @ColumnInfo(name = "categoryActive")
    private String categoryActive;

    @ColumnInfo(name = "eventLocation")
    private String eventLocation;


    public CategoryEntity(String categoryId, String categoryName, String eventCount, String categoryActive, String eventLocation) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.eventCount = eventCount;
        this.categoryActive = categoryActive;
        this.eventLocation = eventLocation;
    }


    public void incrementEventCount(){
        int count = Integer.parseInt(eventCount);
        count++;
        eventCount = Integer.toString(count);
    }

    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getEventCount() {
        return eventCount;
    }

    public void setEventCount(String eventCount) {
        this.eventCount = eventCount;
    }

    public String getCategoryActive() {
        return categoryActive;
    }

    public void setCategoryActive(String categoryActive) {
        this.categoryActive = categoryActive;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
}
