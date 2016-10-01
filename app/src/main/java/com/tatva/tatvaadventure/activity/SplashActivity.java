package com.tatva.tatvaadventure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.tatva.tatvaadventure.R;
import com.tatva.tatvaadventure.httphandler.ASyncHttpHandler;
import com.tatva.tatvaadventure.httphandler.HttpCallback;
import com.tatva.tatvaadventure.utils.CacheHandler;
import com.tatva.tatvaadventure.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity implements HttpCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViewById(R.id.idd).setSelected(true);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if (refreshedToken == null) {   //No GCM ID

        } else if (CacheHandler.getInstance().getCache(this, Constants.CACHE_REG, 0) == 0) { //Not registered
            JSONObject jsonRequest = new JSONObject();

            try {
                jsonRequest.put("auth", Constants.AUTH_REG);
                jsonRequest.put("devid", refreshedToken);
                ASyncHttpHandler httpHandler = new ASyncHttpHandler(this, Constants.URL_REG, jsonRequest, 1);
                httpHandler.execute("");
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(SplashActivity.this, "HTTP Request packing error", Toast.LENGTH_SHORT).show();
            }
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, EventsActivity.class));
                    SplashActivity.this.finish();
                }
            }, 1200);
        }
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
                    Toast.makeText(SplashActivity.this, "Device Registered Successful", Toast.LENGTH_SHORT).show();
                    break;
                case 401:       // Auth Fail
                    Toast.makeText(SplashActivity.this, code + ": " + msg, Toast.LENGTH_SHORT).show();
                    break;
                case 400:       // Bad Request
                    Toast.makeText(SplashActivity.this, code + ": " + msg, Toast.LENGTH_SHORT).show();
                    break;
                case 406:       // Already Exist - Not Acceptable
                    CacheHandler.getInstance().setCache(this, Constants.CACHE_REG, 1);
                    Toast.makeText(SplashActivity.this, "Device Already Registered", Toast.LENGTH_SHORT).show();
                    break;
                case 500:       // Server Internal Error
                    Toast.makeText(SplashActivity.this, code + ": " + msg, Toast.LENGTH_SHORT).show();
                    break;
                default:        //any other case
                    Toast.makeText(SplashActivity.this, "Unknown Response Code", Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(SplashActivity.this, "Application Internal Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        startActivity(new Intent(this, EventsActivity.class));
        finish();
    }

    @Override
    public void onHttpFail(int statusCode, String statusMessage, int tag) {
        Toast.makeText(SplashActivity.this, statusMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHttpError(int tag) {
        Toast.makeText(SplashActivity.this, "Fail Server Connection", Toast.LENGTH_SHORT).show();
    }
}
