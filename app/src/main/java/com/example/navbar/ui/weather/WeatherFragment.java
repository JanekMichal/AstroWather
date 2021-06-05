package com.example.navbar.ui.weather;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.navbar.MainActivity;
import com.example.navbar.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class WeatherFragment extends Fragment {

    TextView deviceTimeWeatherTextView;
    TextView conditionTextView;
    TextView temperatureTextView;
    TextView lowTempTextView;
    TextView highTempTextView;
    TextView pressureTextView;
    TextView windSpeedTextView;
    TextView humidityTextView;
    String currentTime;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WeatherViewModel weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        View root = inflater.inflate(R.layout.fragment_weather, container, false);

        deviceTimeWeatherTextView = root.findViewById(R.id.text_device_time_weather);
        conditionTextView = root.findViewById(R.id.text_condition_value);
        temperatureTextView = root.findViewById(R.id.text_temperature_value);
        lowTempTextView = root.findViewById(R.id.text_low_value);
        highTempTextView = root.findViewById(R.id.text_high_value);
        pressureTextView = root.findViewById(R.id.text_pressure_value);
        windSpeedTextView = root.findViewById(R.id.text_wind_speed_value);
        humidityTextView = root.findViewById(R.id.text_humidity_value);

        weatherViewModel.getText().observe(getViewLifecycleOwner(), s -> {
            if (MainActivity.astronomyCalculator == null) {
                deviceTimeWeatherTextView.setText(R.string.settings_not_set);
            } else {
                runTimers();
                updateTime();

                    setWeatherDetails();


//                try {
//                    MainActivity.advancedWeather.creteMainFile(getActivity());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });
        return root;
    }

    public void setWeatherDetails() {

        String description = MainActivity.advancedWeather.getDescription();
        int temp =  MainActivity.advancedWeather.getTemp();
        int tempMin =  MainActivity.advancedWeather.getTempMin();
        int tempMax =  MainActivity.advancedWeather.getTempMax();
        int pressure =  MainActivity.advancedWeather.getPressure();
        int wind =  MainActivity.advancedWeather.getWind();
        int humidity =  MainActivity.advancedWeather.getHumidity();

        conditionTextView.setText(description);
        temperatureTextView.setText(temp + "°C");
        lowTempTextView.setText(tempMin + "°C");
        highTempTextView.setText(tempMax + "°C");
        pressureTextView.setText(pressure + "hPa");
        windSpeedTextView.setText(wind + "m/s");
        humidityTextView.setText(humidity + "%");
    }


//    public void getWeatherDetails() {
//        String query = API_PATH + "?q=" + city + "&units=metric&APPID=" + API_KEY;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, query,
//                response -> {
//                    try {
//
//                        JSONObject jsonResponse = new JSONObject(response);
//                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
//                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
//                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
//                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
//
//                        String description = jsonObjectWeather.getString("description");
//                        int temp = jsonObjectMain.getInt("temp");
//                        int tempMin = jsonObjectMain.getInt("temp_min");
//                        int tempMax = jsonObjectMain.getInt("temp_max");
//                        int pressure = jsonObjectMain.getInt("pressure");
//                        int wind = jsonObjectWind.getInt("speed");
//                        int humidity = jsonObjectMain.getInt("humidity");
//
//                        conditionTextView.setText(description);
//                        temperatureTextView.setText(temp + "°C");
//                        lowTempTextView.setText(tempMin + "°C");
//                        highTempTextView.setText(tempMax + "°C");
//                        pressureTextView.setText( pressure + "hPa");
//                        windSpeedTextView.setText(wind + "m/s");
//                        humidityTextView.setText(humidity + "%");
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }, error -> {
//
//            System.out.println("Error");
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
//        requestQueue.add(stringRequest);
//    }

    public void runTimers() {
        Handler timeHandler = new Handler();
        Runnable updateTime = new Runnable() {
            @Override
            public void run() {
                updateTime();
                timeHandler.postDelayed(this, 1000);
            }
        };
        timeHandler.postDelayed(updateTime, 1000);

        Handler handler = new Handler();
        Runnable updateTask = new Runnable() {
            @Override
            public void run() {

                handler.postDelayed(this, MainActivity.refreshRate.longValue() * 1000);
            }
        };
        handler.postDelayed(updateTask, MainActivity.refreshRate.longValue() * 1000);
    }

    public void updateTime() {
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        deviceTimeWeatherTextView.setText(currentTime);
    }
}