package com.tatva.tatvaadventure.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tatva.tatvaadventure.R;
import com.tatva.tatvaadventure.model.EventDetail;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<EventDetail> {

    ArrayList<EventDetail> list = null;
    Activity activity = null;
    LayoutInflater inflater = null;

    static class ViewHolder {
        public TextView txtDate;
        public TextView txtTitle;
        public TextView txtPlace;
    }

    public ListAdapter(Activity activity, ArrayList<EventDetail> list) {
        super(activity, R.layout.item_list_events, list);
        this.list = list;
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list_events, null);
            viewHolder = new ViewHolder();
            viewHolder.txtDate = (TextView) view.findViewById(R.id.txtDate);
            viewHolder.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            viewHolder.txtPlace = (TextView) view.findViewById(R.id.txtPlace);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        TextView txtDate = viewHolder.txtDate;
        TextView txtTitle = viewHolder.txtTitle;
        TextView txtPlace = viewHolder.txtPlace;

        txtDate.setText(list.get(position).getTime());
        txtTitle.setText(list.get(position).getTitle());
        txtPlace.setText(list.get(position).getPlace());

        return view;
    }
}
