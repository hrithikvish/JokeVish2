package com.example.jokevish2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("select * from notesTable")
    List<Note> getAllNotes();

    @Insert
    void addNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

}
