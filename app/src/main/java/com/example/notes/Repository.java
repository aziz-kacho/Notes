package com.example.notes;

import com.example.notes.DataBase.App;
import com.example.notes.DataBase.DataBase;
import com.example.notes.DataBase.Note;
import com.example.notes.DataBase.NoteDao;

import java.util.List;

public class Repository {
  private DataBase dataBase;

        public Repository(){
            this.dataBase = App.getApplication().getDataBase();
        }

  private NoteDao noteDao = dataBase.noteDao();


    public List<Note> getListNotes(){
         List<Note> list = noteDao.getAllNotes();
    return list;
    }
}
