package com.example.phishing.ui.search;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.phishing.R;
import com.example.phishing.ui.RequestHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private TextView resultText;
    private List<String> items = new ArrayList<>(Arrays.asList("Loading..."));

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        String url = "/api/phishing/database";
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        SearchView search = root.findViewById(R.id.search_view);
        resultText = root.findViewById(R.id.search_text);
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

    private class NetworkTask extends AsyncTask<Integer, Void, String> {

        private String url;
        private String values;

        public NetworkTask(String url, String values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Integer... integers) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("phishingList");

                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject data = jsonArray.getJSONObject(i);
                    items.add(data.getString("url"));
                }

                resultText.setText(getResult());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String search(String query) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<items.size();i++) {
            String item = items.get(i);
            if (item.toLowerCase().contains(query.toLowerCase())) {
                sb.append(item);
                if (i != items.size()-1) {
                    sb.append("\n\n");
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
                sb.append("\n\n");
            }
        }
        return sb.toString();
    }
}