package ru.android.polenova;

public class Note {

    private String textNameNote;
    private String textBodyNote;
    private String textDateNote;

    public Note(String textNameNote, String textBodyNote, String textDateNote) {
        this.textNameNote = textNameNote;
        this.textBodyNote = textBodyNote;
        this.textDateNote = textDateNote;
    }

    public String getTextNameNote() {
        return textNameNote;
    }

    public void setTextNameNote(String textNameNote) {
        this.textNameNote = textNameNote;
    }

    public String getTextBodyNote() {
        return textBodyNote;
    }

    public void setTextBodyNote(String textBodyNote) {
        this.textBodyNote = textBodyNote;
    }

    public String getTextDateNote() {
        return textDateNote;
    }

    public void setTextDateNote(String textDateNote) {
        this.textDateNote = textDateNote;
    }
}
