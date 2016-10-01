package com.tatva.tatvaadventure.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tatva.tatvaadventure.R;
import com.tatva.tatvaadventure.httphandler.ASyncHttpHandler;
import com.tatva.tatvaadventure.httphandler.HttpCallback;
import com.tatva.tatvaadventure.model.EventDetail;
import com.tatva.tatvaadventure.utils.Constants;

import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity implements HttpCallback {

    TextView txtTitle, txtPlace, txtTime, txtDesc;
    ImageView img;
    EventDetail eventDetail;
    ProgressBar prgDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        eventDetail = Constants.eventDetail;
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtPlace = (TextView) findViewById(R.id.txtPlace);
        txtTime = (TextView) findViewById(R.id.txtDate);
        txtDesc = (TextView) findViewById(R.id.txtDescription);
        img = (ImageView) findViewById(R.id.imgEvent);
        prgDesc = (ProgressBar) findViewById(R.id.prgDesc);
        txtTime.setText(eventDetail.getTime());
        txtPlace.setText(eventDetail.getPlace());
        txtTitle.setText(eventDetail.getTitle());
        Linkify.addLinks(txtDesc, Linkify.WEB_URLS | Linkify.PHONE_NUMBERS | Linkify.EMAIL_ADDRESSES | Linkify.MAP_ADDRESSES);
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("id", "" + eventDetail.getId());
            jsonRequest.put("auth", Constants.AUTH_GET);
        } catch (Exception e) {
            Toast.makeText(DetailActivity.this, "Error packing request", Toast.LENGTH_SHORT).show();
        }

        if (eventDetail.getDescription() == null) {
            ASyncHttpHandler httpDesc = new ASyncHttpHandler(this, Constants.URL_GET_DESC, jsonRequest, 1);
            httpDesc.execute("");
            Toast.makeText(DetailActivity.this, "Fetching Description", Toast.LENGTH_SHORT).show();
        } else {
            prgDesc.setVisibility(View.GONE);
            txtDesc.setText(eventDetail.getDescription());
        }

        if (eventDetail.getImage() == null) {
            ASyncHttpHandler httpImage = new ASyncHttpHandler(this, Constants.URL_GET_IMG, jsonRequest, 2);
            httpImage.execute("");
            Toast.makeText(DetailActivity.this, "Fetching Image.", Toast.LENGTH_SHORT).show();
        } else {
            img.setVisibility(View.VISIBLE);
        }

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.this.finish();
            }
        });
    }

    @Override
    public void onHttpSuccess(int statusCode, String statusMessage, String data, int tag) {

        if (tag == 1) {
            prgDesc.setVisibility(View.GONE);
        } else if (tag == 2) {
            img.setVisibility(View.VISIBLE);
        }
        try {
            JSONObject json = new JSONObject(data);
            int code = json.getInt("statusCode");
            String msg = json.getString("statusMessage");
            //Check for response
            switch (code) {
                case 200:       // OK
                    if (tag == 1) {
                        parseDescription(json.getJSONObject("data").getString("description"));
                    } else if (tag == 2) {
                        parseImage(json.getJSONObject("data").getString("image"));
                    }
                    break;
                case 204:       //No data
                    Toast.makeText(this, "This event might be deleted. Please refresh list", Toast.LENGTH_LONG).show();
                    break;
                case 400:       //bad request
                    Toast.makeText(this, code + ". " + msg, Toast.LENGTH_SHORT).show();
                    break;
                case 401:       //Auth fail
                    Toast.makeText(this, code + ". " + msg, Toast.LENGTH_SHORT).show();
                    break;
                case 406:       //SQL integrity voilation exception
                    Toast.makeText(this, code + ". " + msg, Toast.LENGTH_SHORT).show();
                    break;
                case 500:       //Server internal error
                    Toast.makeText(this, code + ". " + msg, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "Unknown Response", Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Application Internal Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onHttpFail(int statusCode, String statusMessage, int tag) {
        if (tag == 1) {
            Toast.makeText(DetailActivity.this, "Failed to get Description", Toast.LENGTH_SHORT).show();
            eventDetail.setDescription(null);
            prgDesc.setVisibility(View.GONE);
        } else if (tag == 2) {
            Toast.makeText(DetailActivity.this, "Unable to fetch Image", Toast.LENGTH_SHORT).show();
            img.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onHttpError(int tag) {
        if (tag == 1) {
            eventDetail.setDescription(null);
            prgDesc.setVisibility(View.GONE);
        } else if (tag == 2) {
            eventDetail.setImage((String)null);
            img.setVisibility(View.VISIBLE);
        }
    }

    /**
     * @method parseDescription
     * @desc parse encoded description and show on UI.
     */
    private void parseDescription(String data) {
        try {
            eventDetail.setDescription(data);
            txtDesc.setText((eventDetail.getDescription() == null) ? "" : eventDetail.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Linkify.addLinks(txtDesc, Linkify.WEB_URLS | Linkify.PHONE_NUMBERS | Linkify.EMAIL_ADDRESSES | Linkify.MAP_ADDRESSES);
    }

    /**
     * @method parseImage
     * @desc parse encoded image string and show on UI.
     */
    public void parseImage(String data) {
        try {
            eventDetail.setImage(data);
            if (eventDetail.getImage() != null) {

                img.setImageBitmap(eventDetail.getImage());
            }
        } catch (Exception e) {
            Toast.makeText(DetailActivity.this, "Image Decoding Fail", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    
    @Override
    public void onBackPressed() {
        eventDetail.setImage((String) null);
        super.onBackPressed();
    }
}
