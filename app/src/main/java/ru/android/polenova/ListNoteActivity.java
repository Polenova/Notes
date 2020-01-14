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
        adapter = new NoteAdapter(this, notes);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    private List<Note> getNotes() {
        ArrayList<String> textList = new ArrayList<>();
        BufferedReader bufferedReaderNameNote = null;
        try {
            File file = FileNotes.getTextFile(this, false);
            bufferedReaderNameNote = new BufferedReader(new FileReader(file));
            String txt = bufferedReaderNameNote.readLine();
            while (txt != null) {
                textList.add(txt);
                txt = bufferedReaderNameNote.readLine();
            }
            bufferedReaderNameNote.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0, length = textList.size(); i < length; i += 3) {
            notes.add(new Note(textList.get(i), textList.get(i + 1), textList.get(i + 2)));
        }

        return notes;
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
        // Inflate the menu, this adds items to the action bar if it is present.
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
