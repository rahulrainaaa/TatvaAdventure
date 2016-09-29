package com.tatva.tatvaadventure.service;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tatva.tatvaadventure.activity.EventsActivity;
import com.tatva.tatvaadventure.httphandler.ASyncHttpHandler;
import com.tatva.tatvaadventure.httphandler.HttpCallback;
import com.tatva.tatvaadventure.utils.CacheHandler;
import com.tatva.tatvaadventure.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class MyInstanceIDListenerService extends FirebaseInstanceIdService implements HttpCallback {


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is also called
     * when the InstanceID token is initially generated, so this is where
     * you retrieve the token.
     */

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        CacheHandler.getInstance().setCache(this, Constants.CACHE_REG, 0);
        Handler mainHandler = new Handler(getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject jsonRequest = new JSONObject();
                    jsonRequest.put("auth", Constants.AUTH_REG);
                    jsonRequest.put("devid", refreshedToken);
                    ASyncHttpHandler httpHandler = new ASyncHttpHandler(MyInstanceIDListenerService.this, Constants.URL_REG, jsonRequest, 1);
                    httpHandler.execute("");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MyInstanceIDListenerService.this, "HTTP Request packing error", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mainHandler.post(myRunnable);


    }

    @Override
    public void onHttpSuccess(int statusCode, String statusMessage, String data, int tag) {

        try {
            JSONObject json = new JSONObject(data);
            int code = json.getInt("statusCode");
            String msg = json.getString("statusMessage");
            //Check for response
            switch (code) {
                case 200:       // OK
                    CacheHandler.getInstance().setCache(this, Constants.CACHE_REG, 1);
                    Toast.makeText(MyInstanceIDListenerService.this, "Device Registered Successful", Toast.LENGTH_SHORT).show();
                    break;
                case 401:       // Auth Fail
                    Toast.makeText(MyInstanceIDListenerService.this, code + ": " + msg, Toast.LENGTH_SHORT).show();
                    break;
                case 400:       // Bad Request
                    Toast.makeText(MyInstanceIDListenerService.this, code + ": " + msg, Toast.LENGTH_SHORT).show();
                    break;
                case 406:       // Already Exist - Not Acceptable
                    CacheHandler.getInstance().setCache(this, Constants.CACHE_REG, 1);
                    Toast.makeText(MyInstanceIDListenerService.this, "Device Already Registered", Toast.LENGTH_SHORT).show();
                    break;
                case 500:       // Server Internal Error
                    Toast.makeText(MyInstanceIDListenerService.this, code + ": " + msg, Toast.LENGTH_SHORT).show();
                    break;
                default:        //any other case
                    Toast.makeText(MyInstanceIDListenerService.this, "Unknown Response Code", Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(MyInstanceIDListenerService.this, "Application Internal Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onHttpFail(int statusCode, String statusMessage, int tag) {
        Toast.makeText(MyInstanceIDListenerService.this, statusMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHttpError(int tag) {
        Toast.makeText(MyInstanceIDListenerService.this, "Fail Server Connection", Toast.LENGTH_SHORT).show();
    }

}