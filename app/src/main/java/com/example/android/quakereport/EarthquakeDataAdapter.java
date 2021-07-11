package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class EarthquakeDataAdapter extends ArrayAdapter<Earthquake>{
    private static final String LOG_TAG = EarthquakeDataAdapter.class.getSimpleName();


    public EarthquakeDataAdapter(Activity context, ArrayList<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_list_item, parent, false);
        }

        Earthquake earthquake = getItem(position);
        TextView eq_mag = (TextView)listItemView.findViewById(R.id.earthquake_mag);
        eq_mag.setText(formatMagnitude(earthquake.getMagnitude()));

        String location_info = earthquake.getLocation();
        int indexOfOf = location_info.indexOf("of");
        String location_offset, location_main;

        if(location_info.contains("of")){
            location_offset = location_info.substring(0, indexOfOf+2);
            location_main = location_info.substring(indexOfOf+2);
        }
        else {
            location_offset = "Near the ";
            location_main = location_info;
        }

        TextView eq_location = (TextView)listItemView.findViewById(R.id.earthquake_location);
        eq_location.setText(location_offset);

        TextView eq_location_main = (TextView)listItemView.findViewById(R.id.earthquake_location_main);
        eq_location_main.setText(location_main);

        TextView eq_date = (TextView)listItemView.findViewById(R.id.earthquake_date);
        eq_date.setText(earthquake.getDate());
        return listItemView;
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
}