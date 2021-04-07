package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.notes.Adapter.NotesAdapter;
import com.example.notes.DataBase.App;
import com.example.notes.DataBase.DataBase;
import com.example.notes.DataBase.Note;
import com.example.notes.DataBase.NoteDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    NotesAdapter notesAdapter;
    List<Note> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recyclerview
        recyclerView = findViewById(R.id.titleRecyclerview);
    linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
//    notesAdapter = new NotesAdapter(list);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(notesAdapter);






    }


class GetNotesAsync extends AsyncTask<Void, Void, List<Note>>{
       private Repository repository = new Repository();

    @Override
    protected List<Note> doInBackground(Void... voids) {
        List<Note> list = repository.getListNotes();
        return list;
    }

    @Override
    protected void onPostExecute(List<Note> list) {
        super.onPostExecute(list);
        notesAdapter = new NotesAdapter(list);

    }
}


}