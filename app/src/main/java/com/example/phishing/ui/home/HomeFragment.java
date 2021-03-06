package com.example.phishing.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.phishing.R;
import com.example.phishing.ui.RequestHttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView url_count;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        String url = "https://phishing-fishing.herokuapp.com/api/phishing/count";
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        url_count = root.findViewById(R.id.url_count);
        url_count.setText("Loading...");
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
                Log.d(TAG, jsonObject.toString());
                int num = jsonObject.getInt("count");
                url_count.setText("??? "+num+" ???");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}