package com.example.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Update;

import com.example.notes.DataBase.Note;

public class SecondActivity extends AppCompatActivity {
    ImageView finish, update;
    EditText title, description;
    Note note = new Note();
    Repository repository = new Repository();

    String titleSet, descriptionSet;
    String titleGet, descriptionGet;

    public String getTitleText() {
        title = findViewById(R.id.titleNote);
        titleGet = title.getText().toString();

        return titleGet;
    }

    public String getDescriptionText() {
        description = findViewById(R.id.description);
        descriptionGet = description.getText().toString();
        return descriptionGet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                if (TextUtils.isEmpty(getTitleText()) && TextUtils.isEmpty(getDescriptionText())){
                    finish();

                }else {
                    AlertDialog.Builder back_button = new AlertDialog.Builder(SecondActivity.this);
                    back_button.setMessage("Вы хотите выйти не сохроняя?")
                            .setCancelable(false)
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog button_back = back_button.create();
                    button_back.setTitle("Выйти");
                    button_back.show();
                }
            }
        });

        Intent intent = getIntent();
        if (TextUtils.isEmpty(getTitleText()) && TextUtils.isEmpty(getDescriptionText())) {
            titleSet = intent.getStringExtra("title");
            descriptionSet = intent.getStringExtra("description");
            title.setText(titleSet);
            description.setText(descriptionSet);
        }

        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(getTitleText()) && !TextUtils.isEmpty(getDescriptionText())){
                        InsertAsync insertAsync = new InsertAsync();
                        insertAsync.execute();

                        Intent i = new Intent(SecondActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();

                }else {
                    Toast.makeText(SecondActivity.this, "Сначала заполните поля", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class InsertAsync extends AsyncTask<Void, Void, Void>{
       private Repository repository = new Repository();
        @Override
        protected Void doInBackground(Void... voids) {
             repository.insertNewNote(new Note(getTitleText() , getDescriptionText()));
            return null;
        }
    }
    }


