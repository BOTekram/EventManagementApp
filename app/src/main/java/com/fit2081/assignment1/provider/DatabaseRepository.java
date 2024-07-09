package com.fit2081.assignment1.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Handles actual data operations. Acts as a mediator between the ViewModel and the DAO.
public class DatabaseRepository {

    private InterfaceDao mInterfaceDao;
    private LiveData<List<CategoryEntity>> mAllCategories;
    private LiveData<List<EventEntity>> mAllEvents;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    DatabaseRepository(Application application) {
        EMADatabase db = EMADatabase.getDatabase(application); //Get the database instance
        mInterfaceDao = db.interfaceDao(); //Get the DAO instance
        mAllCategories = mInterfaceDao.getAllCategories(); //Get all categories
        mAllEvents = mInterfaceDao.getAllEvents(); //Get all events
    }

    LiveData<List<CategoryEntity>> getAllCategories() {
        return mAllCategories;
    } //Return all categories

    LiveData<List<EventEntity>> getAllEvents() {
        return mAllEvents;
    } //Return all events

    void insert(CategoryEntity category) {
        databaseWriteExecutor.execute(() -> mInterfaceDao.addCategory(category)); //Insert a category
    }

    void insert(EventEntity event) {
        databaseWriteExecutor.execute(() -> mInterfaceDao.addEvent(event)); //Insert an event
    }

    void updateCategory(CategoryEntity category) {
        databaseWriteExecutor.execute(() -> mInterfaceDao.updateCategory(category)); //Update a category
    }

    void delete(CategoryEntity category) {
        databaseWriteExecutor.execute(() -> mInterfaceDao.deleteCategories(category.getCategoryName())); //Delete a category
    }

    void delete(EventEntity event) {
        databaseWriteExecutor.execute(() -> mInterfaceDao.deleteEvents(event.getEventName())); //Delete an event
    }

    void deleteAllCategories() {
        databaseWriteExecutor.execute(() -> mInterfaceDao.deleteAllCategories()); //Delete all categories
    }

    void deleteAllEvents() {
        databaseWriteExecutor.execute(() -> mInterfaceDao.deleteAllEvents());
    } //Delete all events
}