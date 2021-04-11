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
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notes.Adapter.NotesAdapter;
import com.example.notes.DataBase.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Widgets
    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    private ImageButton deleteAllButton;


    private LinearLayoutManager linearLayoutManager;
    private NotesAdapter notesAdapter;
    private Context context;
    private Repository repository = new Repository();
    private int notesAmount = 0;

    private Context getContext() {
        context = this;
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recyclerview
        recyclerView = findViewById(R.id.titleRecyclerview);
        addButton = findViewById(R.id.add_button);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        deleteAllButton = findViewById(R.id.delete_all_button);

        recyclerView.setLayoutManager(linearLayoutManager);
        notesAdapter = new NotesAdapter(onItemClickListener);
        recyclerView.setAdapter(notesAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notesAmount != 0) {
                    showDeleteAllDialog();
                } else {
                    Toast.makeText(MainActivity.this, "Список пуст", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });
    }

    private void showDeleteAllDialog() {
        AlertDialog.Builder buil = new AlertDialog.Builder(MainActivity.this);
        buil.setMessage("Вы уверены, что хотите удалить все заметки?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteAllAsync deleteAllAsync = new DeleteAllAsync();
                        deleteAllAsync.execute();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog button_back = buil.create();
        button_back.setTitle("Удалить всё");
        button_back.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Async task
        getListAsync();
    }

    private void getListAsync() {
        GetNotesAsync notesAsync = new GetNotesAsync();
        notesAsync.execute();
    }

    private NotesAdapter.OnItemClickListener onItemClickListener =
            new NotesAdapter.OnItemClickListener() {

                // Общее нажатие
                @Override
                public void onItemClick(Note note) {
                    Intent intent = new Intent(getContext(), SecondActivity.class);

                    intent.putExtra("noteId", note.getId());
                    intent.putExtra("title", note.getTitle());
                    intent.putExtra("description", note.getText());
                    intent.putExtra("date", note.getDate());
                    startActivity(intent);
                }

                // Запрос на удаление..
                @Override
                public void onDeleteClick(Note note) {
                    showDeleteDialog(note);
                }
            };

    private void showDeleteDialog(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Вы действительно хотите удалить заметку?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //надо удалить запись
                        DeleteNoteAsync deleteNoteAsync =
                                new DeleteNoteAsync(note);
                        deleteNoteAsync.execute();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setTitle("Удаление");
        dialog.show();
    }

    class DeleteAllAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            repository.deleteAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getListAsync();
        }
    }

    class GetNotesAsync extends AsyncTask<Void, Void, List<Note>> {

        @Override
        protected List<Note> doInBackground(Void... voids) {
            List<Note> list = repository.getListNotes();
            notesAmount = list.size();
            return list;
        }

        @Override
        protected void onPostExecute(List<Note> list) {
            super.onPostExecute(list);
            notesAdapter.setList(list);
        }
    }

    class DeleteNoteAsync extends AsyncTask<Void, Void, List<Note>> {

        Note note;

        public DeleteNoteAsync(Note note) {
            this.note = note;
        }

        @Override
        protected List<Note> doInBackground(Void... voids) {
            repository.deleteNote(note);
            return null;
        }

        @Override
        protected void onPostExecute(List<Note> list) {
            super.onPostExecute(list);
            getListAsync();
        }
    }
}