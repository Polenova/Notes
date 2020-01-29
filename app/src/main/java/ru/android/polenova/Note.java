package ru.android.polenova;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Note implements Serializable {

    @NonNull
    private String id;
    @Nullable
    private String textNameNote;
    @Nullable
    private String textBodyNote;
    private boolean checked;
    @NonNull
    private Date lastModifiedDate;
    @Nullable
    private Date deadLineDate;
    public Note(@NonNull String id, @Nullable String textNameNote, @Nullable String textBodyNote, boolean checked, @NonNull Date lastModifiedDate, @Nullable Date deadLineDate) {
        this.id = id;
        this.textNameNote = textNameNote;
        this.textBodyNote = textBodyNote;
        this.checked = checked;
        this.lastModifiedDate = lastModifiedDate;
        this.deadLineDate = deadLineDate;
    }
    @NonNull
    public String getId() {
        return id;
    }
    @Nullable
    public String getTextNameNote() {
        return textNameNote;
    }
    @Nullable
    public String getTextBodyNote() {
        return textBodyNote;
    }
    public boolean isChecked() {
        return checked;
    }
    @NonNull
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
    @Nullable
    public Date getDeadLineDate() {
        return deadLineDate;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return checked == note.checked &&
                id.equals(note.id) &&
                Objects.equals(textNameNote, note.textNameNote) &&
                Objects.equals(textBodyNote, note.textBodyNote) &&
                lastModifiedDate.equals(note.lastModifiedDate) &&
                Objects.equals(deadLineDate, note.deadLineDate);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, textNameNote, textBodyNote, checked, lastModifiedDate, deadLineDate);
    }
}
