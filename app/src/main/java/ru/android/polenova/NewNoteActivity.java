package ru.android.polenova;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class NewNoteActivity extends AppCompatActivity {

    private EditText editTextName;
    private MultiAutoCompleteTextView editTextBody;
    private EditText editTextDate;
    private String textName;
    private String textBody;
    private String textDate;

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
        ArrayList<Note> notes = new ArrayList<>();
        Note noteNewInfo = new Note(textName, textBody, textDate);
        notes.add(noteNewInfo);
        //adapter.notifyDataSetChanged();
        try {
            boolean result = FileNotes.exportToJSON(this, notes);
            if (result) {
                Toast.makeText(this, "готово", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "ошибка сохранения", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

