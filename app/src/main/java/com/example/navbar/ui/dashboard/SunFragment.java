package com.example.navbar.ui.dashboard;

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
import java.util.Date;
import java.util.Locale;

public class SunFragment extends Fragment {

    private SunViewModel sunViewModel;

    TextView deviceTimeSunTextView;
    TextView altitudeSunTextView;
    TextView latitudeSunTextView;

    TextView sunRiseTextView;
    TextView sunSetTextView;
    TextView sunRiseAzimuthTextView;
    TextView sunSetAzimuthTextView;
    TextView sunCivilTwilight;
    TextView sunCivilDawn;

    TextView altitudeMoonTextView;
    TextView latitudeMoonTextView;
    TextView moonRiseTextView;
    TextView moonSetTextView;
    TextView newMoonTextView;
    TextView fullMoonTextView;
    TextView moonPhaseTextView;

    String currentTime;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sunViewModel = new ViewModelProvider(this).get(SunViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sun, container, false);

        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        deviceTimeSunTextView = root.findViewById(R.id.text_device_time_sun_value);
        altitudeSunTextView = root.findViewById(R.id.text_altitude_sun_value);
        latitudeSunTextView = root.findViewById(R.id.text_latitude_sun_value);

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
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        sunViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (MainActivity.astronomyCalculator == null) {
                    deviceTimeSunTextView.setText(R.string.settings_not_set);
                } else {
                    runTimers();
                    updateTime();
                    updateSunInfo();
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
                updateSunInfo();
                handler.postDelayed(this, MainActivity.refreshRate.longValue() * 1000);
            }
        };
        handler.postDelayed(updateTask, MainActivity.refreshRate.longValue() * 1000);
    }

    public void updateTime() {
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        deviceTimeSunTextView.setText(currentTime);
    }

    public void updateSunInfo() {
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        if (altitudeSunTextView != null) {
            altitudeSunTextView.setText("" + MainActivity.astronomyCalculator.getAltitude());
            latitudeSunTextView.setText("" + MainActivity.astronomyCalculator.getLatitude());
        }



        sunRiseTextView.setText(MainActivity.astronomyCalculator.getSunRiseInfo());
        sunSetTextView.setText(MainActivity.astronomyCalculator.getSunSetInfo());
        sunRiseAzimuthTextView.setText("" + MainActivity.astronomyCalculator.getSunRiseAzimuthInfo());
        sunSetAzimuthTextView.setText("" + MainActivity.astronomyCalculator.getSunSetAzimuthInfo());
        sunCivilTwilight.setText(MainActivity.astronomyCalculator.getCivilTwilightMorning());
        sunCivilDawn.setText(MainActivity.astronomyCalculator.getCivilTwilightEvening());

        if(moonRiseTextView != null) {

            altitudeMoonTextView.setText("" + MainActivity.astronomyCalculator.getAltitude());
            latitudeMoonTextView.setText("" + MainActivity.astronomyCalculator.getLatitude());
            moonRiseTextView.setText(MainActivity.astronomyCalculator.getMoonRiseInfo());
            moonSetTextView.setText(MainActivity.astronomyCalculator.getMoonSetInfo());
            moonRiseTextView.setText(MainActivity.astronomyCalculator.getMoonRiseInfo());
            moonSetTextView.setText(MainActivity.astronomyCalculator.getMoonSetInfo());
            newMoonTextView.setText(MainActivity.astronomyCalculator.getNextNewMoon());
            fullMoonTextView.setText(MainActivity.astronomyCalculator.getNextFullMoon());
            moonPhaseTextView.setText((int) MainActivity.astronomyCalculator.getMoonPhase() + "%");
        }


    }
}