package com.example.notes.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    //Новая запись
    @Insert
    public void insertNewNote(Note note);

    // этот метод вытащит кокретную запись по id
    @Query("select * from `Note` where `id` = :id")
    public Note getNotes(int id);


    //    этот метод вытащит все записи из таблицы Note
    @Query("select * from `Note` order by `id` desc")
    public List<Note> getAllNotes();


    //  обновление записи
    @Query("UPDATE `Note` SET title = :title, text = :text WHERE `id` = :id")
    public void update(String title, String text, int id);


    //  удаление записи
    @Delete
    void delete(Note note);

    @Query("DELETE FROM Note")
    void deleteAllNotes();
}
