package com.example.notes;

import com.example.notes.DataBase.App;
import com.example.notes.DataBase.DataBase;
import com.example.notes.DataBase.Note;
import com.example.notes.DataBase.NoteDao;

import java.util.List;

public class Repository {
    private DataBase dataBase;
    private NoteDao noteDao;

    public Repository() {
        this.dataBase = App.getApplication().getDataBase();
        noteDao = dataBase.noteDao();
    }
<<<<<<< HEAD

    public List<Note> getListNotes() {
        List<Note> list = noteDao.getAllNotes();
        return list;
    }

    public void insertNewNote(Note note) {
        noteDao.insertNewNote(note);
    }

    public void insertUpdateNote(Note note){
        noteDao.update(note);
=======

    public List<Note> getListNotes() {
        List<Note> list = noteDao.getAllNotes();
        return list;
    }

    public void insertNewNote(Note note) {
        noteDao.insertNewNote(note);
>>>>>>> 2ebcb7bb4a5525fd4821989266cc08f4fadf3fe9
    }
}
