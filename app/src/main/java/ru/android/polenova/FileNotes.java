package ru.android.polenova;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileNotes {
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getTextFile(Context context, boolean newFile) throws IOException {
        File file = new File(context.getExternalFilesDir(null), "notes");
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
                writer.write(text + "\n");
            }
            writer.flush();
        }
    }
}
