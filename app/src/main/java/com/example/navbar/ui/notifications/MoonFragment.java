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
import com.example.navbar.ui.dashboard.SunFragment;
import com.example.navbar.ui.dashboard.SunViewModel;

import java.text.SimpleDateFormat;
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

    TextView sunRiseTextView;
    TextView sunSetTextView;
    TextView sunRiseAzimuthTextView;
    TextView sunSetAzimuthTextView;
    TextView sunCivilTwilight;
    TextView sunCivilDawn;

    String currentTime;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        moonViewModel = new ViewModelProvider(this).get(MoonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_moon, container, false);

        deviceTimeMoonTextView = root.findViewById(R.id.text_device_time_moon);
        altitudeMoonTextView = root.findViewById(R.id.text_altitude_moon_value);
        latitudeMoonTextView = root.findViewById(R.id.text_latitude_moon_value);

        moonRiseTextView = root.findViewById(R.id.text_moon_rise_value);
        moonSetTextView = root.findViewById(R.id.text_moon_set_value);
        newMoonTextView = root.findViewById(R.id.text_new_moon_value);
        fullMoonTextView = root.findViewById(R.id.text_full_moon_value);
        moonPhaseTextView = root.findViewById(R.id.text_moon_phase_value);

        sunRiseTextView = root.findViewById(R.id.text_sun_rise_value);
        sunSetTextView = root.findViewById(R.id.text_sun_set_value);
        sunRiseAzimuthTextView = root.findViewById(R.id.text_sun_rise_azimuth_value);
        sunSetAzimuthTextView = root.findViewById(R.id.text_sun_set_azimuth_value);
        sunCivilTwilight = root.findViewById(R.id.text_sun_civil_twilight_value);
        sunCivilDawn = root.findViewById(R.id.text_sun_civil_dawn_value);


        moonViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (MainActivity.astronomyCalculator == null) {
                    deviceTimeMoonTextView.setText(R.string.settings_not_set);
                } else {
                    runTimers();
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
        deviceTimeMoonTextView.setText(currentTime);
    }

    public void updateMoonInfo() {

        altitudeMoonTextView.setText("" + MainActivity.astronomyCalculator.getAltitude());
        latitudeMoonTextView.setText("" + MainActivity.astronomyCalculator.getLatitude());

        moonRiseTextView.setText(MainActivity.astronomyCalculator.getMoonRiseInfo());
        moonSetTextView.setText(MainActivity.astronomyCalculator.getMoonSetInfo());
        moonRiseTextView.setText(MainActivity.astronomyCalculator.getMoonRiseInfo());
        moonSetTextView.setText(MainActivity.astronomyCalculator.getMoonSetInfo());
        newMoonTextView.setText(MainActivity.astronomyCalculator.getNextNewMoon());
        fullMoonTextView.setText(MainActivity.astronomyCalculator.getNextFullMoon());
        moonPhaseTextView.setText((int) MainActivity.astronomyCalculator.getMoonPhase() + "%");
        //dayOfSonodicMonthTextView.setText("Day of Sonodic month " + MainActivity.astronomyCalculator.;

        if (sunRiseTextView != null) {
            sunRiseTextView.setText(MainActivity.astronomyCalculator.getSunRiseInfo());
            sunSetTextView.setText(MainActivity.astronomyCalculator.getSunSetInfo());
            sunRiseAzimuthTextView.setText("" + MainActivity.astronomyCalculator.getSunRiseAzimuthInfo());
            sunSetAzimuthTextView.setText("" + MainActivity.astronomyCalculator.getSunSetAzimuthInfo());
            sunCivilTwilight.setText(MainActivity.astronomyCalculator.getCivilTwilightMorning());
            sunCivilDawn.setText(MainActivity.astronomyCalculator.getCivilTwilightEvening());
        }


    }
}