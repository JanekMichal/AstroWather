package com.example.navbar.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.navbar.MainActivity;
import com.example.navbar.R;
import com.example.navbar.ui.AstronomyCalculator;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener{


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        Spinner spinner = root.findViewById(R.id.time_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {


            }
        });

        return root;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}