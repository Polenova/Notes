package ru.android.polenova;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends BaseAdapter {

    private List<Note> notesList;
    private LayoutInflater layoutInFlater;
    Context myContext;
    private String textName;
    private String textBody;
    private String deadLineDate;
    private String textDateOfCreate;
    private boolean checkIsChecked;

    private Date dateDeadLineParse;
    private Date dateNowParse;

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private SimpleDateFormat format1 = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
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
            if ("".equals(textName)&&notePosition.getDeadLineDate() == null) {
                textViewBody.setVisibility(View.VISIBLE);
            } else {
                textViewBody.setVisibility(View.GONE);
            }
        } else {
            textViewBody.setVisibility(View.VISIBLE);
            textViewBody.setText(textBody);
        }
        textViewBody.setText(textBody);
        if (notePosition.getDeadLineDate() == null) {
            textViewDate.setVisibility(View.GONE);
        } else {

            /*Date dateNow = new Date();
            long date = dateNow.getTime();*/
            Calendar calendar = Calendar.getInstance();
            int getCurrentDateTime = Integer.parseInt(format1.format(calendar.getTime()));
            int parseDeadLineDate = Integer.parseInt(format1.format(notePosition.getDeadLineDate()));
            if (getCurrentDateTime > parseDeadLineDate) {
                textViewDate.setTextColor(textViewDate.getResources().getColor(R.color.color_red));
            } else if (getCurrentDateTime == parseDeadLineDate) {
                textViewDate.setTextColor(textViewDate.getResources().getColor(R.color.color_yellow));
            }
            /*if (dateNow.getTime() >= notePosition.getDeadLineDate().getTime()) {
               int colorRed = textViewDate.getResources().getColor(R.color.color_red);
               textViewDate.setTextColor(colorRed);
            }*/
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
}
