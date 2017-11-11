package com.darksidehacks.delhimetropathfinder.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.darksidehacks.delhimetropathfinder.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class RouteMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<StationInfo> stations;
    ArrayList<Integer> vertices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent fromMain = getIntent();
        if (fromMain != null) {
            Bundle bVertices = fromMain.getBundleExtra("vertices");
            Bundle bStations = fromMain.getBundleExtra("stations");
            stations = (ArrayList<StationInfo>) bStations.getSerializable("STATIONS");
            vertices = (ArrayList<Integer>) bVertices.getSerializable("VERTICES");
            Log.i("stations", stations.toString());
            Log.i("vertices", vertices.toString());
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng, oldLatLng = null;
        boolean firstRunDone = false;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (int a : vertices) {
            double lat = 0, lng = 0;
            if (getStationInfo(a) != null) {
                lat = getStationInfo(a).getLat();
                lng = getStationInfo(a).getLng();
            }
            latLng = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(latLng).title(getStationInfo(a).getName()));
            builder.include(latLng);
            if (firstRunDone) {
                drawLine(oldLatLng, latLng, getStationInfo(a).getLine());
                Log.i("Line", getStationInfo(a).getLine());
            } else {
                firstRunDone = true;
            }
            oldLatLng = latLng;
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 200, 200, 0));
    }

    private StationInfo getStationInfo(int index) {
        for (StationInfo object : stations) {
            if (object.getIndex() == index) {
                return object;
            }
        }
        return null;
    }

    public void drawLine(LatLng point1, LatLng point2, String line) {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(point1)
                .add(point2);
        polylineOptions.color(getColor(line));
        mMap.addPolyline(polylineOptions);
    }

    public int getColor(String a) {
        a = a.toLowerCase();
        String[] color = a.split(" ");
        a = color[0].substring(2);
        Log.i("color", a);
        switch (a) {
            case "blue":
                return R.color.Blue;

            case "yellow":
                return R.color.Yellow;

            case "green":
                return R.color.Green;

            default:
                return -1;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
}
