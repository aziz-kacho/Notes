package com.example.notes.DataBase;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {
    public static App application;
    private DataBase dataBase;

    @Override
    public void onCreate() {

        super.onCreate();
        application = this;
        dataBase = Room.databaseBuilder(this, DataBase.class, "NotesDB")
                .build();
    }

    public static App getApplication() {
        return application;
    }

    public DataBase getDataBase() {
        return dataBase;
    }
}
