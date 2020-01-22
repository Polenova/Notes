package ru.android.polenova;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Note implements Serializable {

    private String textNameNote;
    private String textBodyNote;
    private String textDateNote;
    private boolean checkIsChecked;
    private Long date;

    public Note(String textNameNote, String textBodyNote, String textDateNote, boolean checkIsChecked) {
        this.textNameNote = textNameNote;
        this.textBodyNote = textBodyNote;
        this.textDateNote = textDateNote;
        this.checkIsChecked = checkIsChecked;
        date = new Date().getTime();
    }

    public Note(String textNameNote, String textBodyNote, String textDateNote, boolean checkIsChecked, Long date) {
        this.textNameNote = textNameNote;
        this.textBodyNote = textBodyNote;
        this.textDateNote = textDateNote;
        this.checkIsChecked = checkIsChecked;
        this.date = date;
    }

    public String getTextNameNote() {
        return textNameNote;
    }

    public boolean isCheckIsChecked() {
        return checkIsChecked;
    }

    public void setCheckIsChecked(boolean checkIsChecked) {
        this.checkIsChecked = checkIsChecked;
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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(textNameNote, note.textNameNote) &&
                Objects.equals(textBodyNote, note.textBodyNote) &&
                Objects.equals(textDateNote, note.textDateNote) &&
                Objects.equals(checkIsChecked, note.checkIsChecked) &&
                Objects.equals(date, note.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textNameNote, textBodyNote, textDateNote, date);
    }

    @Override
    public String toString() {
        return "Note{" +
                "textNameNote='" + textNameNote + '\'' +
                ", textBodyNote='" + textBodyNote + '\'' +
                ", textDateNote='" + textDateNote + '\'' +
                ", date=" + date +
                '}';
    }
}
