package com.neet101.project;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.util.Arrays;

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    private static String _email;
    private static String _password;

    public HttpHandler(String Email, String Password) {
        _email = Email;
        _password = Password;
    }

    public static JSONArray toJSON(String[] inputs) {
        JSONArray jsonArray = new JSONArray(Arrays.asList(inputs));

        return jsonArray;
    }

    public String makeServiceCall(String reqUrl, String Method, Context context) {
        String response = null;
        try {
            URL url = new URL(reqUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(5000);

            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            if(_email != null) {
                String credentials = _email + ":" + _password;

                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

                conn.setRequestProperty("Authorization", auth);
            }

            conn.setRequestMethod(Method);

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());

            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
