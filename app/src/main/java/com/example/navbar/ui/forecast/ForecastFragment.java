package com.example.navbar.ui.forecast;

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

public class ForecastFragment extends Fragment {

    private ForecastViewModel forecastViewModel;

    TextView deviceTimeWeatherTextView;


    String currentTime;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        forecastViewModel = new ViewModelProvider(this).get(ForecastViewModel.class);
        View root = inflater.inflate(R.layout.fragment_forecast, container, false);

        //deviceTimeWeatherTextView = root.findViewById(R.id.text_device_time_moon);



        forecastViewModel.getText().observe(getViewLifecycleOwner(), s -> {
            if (MainActivity.astronomyCalculator == null) {
                //deviceTimeWeatherTextView.setText(R.string.settings_not_set);
            } else {


                //runTimers();
                //updateTime();

            }
        });
        return root;
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