package com.example.jokevish2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jokevish2.databinding.ActivityAddNoteBinding;

public class AddNote extends AppCompatActivity {

    ActivityAddNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        binding.viewNoteText.setOnClickListener(view -> {
            finish();
        });

        binding.saveNoteBtn.setOnClickListener(view -> {
            String noteText = String.valueOf(binding.noteInput.getText());

            databaseHelper.noteDao().addNote(
                    new Note(noteText)
            );

            Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
        });

    }
}