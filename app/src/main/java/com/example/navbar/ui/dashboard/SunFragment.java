package com.example.navbar.ui.dashboard;

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

import org.w3c.dom.Text;

public class SunFragment extends Fragment {

    private SunViewModel sunViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sunViewModel =
                new ViewModelProvider(this).get(SunViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sun, container, false);

        TextView textView = root.findViewById(R.id.text_sun_rise);
        TextView sunSetTextView = root.findViewById(R.id.text_sun_set);
        TextView sunRiseAzimuthTextView = root.findViewById(R.id.text_sun_rise_azimuth);
        TextView sunSetAzimuthTextView = root.findViewById(R.id.text_sun_set_azimuth);
        TextView sunCivilTwilight = root.findViewById(R.id.text_sun_civil_twilight);
        TextView sunCivilDawn = root.findViewById(R.id.text_sun_civil_dawn);


        sunViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {


                if (MainActivity.astronomyCalculator == null) {
                    textView.setText("First, provide information about your localization in Settings section");
                } else {
                    textView.setText("Sun rise: " + MainActivity.astronomyCalculator.getSunRiseInfo());
                    sunSetTextView.setText("Sun set: " + MainActivity.astronomyCalculator.getSunSetInfo());
                    sunRiseAzimuthTextView.setText("Sun rise azimuth: " + MainActivity.astronomyCalculator.getSunRiseAzimuthInfo());
                    sunSetAzimuthTextView.setText("Sun rise azimuth: " + MainActivity.astronomyCalculator.getSunSetAzimuthInfo());
                    sunCivilTwilight.setText("Sun civil Twilight: " + MainActivity.astronomyCalculator.getCivilTwilightMorning());
                    sunCivilDawn.setText("Sun civil Dawn: " + MainActivity.astronomyCalculator.getCivilTwilightEvening());
                }
            }
        });
        return root;
    }
}