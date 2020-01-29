package ru.android.polenova;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;


public class FileNotes implements NoteRepository {

    private static final String FILE_NAME_NOTES = "notes";
    private static Gson gson = new Gson();
    private SharedPreferences preferences;

    public FileNotes(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME_NOTES, MODE_PRIVATE);
    }

    @Override
    public void saveNote(Note note) {
        preferences.edit()
                .putString(note.getId(), gson.toJson(note))
                .apply();
    }

    @Override
    public void deleteById(Note note) {
        preferences.edit()
                .remove(note.getId())
                .apply();
    }

    @Override
    public List<Note> getNotes() {
        Map<String, ?> all = preferences.getAll();
        List<Note> result = new ArrayList<>(all.size());
        for (Map.Entry<String, ?> entry : all.entrySet()) {
            String noteSerialized = (String) entry.getValue();
            result.add(gson.fromJson(noteSerialized, Note.class));
        }
        Collections.sort(result, new Comparator<Note>() {
            @Override
            public int compare(Note thisNote, Note anotherNote) {
                if (anotherNote.getDeadLineDate() != null) {
                    try {
                        return thisNote.getDeadLineDate().compareTo(anotherNote.getDeadLineDate());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return anotherNote.getLastModifiedDate().compareTo(thisNote.getLastModifiedDate());
            }
        });
        return result;
    }

    @Override
    public Note getNoteById(String id) {
        return gson.fromJson(preferences.getString(String.valueOf(id), ""), Note.class);
    }
}