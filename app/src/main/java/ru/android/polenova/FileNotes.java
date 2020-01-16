package ru.android.polenova;

import android.content.Context;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class FileNotes {

    private static final String FILE_NAME = "notes.json";

    static boolean exportToJSON(Context context, List<Note> notesList) {
        Gson gson = new Gson();
        DataItem dataItem = new DataItem();
        dataItem.setNotes(notesList);
        String jsonString = gson.toJson(dataItem);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
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
        return false;
    }

    static List<Note> importFromJSON(Context context) {

        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(FILE_NAME);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            DataItem dataItem = gson.fromJson(streamReader, DataItem.class);
            return dataItem.getNotes();
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
        return null;
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


    /*public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getTextFile(Context context, boolean newFile) throws IOException {
        File file = new File(context.getExternalFilesDir(null), FILE_NAME);
        if (!file.exists()) {
            file.createNewFile();
        } else if (newFile) {
            file.delete();
            file.createNewFile();
        }
        return file;
    }

    public static void appendTextFile(Context context, ArrayList<String> arrayList) throws IOException {
        updateTextFile(context, arrayList, true);
    }

    public static void rewriteTextFile(Context context, ArrayList<String> arrayList) throws IOException {
        updateTextFile(context, arrayList, false);
    }

    public static void updateTextFile(Context context, ArrayList<String> arrayList, boolean append) throws IOException {
        try (FileWriter writer = new FileWriter(getTextFile(context, false), append)) {
            for (String text : arrayList) {
                writer.write(text);
            }
            writer.flush();
        }
    }
}*/
