package com.example.jokevish2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jokevish2.databinding.FragmentJokeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JokeFragment extends Fragment {
    ArrayList<String> jokeArrayList = new ArrayList<>();
    int currJokeIndex = -1;
    RequestQueue queue;
    FragmentJokeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(requireContext());
        makeApiCall();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentJokeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.nextbtn.setOnClickListener(v -> {
            if(currJokeIndex == jokeArrayList.size() - 1) {
                makeApiCall();
            }
            else {
                if(currJokeIndex <= jokeArrayList.size() - 1) {
                    String nextJoke = jokeArrayList.get(++currJokeIndex);
                    binding.joketext.setText(nextJoke);
                } else {
                    makeApiCall();
                }
            }
        });

        binding.previousbtn.setOnClickListener(v -> {
            if(currJokeIndex > 0) {
                String previousJoke = jokeArrayList.get(--currJokeIndex);
                binding.joketext.setText(previousJoke);
            }
            else {
                Toast.makeText(requireContext(), "No Previous Joke Available.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeApiCall() {
        String url = "https://icanhazdadjoke.com/slack";
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("attachments");
                JSONObject attachments = jsonArray.getJSONObject(0);
                String jokeString = attachments.getString("text");
                ++currJokeIndex;
                jokeArrayList.add(jokeString);
                binding.joketext.setText(jokeString);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, Throwable::printStackTrace);
        queue.add(jsonObjRequest);
    }

}