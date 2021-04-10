package com.example.notes.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.DataBase.Note;
import com.example.notes.MainActivity;
import com.example.notes.R;
import com.example.notes.Repository;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> list;
    private OnItemClickListener listener;
    private ImageView deleting;
    private OnDeleteClickListener onDeleteClickListener;

    public NotesAdapter(List<Note> list,OnDeleteClickListener onDeleteClickListener, OnItemClickListener listener) {
        this.list = list;
        this.onDeleteClickListener = onDeleteClickListener;
        this.listener = listener;
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

    class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        TextView noteDate;

        public NotesViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleNotes);
            noteDate = view.findViewById(R.id.note_date);
            deleting = view.findViewById(R.id.delete);


            view.setOnClickListener(this);
        }

        public void bind(Note note) {
            titleTextView.setText(note.getTitle());
            noteDate.setText(note.getDate());
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(list.get(getAdapterPosition()));
            onDeleteClickListener.onDeleteClick(deleting);
        }
    }
    public interface OnDeleteClickListener{
        public void onDeleteClick(ImageView deleting);
    }

    public interface OnItemClickListener {
        public void onItemClick(Note note);
    }
}