package com.example.navbar.ui.notifications;

import android.os.Bundle;
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

public class MoonFragment extends Fragment {

    private MoonViewModel moonViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        moonViewModel =
                new ViewModelProvider(this).get(MoonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_moon, container, false);

        TextView deviceTimeMoonTextView = root.findViewById(R.id.text_device_time_moon);
        TextView altitudeMoonTextView = root.findViewById(R.id.text_altitude_moon);
        TextView latitudeMoonTextView = root.findViewById(R.id.text_latitude_moon);
        TextView moonRiseTextView = root.findViewById(R.id.text_moon_rise);
        TextView moonSetTextView = root.findViewById(R.id.text_moon_set);
        TextView newMoonTextView = root.findViewById(R.id.text_new_moon);
        TextView fullMoonTextView = root.findViewById(R.id.text_full_moon);
        TextView moonPhaseTextView = root.findViewById(R.id.text_moon_phase);
        TextView dayOfSonodicMonthTextView = root.findViewById(R.id.text_day_of_sonodic_mounth);



        moonViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                if (MainActivity.astronomyCalculator == null) {
                    moonRiseTextView.setText("First, provide information about your localization in Settings section");
                } else {
                    //TODO: dodać czas urządzenia
                    deviceTimeMoonTextView.setText("Device time: ");
                    altitudeMoonTextView.setText("Altitude: " + MainActivity.astronomyCalculator.getAltitude());
                    latitudeMoonTextView.setText("Latitude: " + MainActivity.astronomyCalculator.getLatitude());


                    moonRiseTextView.setText("Moon rise: " + MainActivity.astronomyCalculator.getMoonRiseInfo());
                    moonSetTextView.setText("Moon set: " + MainActivity.astronomyCalculator.getMoonSetInfo());
                    moonRiseTextView.setText("Moon rise: " + MainActivity.astronomyCalculator.getMoonRiseInfo());
                    moonSetTextView.setText("Moon set: " + MainActivity.astronomyCalculator.getMoonSetInfo());
                    newMoonTextView.setText("Next new moon: " + MainActivity.astronomyCalculator.getNextNewMoon());
                    fullMoonTextView.setText("Next full moon: " + MainActivity.astronomyCalculator.getNextFullMoon());
                    moonPhaseTextView.setText("Moon phase: " + MainActivity.astronomyCalculator.getMoonPhase());
                    //dayOfSonodicMonthTextView.setText("Day of Sonodic month " + MainActivity.astronomyCalculator.;
                }

            }
        });
        return root;
    }
}