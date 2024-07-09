package com.fit2081.assignment1.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//Provides methods to interact with the database.
@Dao //Data Access Object
public interface InterfaceDao {

    @Query("SELECT * FROM categories")
    LiveData<List<CategoryEntity>> getAllCategories();

    @Query("SELECT * FROM events")
    LiveData<List<EventEntity>> getAllEvents();

    @Query("SELECT * FROM categories WHERE categoryId = :categoryId")
    LiveData<List<CategoryEntity>> getCategories(String categoryId);

    @Query("SELECT * FROM events WHERE eventName = :eventName")
    LiveData<List<EventEntity>> getEvents(String eventName);

    @Insert
    void addCategory(CategoryEntity category);

    @Insert
    void addEvent(EventEntity event);

    @Update
    void updateCategory(CategoryEntity category); //Update category

    @Query("DELETE FROM categories WHERE categoryName = :categoryName")
    void deleteCategories(String categoryName);

    @Query("DELETE FROM events WHERE eventName = :eventName")
    void deleteEvents(String eventName);

    @Query("DELETE FROM categories")
    void deleteAllCategories();

    @Query("DELETE FROM events")
    void deleteAllEvents();
}
