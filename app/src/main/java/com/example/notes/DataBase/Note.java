package com.example.notes.DataBase;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Note {



    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "title")
    @NonNull
    public String title;

    @ColumnInfo(name = "text")
    @NonNull
    public String text;


    @ColumnInfo(name = "date")
    public String date;


//    public Note() {
//    }

//    public Note(String title, String text) {
//        this.title = title;
//        this.text = text;
//    }




}
