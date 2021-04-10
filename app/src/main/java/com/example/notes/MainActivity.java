package com.example.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.notes.Adapter.NotesAdapter;
import com.example.notes.DataBase.App;
import com.example.notes.DataBase.DataBase;
import com.example.notes.DataBase.Note;
import com.example.notes.DataBase.NoteDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    NotesAdapter notesAdapter;
    Note note = new Note();
    ///
    Context context;
    FloatingActionButton addButton;
    //


    private Context context(){
        context = this;
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Recyclerview
        recyclerView = findViewById(R.id.titleRecyclerview);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context(),  SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Async task
        GetNotesAsync notesAsync = new GetNotesAsync();
        notesAsync.execute();
    }

            //Запрос на удаление..
    private NotesAdapter.OnDeleteClickListener onDeleteClickListener =
            new NotesAdapter.OnDeleteClickListener() {
                @Override
                public void onDeleteClick(ImageView deleting) {
                    AlertDialog.Builder back_button = new AlertDialog.Builder(MainActivity.this);
                    back_button.setMessage("Вы действительно хотите удалить заметку?")
                            .setCancelable(false)
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //надо удалить запись
                                }
                            })
                            .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog button_back = back_button.create();
                    button_back.setTitle("Удаление");
                    button_back.show();
                }
            };

    private NotesAdapter.OnItemClickListener onItemClickListener =
            new NotesAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(Note note) {

                    // нажатый note получаем тут
//                    Log.d("TEST_TAG", "onItemClick: " + note.getTitle());
                    Intent intent = new Intent(context(),  SecondActivity.class);

                    intent.putExtra("title", note.getTitle());
                    intent.putExtra("description", note.getText());
                    startActivity(intent);
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
            notesAdapter = new NotesAdapter(list,onDeleteClickListener , onItemClickListener);
            recyclerView.setAdapter(notesAdapter);
        }
    }
}