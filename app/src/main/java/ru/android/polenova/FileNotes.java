package ru.android.polenova;

import android.content.Context;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.io.*;

import com.google.gson.Gson;


public class FileNotes {

    private static final String FILE_NAME = "notes.json";
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
}


