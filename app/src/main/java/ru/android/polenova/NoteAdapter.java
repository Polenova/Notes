package ru.android.polenova;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends BaseAdapter {

    private List<Note> notesList;
    private LayoutInflater layoutInFlater;
    Context myContext;
    private String textName;
    private String textBody;

    private SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());
    private NoteRepository noteRepository = App.getNoteRepository();

    NoteAdapter(Context context, List<Note> notesList) {
        myContext = context;
        this.notesList = notesList;
        layoutInFlater = layoutInFlater.from(context);
    }

    void addItem(Note item) {
        this.notesList.add(item);
        notifyDataSetChanged();
    }

    void removeItem(int position) {
        Note note = notesList.get(position);
        notesList.remove(position);
        try {
            noteRepository.deleteById(note);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return notesList.size();
    }

    @Override
    public Object getItem(int position) {
        return notesList.get(position);
    }

    public Note getNote(int position) {
        return notesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInFlater.inflate(R.layout.list_note, parent, false);
        }
        final Note notePosition = getNote(position);
        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewBody = view.findViewById(R.id.textViewBody);
        TextView textViewDate = view.findViewById(R.id.textViewDate);
        textName = notePosition.getTextNameNote();
        textBody = notePosition.getTextBodyNote();
        if ("".equals(textName)) {
            textViewName.setVisibility(View.GONE);
        } else {
            textViewName.setVisibility(View.VISIBLE);
            textViewName.setText(textName);
        }
        if ("".equals(textBody)) {
            textViewBody.setVisibility(View.GONE);
        } else {
            textViewBody.setVisibility(View.VISIBLE);
            textViewBody.setText(textBody);
        }
        textViewDate.setTextColor(textViewDate.getResources().getColor(R.color.medium_grey));
        if (notePosition.getDeadLineDate() == null) {
            textViewDate.setVisibility(View.GONE);
        } else {
            Calendar calendar = Calendar.getInstance();
            skipHours(calendar);
            // Текущий день
            long getCurrentDateTime = calendar.getTimeInMillis();
            calendar.setTimeInMillis(notePosition.getDeadLineDate().getTime());
            skipHours(calendar);
            // День из дедлайна
            long parseDeadLineDate = calendar.getTimeInMillis();
            if (getCurrentDateTime > parseDeadLineDate) {
                textViewDate.setTextColor(textViewDate.getResources().getColor(R.color.color_red));
            } else if (getCurrentDateTime == parseDeadLineDate) {
                textViewDate.setTextColor(textViewDate.getResources().getColor(R.color.color_yellow));
            }
            textViewDate.setVisibility(View.VISIBLE);
            textViewDate.setText(format.format(notePosition.getDeadLineDate()));
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note editNote = new Note(
                        notePosition.getId(),
                        notePosition.getTextNameNote(),
                        notePosition.getTextBodyNote(),
                        notePosition.isChecked(),
                        notePosition.getLastModifiedDate(),
                        notePosition.getDeadLineDate()
                );
                Intent intent = new Intent(myContext, NewNoteActivity.class);
                intent.putExtra(Note.class.getSimpleName(), editNote);
                myContext.startActivity(intent);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                builder.setTitle(R.string.dialog_delete)
                        .setMessage(notePosition.getTextNameNote())
                        .setIcon(R.drawable.ic_delete_forever_black_24dp)
                        .setCancelable(false)
                        .setPositiveButton(R.string.dialog_OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeItem(position);
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return false;
            }
        });
        return view;
    }

    private void skipHours(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}
