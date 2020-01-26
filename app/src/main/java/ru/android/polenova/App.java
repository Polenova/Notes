package ru.android.polenova;

import android.app.Application;

public class App extends Application {

    private static NoteRepository noteRepository;
    private static Keystore keystore;

    @Override
    public void onCreate() {
        super.onCreate();
        noteRepository = new FileNotes(this);
        keystore = new FilePin(this);
    }

    public static NoteRepository getNoteRepository() {
        return noteRepository;
    }

    public static Keystore getKeystore() {
        return keystore;
    }
}
