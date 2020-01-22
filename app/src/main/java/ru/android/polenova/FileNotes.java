package ru.android.polenova;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;


public class FileNotes {

    private static final String FILE_NAME_NOTES = "notes";
    private static Gson gson = new Gson();

    static void exportToJSON(Context context, Note note) {
        SharedPreferences preferences = getPreferences(context);
        preferences.edit()
                .putString(note.getDate().toString(), gson.toJson(note))
                .apply();
    }

    static void remove(Context context, Note note) {
        SharedPreferences preferences = getPreferences(context);
        preferences.edit()
                .remove(note.getDate().toString())
                .apply();
    }

    static List<Note> importFromJSON(Context context) {
        SharedPreferences preferences = getPreferences(context);
        Map<String, ?> all = preferences.getAll();
        List<Note> result = new ArrayList<>(all.size());
        for (Map.Entry<String, ?> entry : all.entrySet()) {
            String noteSerialized = (String) entry.getValue();
            result.add(gson.fromJson(noteSerialized, Note.class));
        }
        Collections.sort(result, new Comparator<Note>() {
            @Override
            public int compare(Note thisNote, Note anotherNote) {
                if (anotherNote.getTextDateNote() != null) {
                    return anotherNote.getTextDateNote().compareTo(thisNote.getTextDateNote()) + anotherNote.getDate().compareTo(thisNote.getDate());
                }
                return anotherNote.getDate().compareTo(thisNote.getDate());
            }
        });
        return result;
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(FILE_NAME_NOTES, MODE_PRIVATE);
    }
}


    /*private static final String FILE_NAME = "notes.json";
    private  static Gson gson = new Gson();
    private static DataItem dataItem = new DataItem();

    static boolean exportToJSON(Context context, List<Note> notesList) {
        dataItem.setNotes(notesList);
        String jsonString = gson.toJson(dataItem);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    static List<Note> importFromJSON(Context context) {
        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(FILE_NAME);
            streamReader = new InputStreamReader(fileInputStream);
            dataItem = gson.fromJson(streamReader, DataItem.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataItem.getNotes();
    }

    private static class DataItem {
        private List<Note> notes;

        List<Note> getNotes() {
            return notes;
        }
        void setNotes(List<Note> notes) {
            this.notes = notes;
        }
    }
}*/


