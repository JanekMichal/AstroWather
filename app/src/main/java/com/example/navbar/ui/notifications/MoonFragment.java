package com.example.navbar.ui.notifications;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MoonFragment extends Fragment {

    private MoonViewModel moonViewModel;

    TextView deviceTimeMoonTextView;
    TextView altitudeMoonTextView;
    TextView latitudeMoonTextView;
    TextView moonRiseTextView;
    TextView moonSetTextView;
    TextView newMoonTextView;
    TextView fullMoonTextView;
    TextView moonPhaseTextView;
    TextView dayOfSonodicMonthTextView;
    String currentTime;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        moonViewModel = new ViewModelProvider(this).get(MoonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_moon, container, false);

        deviceTimeMoonTextView = root.findViewById(R.id.text_device_time_moon);
        altitudeMoonTextView = root.findViewById(R.id.text_altitude_moon);
        latitudeMoonTextView = root.findViewById(R.id.text_latitude_moon);
        moonRiseTextView = root.findViewById(R.id.text_moon_rise);
        moonSetTextView = root.findViewById(R.id.text_moon_set);
        newMoonTextView = root.findViewById(R.id.text_new_moon);
        fullMoonTextView = root.findViewById(R.id.text_full_moon);
        moonPhaseTextView = root.findViewById(R.id.text_moon_phase);
        dayOfSonodicMonthTextView = root.findViewById(R.id.text_day_of_sonodic_mounth);


        moonViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (MainActivity.astronomyCalculator == null) {
                    moonRiseTextView.setText("First, provide information about your localization in settings section");
                } else {
                    updateTime();
                    updateMoonInfo();
                }
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
                updateMoonInfo();
                handler.postDelayed(this, MainActivity.refreshRate.longValue() * 1000);
            }
        };
        handler.postDelayed(updateTask, MainActivity.refreshRate.longValue() * 1000);
    }

    public void updateTime() {
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        deviceTimeMoonTextView.setText("Device time: " + currentTime);
    }

    public void updateMoonInfo() {
        altitudeMoonTextView.setText("Altitude: " + MainActivity.astronomyCalculator.getAltitude());
        latitudeMoonTextView.setText("Latitude: " + MainActivity.astronomyCalculator.getLatitude());

        moonRiseTextView.setText("Moon rise: " + MainActivity.astronomyCalculator.getMoonRiseInfo());
        moonSetTextView.setText("Moon set: " + MainActivity.astronomyCalculator.getMoonSetInfo());
        moonRiseTextView.setText("Moon rise: " + MainActivity.astronomyCalculator.getMoonRiseInfo());
        moonSetTextView.setText("Moon set: " + MainActivity.astronomyCalculator.getMoonSetInfo());
        newMoonTextView.setText("Next new moon: " + MainActivity.astronomyCalculator.getNextNewMoon());
        fullMoonTextView.setText("Next full moon: " + MainActivity.astronomyCalculator.getNextFullMoon());
        moonPhaseTextView.setText("Moon phase: " + (int) MainActivity.astronomyCalculator.getMoonPhase() + "%");
        //dayOfSonodicMonthTextView.setText("Day of Sonodic month " + MainActivity.astronomyCalculator.;
    }
}