package com.example.jokevish2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.jokevish2.databinding.FragmentQuoteBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class QuoteFragment extends Fragment {
    FragmentQuoteBinding binding;
    Random random = new Random();
    RequestQueue queue;
    ArrayList<String> quotesArrayList = new ArrayList<>();
    ArrayList<String> authorsArrayList = new ArrayList<>();
    int currQuoteIndex = -1;
    String quoteString, authorString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(requireContext());
        makeApiCall();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuoteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.nextbtn.setOnClickListener(v -> {
            if(currQuoteIndex >= quotesArrayList.size() - 1) {
                Toast.makeText(requireContext(), "No More Quotes Available", Toast.LENGTH_SHORT).show();
            }
            else {
                ++currQuoteIndex;
                String nextQuote = quotesArrayList.get(currQuoteIndex);
                String nextAuthor = authorsArrayList.get(currQuoteIndex);
                binding.quotetext.setText(nextQuote);
                binding.authortext.setText(nextAuthor);
            }
        });

        binding.previousbtn.setOnClickListener(v -> {
            if(currQuoteIndex > 0) {
                --currQuoteIndex;
                String previousQuote = quotesArrayList.get(currQuoteIndex);
                String previousAuthor = authorsArrayList.get(currQuoteIndex);
                binding.quotetext.setText(previousQuote);
                binding.authortext.setText(previousAuthor);
            }
            else {
                Toast.makeText(requireContext(), "No Previous Quotes Available", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void makeApiCall() {
        String url = "https://type.fit/api/quotes";JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            try {
                for(int i = 0; i < response.length(); i++) {
                    JSONObject quoteData = response.getJSONObject(random.nextInt(response.length()));
                    quoteString = quoteData.getString("text");
                    authorString = quoteData.getString("author");
                    quotesArrayList.add(quoteString);
                    authorsArrayList.add(authorString);
                }

                ++currQuoteIndex;
                binding.quotetext.setText(quotesArrayList.get(currQuoteIndex));
                if (authorsArrayList.get(currQuoteIndex).equals("null")) {
                    binding.authortext.setText("Unknown Author");
                }
                else {
                    binding.authortext.setText(authorsArrayList.get(currQuoteIndex));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        queue.add(jsonArrayRequest);
    }

}