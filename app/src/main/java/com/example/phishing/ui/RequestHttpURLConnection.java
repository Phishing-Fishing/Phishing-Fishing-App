package com.example.phishing.ui;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class RequestHttpURLConnection {
    public String request(String _url, String _param) {
        HttpURLConnection urlConn = null;

        try{
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();

            urlConn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
            urlConn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");

            if (_param != null) {
                urlConn.setRequestMethod("POST"); // URL 요청에 대한 메소드 설정 : POST.
                urlConn.setRequestProperty("Content-Type","application/json");
                String params = "{ \"url\" : \""+_param+"\" }";

                Log.d(TAG, "request : "+params);
                OutputStream os = urlConn.getOutputStream();
                os.write(params.getBytes("UTF-8"));
                os.flush();
                os.close();
            } else {
                urlConn.setRequestMethod("GET"); // URL 요청에 대한 메소드 설정 : GET.
            }

            Log.d(TAG, "response : "+urlConn.getResponseCode());

            // 요청한 URL의 출력물을 BufferedReader로 받는다.
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

            String line;
            String page = "";

            // 라인을 받아와 합친다.
            while ((line = reader.readLine()) != null){
                page += line;
            }
            return page;

        } catch (MalformedURLException e) { // for URL.
            e.printStackTrace();
        } catch (IOException e) { // for openConnection().
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }
        return null;

    }
}
