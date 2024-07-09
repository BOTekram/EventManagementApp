package com.fit2081.assignment1.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

//ViewModel class to provide data to the UI and survive configuration changes.
//Manages UI-related data in a lifecycle-aware manner.
public class DatabaseViewModel extends AndroidViewModel {

    private DatabaseRepository mRepository; //Instance of database repository
    private LiveData<List<CategoryEntity>> mAllCategories; //LiveData object to hold all categories
    private LiveData<List<EventEntity>> mAllEvents; //LiveData object to hold all events

    public DatabaseViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DatabaseRepository(application);
        mAllCategories = mRepository.getAllCategories();
        mAllEvents = mRepository.getAllEvents();
    }

    public LiveData<List<CategoryEntity>> getAllCategories() {
        return mAllCategories;
    } //Return all categories

    public LiveData<List<EventEntity>> getAllEvents() {
        return mAllEvents;
    } //Return all events

    public void insert(CategoryEntity category) {
        mRepository.insert(category);
    } //Insert a category

    public void insert(EventEntity event) {
        mRepository.insert(event);
    } //Insert an event

    public void updateCategory(CategoryEntity category) {
        mRepository.updateCategory(category);
    } //Update a category

    public void delete(CategoryEntity category) {
        mRepository.delete(category);
    } //Delete a category

    public void delete(EventEntity event) {
        mRepository.delete(event);
    } //Delete an event

    public void deleteAllCategories() {
        mRepository.deleteAllCategories();
    } //Delete all categories

    public void deleteAllEvents() {
        mRepository.deleteAllEvents();
    } //Delete all events
}