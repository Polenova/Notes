package ru.android.polenova;

import android.content.Context;

import java.util.List;

public interface NoteRepository {
    Note getNoteById(String id);
    List<Note> getNotes();
    void saveNote(Note note);
    void deleteById(Note note);
}
