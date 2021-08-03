package com.example.android.quakereport;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import android.graphics.drawable.GradientDrawable;


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
                    R.layout.earthquake_list_item, parent, false);
        }



        final Earthquake currentEarthquake = getItem(position);



        TextView eq_mag = (TextView)listItemView.findViewById(R.id.earthquake_mag);
        eq_mag.setText(formatMagnitude(currentEarthquake.getMagnitude()));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) eq_mag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        String location_info = currentEarthquake.getLocation();
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
        eq_date.setText(currentEarthquake.getDate());



        return listItemView;
    }


    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);

        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId =  R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId =R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId =R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
        }

    return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
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