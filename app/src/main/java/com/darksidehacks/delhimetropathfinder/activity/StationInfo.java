package com.darksidehacks.delhimetropathfinder.activity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kartik on 11/11/17.
 */

public class StationInfo implements Serializable {

    private String name;
    private String line;
    private int index;
    private double lat;
    private double lng;
    private ArrayList<Integer> vertices;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    String LINE_SUFFIX = " Line";

    public StationInfo() {
        vertices = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<Integer> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Integer> vertices) {
        this.vertices = vertices;
    }
}
