package com.example.navbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.astrocalculator.AstroCalculator;
import com.example.navbar.ui.AstronomyCalculator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.InputMismatchException;


public class MainActivity extends AppCompatActivity {
    public static AstronomyCalculator astronomyCalculator;
    public static double latitude = 0.0, altitude = 0.0;
    private boolean isLocationSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void buttonConfirmSettingsClick(View view) {
        EditText latitudeEditText = findViewById(R.id.edit_text_latitude);
        EditText altitudeEditText = findViewById(R.id.edit_text_altitude);

        try {
            altitude = Double.parseDouble(altitudeEditText.getText().toString());
            latitude = Double.parseDouble(latitudeEditText.getText().toString());
            astronomyCalculator = new AstronomyCalculator(altitude, latitude);
            isLocationSet = true;
        } catch (InputMismatchException IME) {
            //TODO: na pewno można to zrobić lepiej
            System.out.println("Błędne dane");
        }
    }

    public void useDefaultCoordinates(View view) {
        altitude = 51.776659378890685;
        latitude = 19.45303173904975;

        astronomyCalculator = new AstronomyCalculator(altitude, latitude);
        isLocationSet = true;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getAltitude() {
        return altitude;
    }
}