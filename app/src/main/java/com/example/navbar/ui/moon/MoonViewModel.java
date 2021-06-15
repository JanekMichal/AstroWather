package com.example.navbar.ui.moon;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MoonViewModel extends ViewModel {

    private MutableLiveData<Double> lon;
    private MutableLiveData<Double> lat;
    private MutableLiveData<String> city;

    public MoonViewModel() {
        lon = new MutableLiveData<>();
        lat = new MutableLiveData<>();
        city = new MutableLiveData<>();
    }

    public MutableLiveData<Double> getLon() {
        return lon;
    }

    public void setLon(MutableLiveData<Double> lon) {
        this.lon = lon;
    }

    public MutableLiveData<Double> getLat() {
        return lat;
    }

    public void setLat(MutableLiveData<Double> lat) {
        this.lat = lat;
    }

    public MutableLiveData<String> getCity() {
        return city;
    }

    public void setCity(MutableLiveData<String> city) {
        this.city = city;
    }
}