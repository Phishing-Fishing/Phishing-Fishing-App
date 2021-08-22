package com.example.phishing.ui.add;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.phishing.R;
import com.example.phishing.ui.RequestHttpURLConnection;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;


public class AddFragment extends Fragment {

    private AddViewModel addViewModel;
    private boolean isSuccess = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add, container, false);

        String url = "/api/phishing/register";

        TextInputEditText text_url = root.findViewById(R.id.url_add);
        Button url_add = (Button)root.findViewById(R.id.btn_url_add);
        url_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String param = text_url.getText().toString();

                NetworkTask networkTask = new NetworkTask(url, param);
                networkTask.execute();
            }
        });
        return root;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private String values;

        public NetworkTask(String url, String values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(s);

                int check = jsonObject.getInt("success");
                if (check == 1) {
                    Toast.makeText(getActivity(), "URL 등록이 완료되었습니다.", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getActivity(), "URL 등록이 실패했습니다.", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}