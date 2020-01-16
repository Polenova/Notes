package ru.android.polenova;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

public class ListNoteActivity extends AppCompatActivity {

    private String txtName;
    private String txtBody;
    private String txtDate;
    private TextView textViewName;
    private List<Note> notes = new ArrayList<>();
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);
        this.setTitle("заметки");
        initView();
        getNotes();
    }

    private List<Note> getNotes() {
        try {
            notes = FileNotes.importFromJSON(this);
        } catch (EmptyStackException e) {
            e.getMessage();
        }
        if (notes != null) {
            adapter = new NoteAdapter(this, notes);
            ListView listView = findViewById(R.id.list);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Данные добавлены", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    private void initView() {
        FloatingActionButton buttonAddNewNote = findViewById(R.id.floatingActionButton);
        buttonAddNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListNoteActivity.this, NewNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sett) {
            Intent intent = new Intent(ListNoteActivity.this, SettingsActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }
}
