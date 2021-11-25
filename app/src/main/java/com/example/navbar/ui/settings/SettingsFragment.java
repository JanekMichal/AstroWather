package com.example.navbar.ui.settings;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.navbar.MainActivity;
import com.example.navbar.R;
import com.example.navbar.ui.weather.WeatherViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

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

    WeatherViewModel weatherViewModel;
    public static ArrayList<String> favCityList = new ArrayList<>(Collections.singleton("Select favourite city"));

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        Spinner spinnerTime = root.findViewById(R.id.spinner_time);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapter);
        spinnerTime.setOnItemSelectedListener(this);


        Spinner spinnerFavouriteCity = root.findViewById(R.id.spinner_fav_city);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, favCityList);
        spinnerFavouriteCity.setAdapter(adapter2);
        spinnerFavouriteCity.setOnItemSelectedListener(this);


        initializeViews(root);

        runTimers();
        updateTime();
        if (MainActivity.astronomyCalculator != null) {
            updateMoonInfo();
        }
        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.spinner_fav_city:
                MainActivity.city = favCityList.get(position);
                if (position != 0) {
                    Toast.makeText(requireContext(), "City selected: " + favCityList.get(position), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.spinner_time:

                String text = parent.getItemAtPosition(position).toString();

                switch (text) {
                    case "15 min":
                        MainActivity.setRefreshRate(15);
                        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
                        break;
                    case "30 min":
                        MainActivity.setRefreshRate(30);
                        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
                        break;
                    case "45 min":
                        MainActivity.setRefreshRate(45);
                        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
                        break;
                    case "1 hour":
                        MainActivity.setRefreshRate(60);
                        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
                        break;
                }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void updateMoonInfo() {
        if (sunRiseTextView != null) {
            altitudeMoonTextView.setText("" + MainActivity.astronomyCalculator.getAltitude());
            latitudeMoonTextView.setText("" + MainActivity.astronomyCalculator.getLatitude());

            moonRiseTextView.setText(MainActivity.astronomyCalculator.getMoonRiseInfo());
            moonSetTextView.setText(MainActivity.astronomyCalculator.getMoonSetInfo());
            moonRiseTextView.setText(MainActivity.astronomyCalculator.getMoonRiseInfo());
            moonSetTextView.setText(MainActivity.astronomyCalculator.getMoonSetInfo());
            newMoonTextView.setText(MainActivity.astronomyCalculator.getNextNewMoon());
            fullMoonTextView.setText(MainActivity.astronomyCalculator.getNextFullMoon());
            moonPhaseTextView.setText((int) MainActivity.astronomyCalculator.getMoonPhase() + "%");

            sunRiseTextView.setText(MainActivity.astronomyCalculator.getSunRiseInfo());
            sunSetTextView.setText(MainActivity.astronomyCalculator.getSunSetInfo());
            sunRiseAzimuthTextView.setText("" + MainActivity.astronomyCalculator.getSunRiseAzimuthInfo());
            sunSetAzimuthTextView.setText("" + MainActivity.astronomyCalculator.getSunSetAzimuthInfo());
            sunCivilTwilight.setText(MainActivity.astronomyCalculator.getCivilTwilightMorning());
            sunCivilDawn.setText(MainActivity.astronomyCalculator.getCivilTwilightEvening());
        }
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
        if (deviceTimeMoonTextView != null) {
            currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

            deviceTimeMoonTextView.setText(currentTime);
        }
    }

    private void initializeViews(View root) {
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
    }
}