package ru.android.polenova;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends BaseAdapter {

    private List<Note> notesList;
    private LayoutInflater layoutInFlater;
    Context myContext;
    private String textName;
    private String textBody;
    private String textDate;
    private String textDateOfCreate;
    private boolean checkIsChecked;

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
            FileNotes.remove(myContext, note);
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
        textDate = notePosition.getTextDateNote();
        checkIsChecked = notePosition.isCheckIsChecked();
        if ("".equals(textName)) {
            textViewName.setVisibility(View.GONE);
        } else {
            textViewName.setText(textName);
        }
        textViewBody.setText(textBody);
        if ("".equals(textDate)) {
            textViewDate.setVisibility(View.GONE);
        } else {
            SimpleDateFormat dateNow = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String dateNowString = dateNow.format(new Date());
            int dateNowInt = Integer.parseInt(dateNowString.replace(".", ""));
            int dateInt = Integer.parseInt(textDate.replace(".", ""));
            if (dateNowInt >= dateInt) {
                textViewDate.setBackgroundResource(R.color.colorBackgroundRed);
            } else if (dateInt <= dateNowInt + 2000000) {
                textViewDate.setBackgroundResource(R.color.colorBackgroundYellow);
            }
            textViewDate.setText(textDate);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note editNote = new Note(notePosition.getTextNameNote(), notePosition.getTextBodyNote(),
                        notePosition.getTextDateNote(), notePosition.isCheckIsChecked(), notePosition.getDate());
                Intent intent = new Intent(myContext, NewNoteActivity.class);
                intent.putExtra(Note.class.getSimpleName(), editNote);
                myContext.startActivity(intent);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                builder.setTitle("Удалить данные?")
                        .setMessage(notePosition.getTextNameNote())
                        .setIcon(R.drawable.ic_delete_forever_black_24dp)
                        .setCancelable(false)
                        .setPositiveButton("удалить", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeItem(position);
                            }
                        })
                        .setNegativeButton("отмена", new DialogInterface.OnClickListener() {
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
}
