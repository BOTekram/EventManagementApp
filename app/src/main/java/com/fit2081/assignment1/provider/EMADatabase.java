package com.fit2081.assignment1.provider;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Manages the creation and versioning of the app's SQLite database.
@Database(entities = {EventEntity.class, CategoryEntity.class}, version = 1)
public abstract class EMADatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "ema_database";

    public abstract InterfaceDao interfaceDao();

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile EMADatabase INSTANCE; //Instance of the database. volatile keyword ensures that the value of INSTANCE is always read from and written to main memory

    static EMADatabase getDatabase(final Application context) {
        if (INSTANCE == null) { //Check if the database instance is null
            synchronized (EMADatabase.class) { //Synchronize the database instance
                if (INSTANCE == null) { //Check if the database instance is null
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EMADatabase.class, DATABASE_NAME)
                            .build(); //Create the database instance
                }
            }
        }
        return INSTANCE; //Return the database instance
    }



}
