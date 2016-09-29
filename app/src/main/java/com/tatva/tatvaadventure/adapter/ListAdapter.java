package com.tatva.tatvaadventure.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.tatva.tatvaadventure.model.EventDetail;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<EventDetail> {

    public ListAdapter(Activity activity, ArrayList<EventDetail> list) {
        super(activity,android.R.layout.simple_list_item_1, list);
    }
}
