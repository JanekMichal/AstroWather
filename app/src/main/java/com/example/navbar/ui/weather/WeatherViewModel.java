package com.example.navbar.ui.weather;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<String> city;
    private MutableLiveData<String> description;
    private MutableLiveData<Integer> temp;
    private MutableLiveData<Integer> tempMin;
    private MutableLiveData<Integer> tempMax;
    private MutableLiveData<Integer> pressure;
    private MutableLiveData<Integer> wind;
    private MutableLiveData<Integer> humidity;

    public WeatherViewModel() {

        description = new MutableLiveData<>();
        temp = new MutableLiveData<>();
        tempMin = new MutableLiveData<>();
        tempMax = new MutableLiveData<>();
        pressure = new MutableLiveData<>();
        wind = new MutableLiveData<>();
        humidity = new MutableLiveData<>();
    }

    public MutableLiveData<String> getCity() {
        return city;
    }

    public void setCity(MutableLiveData<String> city) {
        this.city = city;
    }

    public void setHumidity(MutableLiveData<Integer> humidity) {
        this.humidity = humidity;
    }

    public MutableLiveData<Integer> getHumidity() {
        return humidity;
    }

    public MutableLiveData<String> getDescription() {
        return description;
    }

    public void setDescription(MutableLiveData<String> description) {
        this.description = description;
    }

    public MutableLiveData<Integer> getTemp() {
        return temp;
    }

    public void setTemp(MutableLiveData<Integer> temp) {
        this.temp = temp;
    }

    public MutableLiveData<Integer> getTempMin() {
        return tempMin;
    }

    public void setTempMin(MutableLiveData<Integer> tempMin) {
        this.tempMin = tempMin;
    }

    public MutableLiveData<Integer> getTempMax() {
        return tempMax;
    }

    public void setTempMax(MutableLiveData<Integer> tempMax) {
        this.tempMax = tempMax;
    }

    public MutableLiveData<Integer> getPressure() {
        return pressure;
    }

    public void setPressure(MutableLiveData<Integer> pressure) {
        this.pressure = pressure;
    }

    public MutableLiveData<Integer> getWind() {
        return wind;
    }

    public void setWind(MutableLiveData<Integer> wind) {
        this.wind = wind;
    }


}