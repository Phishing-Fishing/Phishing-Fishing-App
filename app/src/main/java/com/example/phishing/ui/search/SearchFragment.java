package com.example.phishing.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.phishing.R;

import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private List<String> items = Arrays.asList("텍스트1", "텍스트2");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        SearchView search = root.findViewById(R.id.search_view);
        TextView resultText = root.findViewById(R.id.search_text);
        resultText.setText(getResult());

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                resultText.setText(search(newText));
                return false;
            }
        });

        return root;
    }

    private String search(String query) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<items.size();i++) {
            String item = items.get(i);
            if (item.toLowerCase().contains(query.toLowerCase())) {
                sb.append(item);
                if (i != items.size()-1) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    private String getResult() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<items.size();i++) {
            String item = items.get(i);
            sb.append(item);
            if (i != items.size()-1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}