package com.example.navbar;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.navbar.ui.forecast.ForecastViewModel;
import com.example.navbar.ui.moon.MoonViewModel;
import com.example.navbar.ui.sun.SunViewModel;
import com.example.navbar.ui.weather.WeatherViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static AstronomyCalculator astronomyCalculator;
    public static double latitude = 0.0, altitude = 0.0;
    public static Double refreshRate = 60.0 * 60.0; //1 godzina

    public static String unit = "C";

    private final String API_KEY = "eeda7f9dbb9ad0575f297a1ffbd7ef59";
    private final String WEATHER_API_PATH = "http://api.openweathermap.org/data/2.5/weather";
    private final String FORECAST_API_PATH = "https://api.openweathermap.org/data/2.5/onecall";
    private String exclude = "current,minutely,hourly,alerts";
    private String units = "metric";
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private String city = "Głowno";
    public Double lon;
    private Double lat;
    private String description;
    private Integer temp;
    private Integer tempMin;
    private Integer tempMax;
    private Integer pressure;
    private Integer wind;
    private Integer humidity;
    SingletonQueue requestQueue;
    JSONObject jsonResponse;
    JSONObject jsonResponseForecast;
    String FILE_NAME = "weatherData.json";

    ForecastViewModel forecastViewModel;
    WeatherViewModel weatherViewModel;
    MoonViewModel moonViewModel;
    SunViewModel sunViewModel;

    ToggleButton toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);


        toggle = (ToggleButton) findViewById(R.id.unitsSwitch);


        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        forecastViewModel = new ViewModelProvider(this).get(ForecastViewModel.class);
        sunViewModel = new ViewModelProvider(this).get(SunViewModel.class);
        moonViewModel = new ViewModelProvider(this).get(MoonViewModel.class);
        requestQueue = SingletonQueue.getInstance(getApplicationContext());
    }

    public void setOnCheckedChangeListener(View view) {
        toggle = (ToggleButton) findViewById(R.id.unitsSwitch);
        if (toggle.isChecked()) {
            unit = "F";

        } else {
            unit = "C";
        }
    }



    public void useDefaultCoordinates(View view) {
        getWeatherDetails();
    }

    public void getWeatherDetails() {
        String query = WEATHER_API_PATH + "?q=" + city + "&units=metric&APPID=" + API_KEY;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, query,
                response -> {
                    try {
                        jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        JSONObject jsonObjectCords = jsonResponse.getJSONObject("coord");

                        lon = jsonObjectCords.getDouble("lon");
                        lat = jsonObjectCords.getDouble("lat");
                        description = jsonObjectWeather.getString("description");
                        temp = jsonObjectMain.getInt("temp");
                        tempMin = jsonObjectMain.getInt("temp_min");
                        tempMax = jsonObjectMain.getInt("temp_max");
                        pressure = jsonObjectMain.getInt("pressure");
                        wind = jsonObjectWind.getInt("speed");
                        humidity = jsonObjectMain.getInt("humidity");
                        System.out.println(jsonResponse);


                        sunViewModel.setCity(new MutableLiveData<>(city));
                        sunViewModel.setLat(new MutableLiveData<>(lat));
                        sunViewModel.setLon(new MutableLiveData<>(lon));

                        moonViewModel.setCity(new MutableLiveData<>(city));
                        moonViewModel.setLat(new MutableLiveData<>(lat));
                        moonViewModel.setLon(new MutableLiveData<>(lon));

                        forecastViewModel.setCity(new MutableLiveData<>(city));
                        weatherViewModel.setCity(new MutableLiveData<>(city));
                        weatherViewModel.setDescription(new MutableLiveData<>(description));
                        weatherViewModel.setTemp(new MutableLiveData<>(temp));
                        weatherViewModel.setTempMin(new MutableLiveData<>(tempMin));
                        weatherViewModel.setTempMax(new MutableLiveData<>(tempMax));
                        weatherViewModel.setPressure(new MutableLiveData<>(pressure));
                        weatherViewModel.setWind(new MutableLiveData<>(wind));
                        weatherViewModel.setHumidity(new MutableLiveData<>(humidity));


                        astronomyCalculator = new AstronomyCalculator(lon, lat);
                        creteMainFile();

                        createForecastRequest();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }, error -> {

            String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
            try {
                JSONObject data = new JSONObject(responseBody);
                String message = data.optString("message");
                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);

                toast.show();
                error.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        requestQueue.addToRequestQueue(stringRequest);
    }

    public void createForecastRequest() {
        String forecastQuery = FORECAST_API_PATH + "?lat=" + lat +
                "&lon=" + lon + "&exclude=" + exclude + "&units=" + units + "&APPID=" + API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, forecastQuery,
                response -> {
                    try {
                        jsonResponseForecast = new JSONObject(response);
                        JSONArray jsonArray = jsonResponseForecast.getJSONArray("daily");

                        Integer temp1 = jsonArray.getJSONObject(0).getJSONObject("temp").getInt("max");
                        forecastViewModel.setDayOneTemp(new MutableLiveData<>(temp1));
                        Integer temp2 = jsonArray.getJSONObject(1).getJSONObject("temp").getInt("max");
                        forecastViewModel.setDayTwoTemp(new MutableLiveData<>(temp2));
                        Integer temp3 = jsonArray.getJSONObject(2).getJSONObject("temp").getInt("max");
                        forecastViewModel.setDayThreeTemp(new MutableLiveData<>(temp3));
                        Integer temp4 = jsonArray.getJSONObject(3).getJSONObject("temp").getInt("max");
                        forecastViewModel.setDayFourTemp(new MutableLiveData<>(temp4));
                        Integer temp5 = jsonArray.getJSONObject(4).getJSONObject("temp").getInt("max");
                        forecastViewModel.setDayFiveTemp(new MutableLiveData<>(temp5));
                        Integer temp6 = jsonArray.getJSONObject(5).getJSONObject("temp").getInt("max");
                        forecastViewModel.setDaySixTemp(new MutableLiveData<>(temp6));
                        Integer temp7 = jsonArray.getJSONObject(6).getJSONObject("temp").getInt("max");
                        forecastViewModel.setDaySevenTemp(new MutableLiveData<>(temp7));

                        String icon1 = jsonArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");
                        forecastViewModel.setDayOneIcon(new MutableLiveData<>(icon1));
                        String icon2 = jsonArray.getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("icon");
                        forecastViewModel.setDayTwoIcon(new MutableLiveData<>(icon2));
                        String icon3 = jsonArray.getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("icon");
                        forecastViewModel.setDayThreeIcon(new MutableLiveData<>(icon3));
                        String icon4 = jsonArray.getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("icon");
                        forecastViewModel.setDayFourIcon(new MutableLiveData<>(icon4));
                        String icon5 = jsonArray.getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("icon");
                        forecastViewModel.setDayFiveIcon(new MutableLiveData<>(icon5));
                        String icon6 = jsonArray.getJSONObject(5).getJSONArray("weather").getJSONObject(0).getString("icon");
                        forecastViewModel.setDaySixIcon(new MutableLiveData<>(icon6));
                        String icon7 = jsonArray.getJSONObject(6).getJSONArray("weather").getJSONObject(0).getString("icon");
                        forecastViewModel.setDaySevenIcon(new MutableLiveData<>(icon7));

                        long epoch1 = jsonArray.getJSONObject(0).getLong("dt");
                        String day1 = new SimpleDateFormat("EEE").format(new Date(epoch1));
                        forecastViewModel.setDayOneName(new MutableLiveData<>(day1));
                        long epoch2 = jsonArray.getJSONObject(0).getLong("dt");
                        String day2 = new SimpleDateFormat("EEE").format(new Date(epoch2));
                        forecastViewModel.setDayOneName(new MutableLiveData<>(day2));
                        long epoch3 = jsonArray.getJSONObject(0).getLong("dt");
                        String day3 = new SimpleDateFormat("EEE").format(new Date(epoch3));
                        forecastViewModel.setDayOneName(new MutableLiveData<>(day3));
                        long epoch4 = jsonArray.getJSONObject(0).getLong("dt");
                        String day4 = new SimpleDateFormat("EEE").format(new Date(epoch4));
                        forecastViewModel.setDayOneName(new MutableLiveData<>(day4));
                        long epoch5 = jsonArray.getJSONObject(0).getLong("dt");
                        String day5 = new SimpleDateFormat("EEE").format(new Date(epoch5));
                        forecastViewModel.setDayOneName(new MutableLiveData<>(day5));
                        long epoch6 = jsonArray.getJSONObject(0).getLong("dt");
                        String day6 = new SimpleDateFormat("EEE").format(new Date(epoch6));
                        forecastViewModel.setDayOneName(new MutableLiveData<>(day6));
                        long epoch7 = jsonArray.getJSONObject(0).getLong("dt");
                        String day7 = new SimpleDateFormat("EEE").format(new Date(epoch7));
                        forecastViewModel.setDayOneName(new MutableLiveData<>(day7));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast toast = Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
            toast.show();
            error.printStackTrace();
        });

        requestQueue.addToRequestQueue(stringRequest);
    }

    public void creteMainFile() throws IOException {
        String fileLocation = this.getCacheDir().toString() + FILE_NAME;
        PrintWriter out = new PrintWriter(new FileWriter(fileLocation));
        //System.out.println(fileLocation);
        out.write(jsonResponse.toString());
        out.close();
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

    public double getLatitude() {
        return latitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public String getCity() {
        return city;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }


}