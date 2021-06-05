package com.example.navbar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    public static AstronomyCalculator astronomyCalculator;
    public static double latitude = 0.0, altitude = 0.0;
    public static Double refreshRate = 60.0 * 60.0; //1 godzina
    public static AdvancedWeather advancedWeather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);


    }


    public void buttonConfirmSettingsClick(View view) {
        EditText latitudeEditText = findViewById(R.id.edit_text_city);
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


            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
        }
    }
    public void useDefaultCoordinates(View view) {
        try {
            advancedWeather = new AdvancedWeather("Łódź", view, this);
        } catch ( java.net.ConnectException e) {
            Toast toast = Toast.makeText(getApplicationContext(), "Failed to connect to server, data might be outdated.", Toast.LENGTH_LONG);
            toast.show();
            e.printStackTrace();
        }
        //Łódź
//        latitude = 19.45303;
//        altitude = 51.77665;
        astronomyCalculator = new AstronomyCalculator(advancedWeather.getLon(), advancedWeather.getLat());
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
    public double getLatitude() {
        return latitude;
    }
    public double getAltitude() {
        return altitude;
    }
}