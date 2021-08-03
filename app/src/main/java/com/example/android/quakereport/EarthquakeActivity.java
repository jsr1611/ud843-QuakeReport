/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String urlStr = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";


    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {
        private ArrayList<Earthquake> earthquakeList;

        @Override
        protected ArrayList<Earthquake> doInBackground(String... urls) {
            earthquakeList = (ArrayList<Earthquake>) QueryUtils.fetchEarthquakeData(urlStr);
            return earthquakeList;
        }

        @Override
        protected void onPostExecute(List<Earthquake> earthquakeList) {
            UpdateUi(earthquakeList);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        EarthquakeAsyncTask earthquakeAsyncTask = new EarthquakeAsyncTask();
        earthquakeAsyncTask.execute(urlStr);







    }
    private void UpdateUi(List<Earthquake> earthquakes){

        // Create a fake list of earthquake locations.
        //ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();
//        ArrayList<Earthquake> earthquakes = new ArrayList<>();
//        earthquakes.add(new Earthquake(7.2, "San Francisco", "Feb 2, 2016") );
//        earthquakes.add(new Earthquake(6.1, "London", "July 20, 2015"));
//        earthquakes.add(new Earthquake(3.1, "Tokyo", "Nov 10, 2014"));
//        earthquakes.add(new Earthquake(5.4, "Mexico City", "May 3, 2014"));
//        earthquakes.add(new Earthquake(2.8, "Moscow", "Jan 31, 2013"));
//        earthquakes.add(new Earthquake(4.9, "Rio de Janeiro", "Aug 19, 2012" ));
//        earthquakes.add(new Earthquake(1.6, "Paris", "Oct 30, 2011"));

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        final EarthquakeDataAdapter eq_dataAdapter = new EarthquakeDataAdapter(
                EarthquakeActivity.this, (ArrayList<Earthquake>) earthquakes);

        earthquakeListView.setAdapter(eq_dataAdapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url = eq_dataAdapter.getItem(position).getUrl();
                System.out.println("URL: " + url);
                Intent oepnUrlIntent = new Intent(Intent.ACTION_VIEW);
                oepnUrlIntent.setData(Uri.parse(url));
                startActivity(oepnUrlIntent);
            }
        });
    }
}
