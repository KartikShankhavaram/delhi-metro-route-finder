package com.darksidehacks.delhimetropathfinder.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.darksidehacks.delhimetropathfinder.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> stations = new ArrayList<>();

    @BindView(R.id.main_toolbar) Toolbar toolbar;
    @BindView(R.id.source) AutoCompleteTextView source;
    @BindView(R.id.destination) AutoCompleteTextView destination;
    @OnClick(R.id.get_route) void validate() {
        String a = String.valueOf(source.getText());
        String b = String.valueOf(destination.getText());
        if(!stations.contains(a)) {
            source.setError("Station does not exist.");
        }
        if(!stations.contains(b)) {
            destination.setError("Station does not exist.");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        //startActivity(new Intent(this, SearchResultsActivity.class));

        stations.add("Rajiv Chowk");
        stations.add("Rajiv Gali");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stations);
        source.setAdapter(adapter);
        destination.setAdapter(adapter);




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
                Toast.makeText(MainActivity.this, "Couldn't fetch station data, please try again.", Toast.LENGTH_SHORT).show();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            String crappyPrefix = "null";

            if(s.startsWith(crappyPrefix)){
                s = s.substring(crappyPrefix.length(), s.length());
            }



        }

    }
}
