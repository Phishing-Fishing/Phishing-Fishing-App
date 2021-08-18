package com.example.phishing.sms;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.phishing.MainActivity;
import com.example.phishing.R;

import static android.content.ContentValues.TAG;

public class SMSActivity extends AppCompatActivity {

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
            String string = intent.getStringExtra("url");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Phishing fishing에 의해 해당 URL은 악성 URL로 판별되었습니다.").setMessage(string);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                    System.exit(0);
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    // 이미 실행되고 있던 경우
    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
        super.onNewIntent(intent);
    }
}
