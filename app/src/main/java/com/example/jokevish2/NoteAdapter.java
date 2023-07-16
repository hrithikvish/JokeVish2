package com.example.jokevish2;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.jokevish2.databinding.ActivityAddNoteBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.viewHolder>{
    ArrayList<Note> list;
    Context context;

    public NoteAdapter(ArrayList<Note> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Note note = list.get(position);
        holder.noteTextView.setText(note.getNote());

        holder.deleteNoteBtn.setVisibility(View.GONE);

        if(holder.deleteNoteBtn.getVisibility() == View.GONE) {
            holder.downBtn.setOnClickListener(view -> {
                holder.deleteNoteBtn.setVisibility(View.VISIBLE);
                holder.downBtn.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);
            });
        }
        if(holder.deleteNoteBtn.getVisibility() == View.VISIBLE) {
            holder.downBtn.setOnClickListener(view -> {
                holder.deleteNoteBtn.setVisibility(View.GONE);
                holder.downBtn.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
            });
        }

        holder.deleteNoteBtn.setOnClickListener(view -> {
            Toast.makeText(context, "Delete Button", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView noteTextView;
        Button deleteNoteBtn;
        ImageButton downBtn;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            noteTextView = itemView.findViewById(R.id.noteTextView);
            deleteNoteBtn = itemView.findViewById(R.id.deleteNoteBtn);
            downBtn = itemView.findViewById(R.id.downBtn);
        }
    }
}
