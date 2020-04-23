package com.example.bazarshodai;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent,false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.textView_Title.setText(currentNote.getTitle());
        holder.textView_Amount.setText(String.valueOf(currentNote.getAmount()));
        holder.textView_AmountType.setText(currentNote.getAmountType());
        holder.textView_Description.setText(currentNote.getDescription());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    //This function will be called in Observe function in HomeActivity
    public void setNotes(List<Note> notes){
        this.notes = notes;
        //It will notify RecyclerView but it is not efficient
        //There are more functions like notifyItemInserted(),notifyItemRemoved() etc
        //They also can hold animations but this notifyDataSetChanged() can not
        //I will use them later
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position){
        return notes.get(position);
    }

    public class NoteHolder extends RecyclerView.ViewHolder{

        private TextView textView_Title;
        private TextView textView_Amount;
        private TextView textView_AmountType;
        private TextView textView_Description;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textView_Title = itemView.findViewById(R.id.title);
            textView_Amount = itemView.findViewById(R.id.amount);
            textView_AmountType = itemView.findViewById(R.id.amount_type);
            textView_Description = itemView.findViewById(R.id.description);
        }
    }
}
