package com.chicagoteamapp.chicagoteamapp;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.chicagoteamapp.chicagoteamapp.data.room.TaskDatabase;

public class MyApp extends Application {

    private static MyApp sInstance;
    private TaskDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mDatabase = Room.databaseBuilder(this, TaskDatabase.class, "TaskDatabase.db")
                .addMigrations(TaskDatabase.MIGRATION_1_2)
                .allowMainThreadQueries()
                .build();
    }

    public static MyApp getInstance() {
        return sInstance;
    }

    public TaskDatabase getDatabase() {
        return mDatabase;
    }
}
