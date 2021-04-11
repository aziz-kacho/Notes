package com.example.notes;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.DataBase.Note;

public class SecondActivity extends AppCompatActivity {
    private ImageView backButton, update;
    private EditText title, description;

    private Repository repository = new Repository();
    private String titleSet, descriptionSet;
    private String titleGet, descriptionGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        title = findViewById(R.id.titleNote);
        description = findViewById(R.id.description);
        backButton = findViewById(R.id.back_button);
        update = findViewById(R.id.update);

        if (TextUtils.isEmpty(getTitleText()) && TextUtils.isEmpty(getDescriptionText())) {
            titleSet = getIntent().getStringExtra("title");
            descriptionSet = getIntent().getStringExtra("description");
            title.setText(titleSet);
            description.setText(descriptionSet);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForExit();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(getTitleText()) && !TextUtils.isEmpty(getDescriptionText())) {
                    if(getIntent().getStringExtra("title") != null &&
                            getIntent().getStringExtra("description") != null) {
                        int id = getIntent().getIntExtra("noteId", 0);
                        UpdateAsync updateAsync = new UpdateAsync(
                                getTitleText(),
                                getDescriptionText(),
                                id);
                        updateAsync.execute();
                    } else {
                        InsertAsync insertAsync = new InsertAsync();
                        insertAsync.execute();
                    }


                } else {
                    Toast.makeText(SecondActivity.this, "Сначала заполните поля", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkForExit() {
        if(getIntent().getStringExtra("title") != null &&
                getIntent().getStringExtra("description") != null) {

            if(!getIntent().getStringExtra("title").equals(getTitleText()) ||
                    !getIntent().getStringExtra("description").equals(getDescriptionText())
            ) {
                showAlertDialog();
            } else {
                finish();
            }
        } else {
            if (TextUtils.isEmpty(getTitleText()) || TextUtils.isEmpty(getDescriptionText())) {
                finish();
            } else {
                showAlertDialog();
            }
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder back_button = new AlertDialog.Builder(SecondActivity.this);
        back_button.setMessage("Вы хотите выйти не сохраняя?")
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

    @Override
    public void onBackPressed() {
        checkForExit();
    }

    private String getTitleText() {
        return title.getText().toString().trim();
    }

    private String getDescriptionText() {
        return description.getText().toString().trim();
    }

    private class InsertAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            repository.insertNewNote(new Note(getTitleText(), getDescriptionText()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();
        }
    }

    private class UpdateAsync extends AsyncTask<Void, Void, Void> {

        private String title;
        private String text;
        private int id;

        public UpdateAsync(String title, String text, int id) {
            this.title = title;
            this.text = text;
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            repository.updateNote(title, text, id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();
        }
    }
}


