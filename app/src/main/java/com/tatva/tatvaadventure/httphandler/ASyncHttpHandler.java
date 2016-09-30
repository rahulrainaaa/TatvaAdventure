package com.tatva.tatvaadventure.httphandler;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ASyncHttpHandler extends AsyncTask<String, String, String> {

    private HttpCallback callback = null;
    private JSONObject jsonRequest = null;
    private String url = null;
    private int STATUS_CODE = -1;
    private int tag = -1;
    private String STATUS_MESSAGE = null;

    public ASyncHttpHandler(HttpCallback callback, String url, JSONObject jsonRequest, int tag) {
        this.callback = callback;
        this.jsonRequest = jsonRequest;
        this.tag = tag;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            //Create HttpURLConnection and set request properties
            URL url = new URL(this.url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");

            //Create output stream and write data-request
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonRequest.toString());
            writer.flush();
            writer.close();
            os.close();

            //Open and check the connectivity
            urlConnection.connect();
            STATUS_CODE = urlConnection.getResponseCode();
            STATUS_MESSAGE = urlConnection.getResponseMessage();
            if (STATUS_CODE == 200) {
                InputStream it = new BufferedInputStream(urlConnection.getInputStream());
                InputStreamReader read = new InputStreamReader(it);
                BufferedReader buff = new BufferedReader(read);
                StringBuilder dta = new StringBuilder();
                String chunks;
                while ((chunks = buff.readLine()) != null) {
                    dta.append(chunks);
                }
                return dta.toString();
            } else {
                return null;
            }

        } catch (Exception e) {
            STATUS_CODE = -1;
            STATUS_MESSAGE = "Connection Failed";
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {

        if (STATUS_CODE == 200) {
            //SUCCESS
            if (callback != null) {
                callback.onHttpSuccess(STATUS_CODE, STATUS_MESSAGE, s.trim(), tag);
            } else {
                Log.d("ASyncHttpHandler", "SUCCESS, But httpCallback is null");
            }
        } else if (STATUS_CODE == -1) {
            //ERROR
            if (callback != null) {
                callback.onHttpError(tag);
            } else {
                Log.d("ASyncHttpHandler", "FAIL, But httpCallback is null");
            }
        } else {
            //FAILED
            if (callback != null) {
                callback.onHttpFail(STATUS_CODE, STATUS_MESSAGE, tag);
            } else {
                Log.d("ASyncHttpHandler", "ERROR-EXCEPTION, But httpCallback is null");
            }
        }
        super.onPostExecute(s);
    }

    /**
     * @method destroyAll
     * @desc Close, Remove, null the objects
     */
    private void destroyAll() {
        callback = null;
        jsonRequest = null;
        STATUS_MESSAGE = null;
    }

    @Override
    protected void finalize() throws Throwable {
        destroyAll();
        super.finalize();
    }
}
