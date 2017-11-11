package com.darksidehacks.delhimetropathfinder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.darksidehacks.delhimetropathfinder.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    ArrayList<StationInfo> stations = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayList<String> stationNames = new ArrayList<>();

    @BindView(R.id.main_toolbar) Toolbar toolbar;
    @BindView(R.id.source) AutoCompleteTextView source;
    @BindView(R.id.destination) AutoCompleteTextView destination;
    @BindView(R.id.loading_icon) ProgressBar loadingSpinner;
    @BindView(R.id.get_route) Button showRoute;
    @BindView(R.id.message) TextView message;
    @BindView(R.id.refreshButton) ImageButton refreshButton;
    @OnClick(R.id.refreshButton) void refresh() {
        loadingSpinner.setVisibility(View.VISIBLE);
        refreshButton.setVisibility(View.GONE);
        showRoute.setVisibility(View.GONE);
        message.setText(R.string.loading_message);
        message.setTextColor(getResources().getColor(R.color.colorLoadingMessage));
        loadStationData();
    }

    @OnClick(R.id.get_route) void getRoute() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        if(!validate()) {
            return;
        } else {
            loadingSpinner.setVisibility(View.VISIBLE);
            refreshButton.setVisibility(View.GONE);
            showRoute.setVisibility(View.GONE);
            message.setText(R.string.loading_message);
            message.setTextColor(getResources().getColor(R.color.colorLoadingMessage));
            int idS = getIndex(String.valueOf(source.getText()));
            int idD = getIndex(String.valueOf(destination.getText()));
            new GetShortestRoute().execute("http://delhi-metro-api.herokuapp.com/distance/get/distance?from=" + idS + "&to=" + idD);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        loadingSpinner.setVisibility(View.VISIBLE);
        refreshButton.setVisibility(View.GONE);
        showRoute.setVisibility(View.GONE);
        message.setText(R.string.loading_message);
        message.setTextColor(getResources().getColor(R.color.colorLoadingMessage));

        source.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        destination.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        source.setEnabled(false);
        destination.setEnabled(false);

        destination.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            getRoute();
                            Log.i("Done", "Pressed!");
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        loadStationData();

    }

    public class GetStationData extends AsyncTask<String, Void, String> {

        HttpURLConnection httpURLConnection;
        URL url1;

        @Override
        protected String doInBackground(String... urls) {

            try {

                url1 = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url1.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                String result = "";

                int data = reader.read();
                char current;

                while(data != -1) {

                    current = (char) data;
                    result += current;
                    data = reader.read();

                }


                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s == null) {
                message.setText(R.string.station_error_message);
                refreshButton.setVisibility(View.VISIBLE);
                message.setTextColor(getResources().getColor(R.color.colorErrorMessage));
                loadingSpinner.setVisibility(View.GONE);
                showRoute.setVisibility(View.VISIBLE);
                return;
            }

            String crappyPrefix = "null";

            if(s.startsWith(crappyPrefix)){
                s = s.substring(crappyPrefix.length(), s.length());
            }

            int i;
            StationInfo info;

            message.setText("");

            try {
                JSONArray station = new JSONArray(s);
                for(i = 0; i < station.length(); i++) {
                    info = new StationInfo();
                    info.setName(station.getJSONObject(i).getString("name"));
                    info.setLine(station.getJSONObject(i).getString("line"));
                    info.setIndex(station.getJSONObject(i).getInt("index"));
                    info.setLat(station.getJSONObject(i).getDouble("latitude"));
                    info.setLng(station.getJSONObject(i).getDouble("longitude"));
                    JSONArray a = station.getJSONObject(i).getJSONArray("vertices");
                    ArrayList<Integer> b = new ArrayList<>();
                    for(int j = 0; j < a.length(); j++) {
                        b.add(a.getInt(j));
                    }
                    info.setVertices(b);
                    stations.add(info);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("JSON", "Couldn't do it!");
            }

            Log.i("Station", stations.toString());
            for(i = 0; i < stations.size(); i++) {
                stationNames.add(stations.get(i).getName());
            }
            adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, stationNames);
            source.setAdapter(adapter);
            destination.setAdapter(adapter);

            loadingSpinner.setVisibility(View.GONE);
            showRoute.setVisibility(View.VISIBLE);

            source.setEnabled(true);
            destination.setEnabled(true);
        }

    }

    public class GetShortestRoute extends AsyncTask<String, Void, String> {

        HttpURLConnection httpURLConnection;
        URL url1;
        ArrayList<Integer> vertices = new ArrayList<>();
        int i;

        @Override
        protected String doInBackground(String... urls) {

            try {

                url1 = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url1.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                String result = "";

                int data = reader.read();
                char current;

                while(data != -1) {

                    current = (char) data;
                    result += current;
                    data = reader.read();

                }


                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            String crappyPrefix = "null";

            if(s == null) {
                message.setText(R.string.route_error_message);
                message.setTextColor(getResources().getColor(R.color.colorErrorMessage));
                loadingSpinner.setVisibility(View.GONE);
                showRoute.setVisibility(View.VISIBLE);
                return;
            }

            if(s.startsWith(crappyPrefix)){
                s = s.substring(crappyPrefix.length(), s.length());
            }

            try {
                JSONArray nodes = new JSONArray(s);
                for(i = 0; i < nodes.length(); i++) {
                    vertices.add(i, nodes.getInt(i));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent maps = new Intent(MainActivity.this, RouteMapsActivity.class);
            Bundle bVertices = new Bundle();
            bVertices.putSerializable("VERTICES", vertices);
            Bundle bStations = new Bundle();
            bStations.putSerializable("STATIONS", stations);
            maps.putExtra("vertices", bVertices);
            maps.putExtra("stations", bStations);
            startActivity(maps);
        }

    }

    public boolean validate() {
        String a = String.valueOf(source.getText());
        String b = String.valueOf(destination.getText());
        boolean a_status = true, b_status = true;
        if(a.equals("")) {
            source.setError("Enter a station's name.");
        }
        if(b.equals("")) {
            destination.setError("Enter a station's name.");
        }
        if(!stationNames.contains(a) && !a.equals("")) {
            source.setError("Station does not exist.");
            a_status = false;
        }
        if(!stationNames.contains(b) && !b.equals("")) {
            destination.setError("Station does not exist.");
            b_status = false;
        }
        return a_status && b_status;
    }

    public void loadStationData() {
        new GetStationData().execute("http://delhi-metro-api.herokuapp.com/distance");
    }

    public int getIndex(String name) {
        for(StationInfo object: stations) {
            if(object.getName().equals(name)) {
                return object.getIndex();
            }
        }
        return -1;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
