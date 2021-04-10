package com.example.notes.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
     public abstract NoteDao noteDao();
}
