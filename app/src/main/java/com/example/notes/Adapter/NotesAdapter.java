package com.example.notes.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.DataBase.Note;
import com.example.notes.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{

    List<Note> list;

    public NotesAdapter(List<Note> list) {
        this.list = list;
    }


    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes, parent, false);
        NotesViewHolder viewHolder = new NotesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NotesViewHolder viewHolder, int position) {
        Note note = list.get(position);
        viewHolder.bind(note);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;


        public NotesViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleNotes);
        }


        public void bind(Note note) {
            titleTextView.setText(note.getTitle());
        }

    }
}


