package com.example.phishing.ui.add;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.phishing.R;
import com.example.phishing.ui.RequestHttpURLConnection;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class AddFragment extends Fragment {

    private AddViewModel addViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add, container, false);

        String url = "http://127.0.0.1/addUrl";

        TextInputEditText text_url = root.findViewById(R.id.url_add);
        Button url_add = (Button)root.findViewById(R.id.btn_url_add);
        url_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues params = new ContentValues();
                params.put("url", text_url.getText().toString());

                NetworkTask networkTask = new NetworkTask(url, params);
                networkTask.execute();
            }
        });
        return root;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}