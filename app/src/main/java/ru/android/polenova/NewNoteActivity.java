package ru.android.polenova;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;


public class NewNoteActivity extends AppCompatActivity {

    private EditText editTextName;
    private MultiAutoCompleteTextView editTextBody;
    private EditText editTextDate;
    private String textName;
    private String textBody;
    private String textDate;
    private String textInfoNote = "notes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        this.setTitle("новые данные");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        editTextName = findViewById(R.id.editNameNote);
        editTextBody = findViewById(R.id.multiTextNote);
        editTextDate = findViewById(R.id.editDeadLine);

        ImageButton buttonDate = findViewById(R.id.imageButtonCalendar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            Toast.makeText(NewNoteActivity.this, "save", Toast.LENGTH_LONG).show();
            saveInfoNote();
            return false;
        } else if (id == R.id.action_clear) {
            Toast.makeText(NewNoteActivity.this, "clear", Toast.LENGTH_LONG).show();
            editTextName.getText().clear();
            editTextBody.getText().clear();
            editTextDate.getText().clear();
            return false;
        } else if (id == android.R.id.home) {
            Intent intent = new Intent(NewNoteActivity.this, ListNoteActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    private void saveInfoNote() {
        textName = editTextName.getText().toString();
        textBody = editTextBody.getText().toString();
        textDate = editTextDate.getText().toString();
        Note noteNewInfo = new Note(textName + "\n", textBody + "\n", textDate + "\n");
        BufferedWriter bufferedWriter = null;
        try {
            FileOutputStream fileOutputStreamLogin = openFileOutput(textInfoNote, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriterLogin = new OutputStreamWriter(fileOutputStreamLogin);
            bufferedWriter = new BufferedWriter(outputStreamWriterLogin);
            bufferedWriter.write(textName + "\n" + textBody + "\n" + textDate);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "готово", Toast.LENGTH_SHORT).show();
    }
}

