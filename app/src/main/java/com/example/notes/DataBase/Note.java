package com.example.notes.DataBase;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Objects;

@Entity
public class Note {



    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    @ColumnInfo(name = "text")
    @NonNull
    private String text;


    @ColumnInfo(name = "date")
    private String date;


    public Note() {
    }

    //Дата не добавлен потому что для него пишется мотод в самом MainActivity
    public Note(String title, String text) {
        this.title = title;
        this.text = text;

        // Получаем дату
        String date = getCurrentDate();
        this.date = date;
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance(); // 08.04.2021
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day + "." + month + "." + year;
    }

    //Getter
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    //Setter

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
