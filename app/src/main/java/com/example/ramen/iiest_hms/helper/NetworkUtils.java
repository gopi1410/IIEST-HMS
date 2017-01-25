package com.example.ramen.iiest_hms.helper;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtils {

    private static URL urlObj;
    private static HttpURLConnection conn;
    private static DataOutputStream wr;
    private static StringBuilder result;

    public static String httpPostRequest(String url, String params) {
        try {
            urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            wr = new DataOutputStream(conn.getOutputStream());
            wr.write(params.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader read = new BufferedReader(new InputStreamReader(in));

            result = new StringBuilder();
            String line;
            while ((line = read.readLine()) != null) {
                result.append(line);
            }

            int maxLogSize = 1000;
            for (int i = 0; i <= result.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > result.length() ? result.length() : end;
                Log.v("logged in: ", result.substring(start, end));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        return result.toString();
    }

    public static String okHttpPostRequest(String url, String params){
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "sid=510814041&password=rvprerna&type=2&login=");
        Request request = new Request.Builder()
                .url("http://iiesthostel.iiests.ac.in/students/login_students")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "bbd9c3f0-eae3-5fa7-eb63-f712c41861f9")
                .build();

        try {
            String response = client.newCall(request).execute().body().string();
            int maxLogSize = 1000;
            for (int i = 0; i <= response.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > response.length() ? response.length() : end;
                Log.v("logged in: ", response.substring(start, end));
            }
            return response;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
