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

import android.app.LoaderManager;
import android.content.Loader;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private EarthquakeDataAdapter mAdapter;
    private EarthquakeLoader mLoader;
    private TextView mEmptyStateTextView;
    private ListView earthquakeListView;
    private ProgressBar mProgressBar;
    LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mEmptyStateTextView = (TextView) findViewById(R.id.emptyTxtView);
        earthquakeListView = (ListView) findViewById(R.id.list);
        mProgressBar = (ProgressBar)findViewById(R.id.loading_spinner);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Get a reference to the LoaderManager, in order to interact with loaders.
        loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        Log.i(LOG_TAG, "initLoader started ----------------------------------------");
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null)
            return;
        else if (networkInfo != null && networkInfo.isConnected())
            return;

    }


    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        // TODO: Create a new loader for the given URL
        Log.i(LOG_TAG, "onCreateLoader() callback ----------------------------------------");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        // TODO: Update the UI with the result
        mProgressBar.setVisibility(View.GONE);
        Log.i(LOG_TAG, "onLoaderFinished() callback ----------------------------------------");
        // Clear the adapter of previous earthquake data
        if (mAdapter != null)
            mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            //setEmptyView(0);
            UpdateUi(data);
        } else {
            // Set empty state text to display "No earthquakes found."
            mEmptyStateTextView.setText(R.string.no_earthquakes_found);
            //setEmptyView(1);
        }


    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // TODO: Loader reset, so we can clear out our existing data.
        Log.i(LOG_TAG, "onLoaderReset() callback ----------------------------------------");
        if (mAdapter != null)
            mAdapter.clear();

    }

    private void UpdateUi(List<Earthquake> earthquakes) {
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        mAdapter = new EarthquakeDataAdapter(
                EarthquakeActivity.this, (ArrayList<Earthquake>) earthquakes);

        earthquakeListView.setAdapter(mAdapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url = mAdapter.getItem(position).getUrl();
                System.out.println("URL: " + url);
                Intent oepnUrlIntent = new Intent(Intent.ACTION_VIEW);
                oepnUrlIntent.setData(Uri.parse(url));
                startActivity(oepnUrlIntent);
            }
        });
    }

//    private void setEmptyView(int visibility) {
//        if(visibility == 1)
//            mEmptyStateTextView.setVisibility(View.VISIBLE);
//        else
//            mEmptyStateTextView.setVisibility(View.INVISIBLE);
//    }
}
