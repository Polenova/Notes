package ru.android.polenova;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.UUID;

public class NoteFactory {
    private NoteFactory() {
    }
    public static Note createNote(@Nullable String textNameNote, @Nullable String textBodyNote, boolean checked, @Nullable Date deadLineDate) {
        return new Note(
                UUID.randomUUID().toString(),
                textNameNote,
                textBodyNote,
                checked,
                new Date(),
                deadLineDate
        );
    }
}
