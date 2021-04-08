package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.notes.Adapter.NotesAdapter;
import com.example.notes.DataBase.App;
import com.example.notes.DataBase.DataBase;
import com.example.notes.DataBase.Note;
import com.example.notes.DataBase.NoteDao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recyclerview
        recyclerView = findViewById(R.id.titleRecyclerview);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Async task
        GetNotesAsync notesAsync = new GetNotesAsync();
        notesAsync.execute();
    }

    private NotesAdapter.OnItemClickListener onItemClickListener =
            new NotesAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Note note) {
                    // нажатый note получаем тут
                    Log.d("TEST_TAG", "onItemClick: " + note.getTitle());
                }
            };

    class GetNotesAsync extends AsyncTask<Void, Void, List<Note>> {
        private Repository repository = new Repository();

        @Override
        protected List<Note> doInBackground(Void... voids) {
//            repository.insertNewNote(new Note("Some second note", "Some note here. Hello world, this is my new note"));
            List<Note> list = repository.getListNotes();
            return list;
        }

        @Override
        protected void onPostExecute(List<Note> list) {
            super.onPostExecute(list);
            notesAdapter = new NotesAdapter(list, onItemClickListener);
            recyclerView.setAdapter(notesAdapter);
        }
    }


}