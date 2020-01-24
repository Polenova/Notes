package ru.android.polenova;

import android.app.Application;

public class App extends Application {
    private static NoteRepository noteRepository;
    private static Keystore keystore;
    @Override
    public void onCreate() {
        super.onCreate();

        noteRepository = new FileNotes();
        //keystore = new FilePin();
    }

    // Возвращаем интерфейс, а не конкретную реализацию!

    public static NoteRepository getNoteRepository() {
        return noteRepository;
    }
    public static Keystore getKeystore() {
        return keystore;
    }
}
