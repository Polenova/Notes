package ru.android.polenova;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

    private List<Note> notes = new ArrayList<>();
    private BaseAdapter adapter;
    private NoteRepository noteRepository = App.getNoteRepository();
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);
        this.setTitle(R.string.title_notes);
        initView();
        getNotesList();
    }

    private void initSwipeRefresh() {
        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notes.clear();
                getNotesList();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private List<Note> getNotesList() {
        try {
            notes = noteRepository.getNotes();
        } catch (EmptyStackException e) {
            e.getMessage();
        }
        if (notes != null) {
            adapter = new NoteAdapter(this, notes);
            ListView listView = findViewById(R.id.list);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, R.string.toast_error_add, Toast.LENGTH_LONG).show();
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
