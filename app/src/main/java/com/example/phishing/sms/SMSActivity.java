package com.example.phishing.sms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phishing.R;
import com.example.phishing.ui.RequestHttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class SMSActivity extends AppCompatActivity {

    private String getUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        setTitle("Phishing fishing");

        Log.d(TAG, "In SMS Activity");

        Intent passedIntent = getIntent();
        processIntent(passedIntent);
    }

    private void processIntent(Intent intent){
        if(intent != null){
            getUrl = intent.getStringExtra("url");
            String url = "/api";

            NetworkTask networkTask = new NetworkTask(url, getUrl);
            networkTask.execute();
        }
    }

    // 이미 실행되고 있던 경우
    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
        super.onNewIntent(intent);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(SMSActivity.this);

            try {
                jsonObject = new JSONObject(json);
                int result = jsonObject.getInt("phishing");
                if (result == 1) {
                    builder.setTitle("Phishing fishing에 의해 해당 URL은 악성 URL로 판별되었습니다.").setMessage(getUrl);
                } else {
                    builder.setTitle("Phishing fishing에 의해 해당 URL은 악성 URL이 아닌 것으로 판별되었습니다.").setMessage(getUrl);
                }

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                        System.exit(0);
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
