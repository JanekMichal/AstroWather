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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    String currentTime;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sunViewModel = new ViewModelProvider(this).get(SunViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sun, container, false);

        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        deviceTimeSunTextView = root.findViewById(R.id.text_device_time_sun);
        altitudeSunTextView = root.findViewById(R.id.text_altitude_sun);
        latitudeSunTextView = root.findViewById(R.id.text_latitude_sun);

        sunRiseTextView = root.findViewById(R.id.text_sun_rise);
        sunSetTextView = root.findViewById(R.id.text_sun_set);
        sunRiseAzimuthTextView = root.findViewById(R.id.text_sun_rise_azimuth);
        sunSetAzimuthTextView = root.findViewById(R.id.text_sun_set_azimuth);
        sunCivilTwilight = root.findViewById(R.id.text_sun_civil_twilight);
        sunCivilDawn = root.findViewById(R.id.text_sun_civil_dawn);
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        sunViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (MainActivity.astronomyCalculator == null) {
                    sunRiseTextView.setText("First, provide information about your localization in Settings section");
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
        deviceTimeSunTextView.setText("Device time: " + currentTime);
    }

    public void updateSunInfo() {
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        deviceTimeSunTextView.setText("Device time: " + currentTime);
        altitudeSunTextView.setText("Altitude: " + MainActivity.astronomyCalculator.getAltitude());
        latitudeSunTextView.setText("Latitude: " + MainActivity.astronomyCalculator.getLatitude());
        sunRiseTextView.setText("Sun rise: " + MainActivity.astronomyCalculator.getSunRiseInfo());
        sunSetTextView.setText("Sun set: " + MainActivity.astronomyCalculator.getSunSetInfo());
        sunRiseAzimuthTextView.setText("Sun rise azimuth: " + MainActivity.astronomyCalculator.getSunRiseAzimuthInfo());
        sunSetAzimuthTextView.setText("Sun rise azimuth: " + MainActivity.astronomyCalculator.getSunSetAzimuthInfo());
        sunCivilTwilight.setText("Sun civil Twilight: " + MainActivity.astronomyCalculator.getCivilTwilightMorning());
        sunCivilDawn.setText("Sun civil Dawn: " + MainActivity.astronomyCalculator.getCivilTwilightEvening());
    }
}