package com.example.jokevish2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jokevish2.databinding.FragmentRoomBinding;

import java.util.ArrayList;

public class RoomFragment extends Fragment {

    FragmentRoomBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        databaseHelper = DatabaseHelper.getDB(requireContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void refreshFragment(Fragment fragment) {
        FragmentManager fragmentManager  = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRoomBinding.inflate(inflater, container, false);

        binding.addNoteBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AddNote.class);
            startActivity(intent);
        });

        binding.refreshNoteBtn.setOnClickListener(view -> {
            refreshFragment(new RoomFragment());
        });

        ArrayList<Note> notesArr = (ArrayList<Note>) databaseHelper.noteDao().getAllNotes();

        NoteAdapter adapter = new NoteAdapter(notesArr, getContext());
        binding.notesList.setAdapter(adapter);

        return binding.getRoot();
    }
}