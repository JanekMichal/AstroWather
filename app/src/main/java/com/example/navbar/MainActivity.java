package com.example.navbar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.navbar.ui.AstronomyCalculator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {
    public static AstronomyCalculator astronomyCalculator;
    public static double latitude = 0.0, altitude = 0.0;
    public static Double refreshRate = 60.0 * 60.0; //1 godzina

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

//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

    public void buttonConfirmSettingsClick(View view) {
        EditText latitudeEditText = findViewById(R.id.edit_text_latitude);
        EditText altitudeEditText = findViewById(R.id.edit_text_altitude);
        closeKeyboard();
        try {
            altitude = Double.parseDouble(altitudeEditText.getText().toString());
            latitude = Double.parseDouble(latitudeEditText.getText().toString());
            astronomyCalculator = new AstronomyCalculator(altitude, latitude);
        } catch (NumberFormatException NFE) {
            latitudeEditText.setText("");
            altitudeEditText.setText("");
            Context context = getApplicationContext();
            CharSequence text = "Incorrect co-ordinates!";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void useDefaultCoordinates(View view) {
        //Łódź
        latitude = 19.45303173904975;
        altitude = 51.776659378890685;

        //Gdańsk
        //latitude = 18.596496032411604;
//        altitude = 54.37282100574918;

        astronomyCalculator = new AstronomyCalculator(altitude, latitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public static void setRefreshRate(double refreshRate) {
        MainActivity.refreshRate = refreshRate * 60.0;
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}