package ru.android.polenova;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends BaseAdapter {

    private List<Note> notesList;
    private LayoutInflater layoutInFlater;
    Context myContext;

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
        notesList.remove(position);
        /*ArrayList<String> notes = new ArrayList<>();
        for (Note cnt : notesList) {
            notes.add(cnt.getTextNameNote());
            notes.add(cnt.getTextBodyNote());
            notes.add(cnt.getTextDateNote());
        }
        try {
            FileNotes.rewriteTextFile(myContext, notes);
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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
        Note notePosition = getNote(position);
        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewBody = view.findViewById(R.id.textViewBody);
        TextView textViewDate = view.findViewById(R.id.textViewDate);
        //CardView cardView = view.findViewById(R.id.CardVeiw);
        textViewName.setText(notePosition.getTextNameNote());
        textViewBody.setText(notePosition.getTextBodyNote());
        textViewDate.setText(notePosition.getTextDateNote());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myContext, getNote(position).getTextBodyNote(), Toast.LENGTH_SHORT).show();
                removeItem(position);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                removeItem(position);
                return false;
            }
        });
        return view;
    }
}
