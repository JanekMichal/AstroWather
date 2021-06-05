package com.example.navbar;

import android.app.Activity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.text.DecimalFormat;

public class AdvancedWeather {
    private String API_KEY = "eeda7f9dbb9ad0575f297a1ffbd7ef59";
    private String API_PATH = "http://api.openweathermap.org/data/2.5/weather";
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private String city;
    private double lon;
    private double lat;
    private String description;
    private int temp;
    private int tempMin;
    private int tempMax;
    private int pressure;
    private int wind;
    private int humidity;
    JSONObject jsonResponse;
    String FILE_NAME = "weatherData.json";
    Activity activity;
    int errorCode;

    public AdvancedWeather(String city, View view, Activity activity) throws ConnectException{
        this.activity = activity;
        this.city = city;
        getWeatherDetails(view);
    }

    public int getWeatherDetails(View view) throws ConnectException{
        String query = API_PATH + "?q=" + city + "&units=metric&AfdasfdasPPID=" + API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, query,
                response -> {
                    try {
                        jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        JSONObject jsonObjectCords = jsonResponse.getJSONObject("coord");

                        lon = jsonObjectCords.getDouble("lon");
                        lat = jsonObjectCords.getDouble("lat");
                        description = jsonObjectWeather.getString("description");
                        temp = jsonObjectMain.getInt("temp");
                        tempMin = jsonObjectMain.getInt("temp_min");
                        tempMax = jsonObjectMain.getInt("temp_max");
                        pressure = jsonObjectMain.getInt("pressure");
                        wind = jsonObjectWind.getInt("speed");
                        humidity = jsonObjectMain.getInt("humidity");

                        creteMainFile();

                    } catch (JSONException | IOException e) {

                        e.printStackTrace();

                    }
                }, error -> {
            System.out.println("Error");
            errorCode = 1;
        });
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(stringRequest);
        if (errorCode == 1) {
            throw new ConnectException("plz work");
        } else {
            return 1;
        }
    }

    public void creteMainFile() throws IOException {
        String fileLocation = activity.getCacheDir().toString() + FILE_NAME;
        PrintWriter out = new PrintWriter(new FileWriter(fileLocation));
        System.out.println(fileLocation);
        out.write(jsonResponse.toString());
        out.close();
    }





    public String getCity() {
        return city;
    }
    public double getLon() {
        return lon;
    }
    public double getLat() {
        return lat;
    }
    public String getDescription() {
        return description;
    }
    public int getTemp() {
        return temp;
    }
    public int getTempMin() {
        return tempMin;
    }
    public int getTempMax() {
        return tempMax;
    }
    public int getPressure() {
        return pressure;
    }
    public int getWind() {
        return wind;
    }
    public int getHumidity() {
        return humidity;
    }
    public JSONObject getJsonResponse() {
        return jsonResponse;
    }
}
