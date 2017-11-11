package com.darksidehacks.delhimetropathfinder.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.darksidehacks.delhimetropathfinder.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kartik on 11/11/17.
 */

public class SuggestionAdapter extends ArrayAdapter {

    ArrayList<StationInfo> list;
    LayoutInflater inflater;

    public SuggestionAdapter(@NonNull Context context, int resource, @NonNull ArrayList<StationInfo> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SuggestionViewHolder viewHolder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.suggestion_layout, parent);
            viewHolder = new SuggestionViewHolder();
            viewHolder.stationLine = convertView.findViewById(R.id.station_line);
            viewHolder.stationName = convertView.findViewById(R.id.station_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SuggestionViewHolder) convertView.getTag();
            viewHolder.stationLine = convertView.findViewById(R.id.station_line);
            viewHolder.stationName = convertView.findViewById(R.id.station_name);
        }

        viewHolder.stationName.setText(list.get(position).getName());
        viewHolder.stationLine.setText(list.get(position).getLine());

        return convertView;
    }
}
