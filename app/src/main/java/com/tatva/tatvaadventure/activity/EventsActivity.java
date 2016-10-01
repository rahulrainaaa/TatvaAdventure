package com.tatva.tatvaadventure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tatva.tatvaadventure.R;
import com.tatva.tatvaadventure.adapter.ListAdapter;
import com.tatva.tatvaadventure.httphandler.ASyncHttpHandler;
import com.tatva.tatvaadventure.httphandler.HttpCallback;
import com.tatva.tatvaadventure.model.EventDetail;
import com.tatva.tatvaadventure.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, HttpCallback {

    private ListView listView = null;
    ArrayList<EventDetail> list = new ArrayList<EventDetail>();
    ListAdapter adapter = null;
    ImageView img = null;
    ProgressBar progress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        listView = (ListView) findViewById(R.id.listView);
        img = (ImageView)findViewById(R.id.img);
        progress = (ProgressBar)findViewById(R.id.process);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        adapter = new ListAdapter(this, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        refreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        refreshList();
        return true;
    }

                @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Constants.eventDetail = list.get(position);
        startActivity(new Intent(this, DetailActivity.class));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        Snackbar.make(view, "Event on: " + list.get(position).getTime(), Snackbar.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onHttpSuccess(int statusCode, String statusMessage, String data, int tag) {
        progress.setVisibility(View.GONE);
        img.setVisibility(View.GONE);
        try {
            JSONObject json = new JSONObject(data);
            int code = json.getInt("statusCode");
            String msg = json.getString("statusMessage");
            //Check for response
            switch (code) {
                case 200:       // OK
                    parseData(json.getJSONArray("data"));//parse data
                    break;
                case 204:       //No data
                    Toast.makeText(EventsActivity.this, "No events", Toast.LENGTH_SHORT).show();
                    break;
                case 400:       //bad request
                    Toast.makeText(EventsActivity.this, code + ". " + msg, Toast.LENGTH_SHORT).show();
                    break;
                case 401:       //Auth fail
                    Toast.makeText(EventsActivity.this, code + ". " + msg, Toast.LENGTH_SHORT).show();
                    break;
                case 406:       //SQL integrity voilation exception
                    Toast.makeText(EventsActivity.this, code + ". " + msg, Toast.LENGTH_SHORT).show();
                    break;
                case 500:       //Server internal error
                    Toast.makeText(EventsActivity.this, code + ". " + msg, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(EventsActivity.this, "Unknown Response", Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Application Internal Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onHttpFail(int statusCode, String statusMessage, int tag) {
        Toast.makeText(EventsActivity.this, statusCode + ": " + statusMessage, Toast.LENGTH_SHORT).show();
        progress.setVisibility(View.GONE);
        img.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHttpError(int tag) {
        Toast.makeText(EventsActivity.this, "Server Connection Fail", Toast.LENGTH_SHORT).show();
        progress.setVisibility(View.GONE);
        img.setVisibility(View.VISIBLE);
    }

    /**
     * @method refreshList
     * @desc Method to Call Web-service for list
     */
    private void refreshList()
    {
        progress.setVisibility(View.VISIBLE);
        img.setVisibility(View.GONE);
        try {
            JSONObject json = new JSONObject();
            json.put("auth", Constants.AUTH_GET);
            ASyncHttpHandler httpHandler = new ASyncHttpHandler(this, Constants.URL_GET, json, 1);
            httpHandler.execute("");
        } catch (Exception e) {
            Toast.makeText(EventsActivity.this, "Application crashing internally", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @method: parseData
     * @desc Parse HTTP Data into listView
     */
    private void parseData(JSONArray array) {
        int len = array.length();
        try
        {
            list.clear();
            for (int i = 0; i < len; i++) {
                EventDetail eventDetail = new EventDetail();
                eventDetail.setId(array.getJSONObject(i).getInt("id"));
                eventDetail.setPlace(array.getJSONObject(i).getString("place"));
                eventDetail.setTime(array.getJSONObject(i).getString("time"));
                eventDetail.setTitle(array.getJSONObject(i).getString("title"));
                //eventDetail.setTitle(array.getJSONObject(i).getString("description"));
                list.add(eventDetail);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(EventsActivity.this, "Event array parse exception", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }
}
