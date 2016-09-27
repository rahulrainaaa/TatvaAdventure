package com.tatva.tatvaadventure.httphandler;


import android.content.Context;
import android.os.AsyncTask;

import com.tatva.tatvaadventure.utils.Constants;

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

    private Context context;
    private JSONObject jsonRequest;


    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            //Create HttpURLConnection and set request properties
            URL url = new URL(Constants.REGISTER_DEVICE);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");

            //Create output stream and write data-request
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonRequest.toString()); // should be fine if my getQuery is encoded right yes?
            writer.flush();
            writer.close();
            os.close();

            //Open and check the connectivity
            urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();
            String str = urlConnection.getResponseMessage();
            if (statusCode == 200) {
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
                return "ERROR";
            }

        } catch (Exception e) {
            return "EXCEPTION";
        }

    }

    @Override
    protected void onPostExecute(String s) {


        super.onPostExecute("");
    }
}
