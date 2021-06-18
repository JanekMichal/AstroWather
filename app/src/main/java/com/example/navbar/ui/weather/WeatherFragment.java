package com.example.navbar.ui.weather;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.navbar.MainActivity;
import com.example.navbar.R;
import com.example.navbar.ui.settings.SettingsViewModel;

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
    TextView cityTextView;
    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WeatherViewModel weatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_weather, container, false);

        initializeViews(root);

        if (MainActivity.astronomyCalculator == null) {
            deviceTimeWeatherTextView.setText(R.string.settings_not_set);
        } else {
            if (MainActivity.unit.equals("C")) {
                setTempsCelsius(weatherViewModel);
            } else {
                setTempsFahrenheit(weatherViewModel);


            }
            runTimers();
            updateTime();
        }
        return root;
    }

    private void setTempsFahrenheit(WeatherViewModel weatherViewModel) {
        cityTextView.setText(weatherViewModel.getCity().getValue());
        conditionTextView.setText(weatherViewModel.getDescription().getValue());
        temperatureTextView.setText(weatherViewModel.getTemp().getValue() * 1.8 + 32 + "°F");
        lowTempTextView.setText(weatherViewModel.getTempMin().getValue() * 1.8 + 32 + "°F");
        highTempTextView.setText(weatherViewModel.getTempMax().getValue() * 1.8 + 32 + "°F");
        pressureTextView.setText(weatherViewModel.getPressure().getValue() * 2088.5 + "psf");
        windSpeedTextView.setText(weatherViewModel.getWind().getValue() * 3.2808 + "ft/s");
        humidityTextView.setText(weatherViewModel.getHumidity().getValue() + "%");
    }

    private void setTempsCelsius(WeatherViewModel weatherViewModel) {
        cityTextView.setText(weatherViewModel.getCity().getValue());
        conditionTextView.setText(weatherViewModel.getDescription().getValue());
        temperatureTextView.setText(weatherViewModel.getTemp().getValue() + "°C");
        lowTempTextView.setText(weatherViewModel.getTempMin().getValue() + "°C");
        highTempTextView.setText(weatherViewModel.getTempMax().getValue() + "°C");
        pressureTextView.setText(weatherViewModel.getPressure().getValue() + "hPa");
        windSpeedTextView.setText(weatherViewModel.getWind().getValue() + "m/s");
        humidityTextView.setText(weatherViewModel.getHumidity().getValue() + "%");
    }

    private void initializeViews(View root) {
        cityTextView = root.findViewById(R.id.text_city_weather);
        deviceTimeWeatherTextView = root.findViewById(R.id.text_device_time_weather);
        conditionTextView = root.findViewById(R.id.text_condition_value);
        temperatureTextView = root.findViewById(R.id.text_temperature_value);
        lowTempTextView = root.findViewById(R.id.text_low_value);
        highTempTextView = root.findViewById(R.id.text_high_value);
        pressureTextView = root.findViewById(R.id.text_pressure_value);
        windSpeedTextView = root.findViewById(R.id.text_wind_speed_value);
        humidityTextView = root.findViewById(R.id.text_humidity_value);
    }


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