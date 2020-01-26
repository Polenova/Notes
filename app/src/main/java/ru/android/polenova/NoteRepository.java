package ru.android.polenova;

import android.content.Context;

import java.util.List;

public interface NoteRepository {
    Note getNoteById(String id);
    List<Note> getNotes(Context context);
    void saveNote(Context context, Note note);
    void deleteById(Context context, Note note);
}
