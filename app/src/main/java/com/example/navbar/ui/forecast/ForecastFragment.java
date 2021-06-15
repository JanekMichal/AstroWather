package com.example.navbar.ui.forecast;

import android.content.Context;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.navbar.MainActivity;
import com.example.navbar.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ForecastFragment extends Fragment {

    private ForecastViewModel forecastViewModel;

    TextView deviceTimeTextView;
    TextView cityTextView;

    TextView dayOneTextView;
    TextView dayTwoTextView;
    TextView dayThreeTextView;
    TextView dayFourTextView;
    TextView dayFiveTextView;
    TextView daySixTextView;
    TextView daySevenTextView;

    TextView dayOneValueTextView;
    TextView dayTwoValueTextView;
    TextView dayThreeValueTextView;
    TextView dayFourValueTextView;
    TextView dayFiveValueTextView;
    TextView daySixValueTextView;
    TextView daySevenValueTextView;

    ImageView dayOneIcon;
    ImageView dayTwoIcon;
    ImageView dayThreeIcon;
    ImageView dayFourIcon;
    ImageView dayFiveIcon;
    ImageView daySixIcon;
    ImageView daySevenIcon;

    String currentTime;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        forecastViewModel = new ViewModelProvider(requireActivity()).get(ForecastViewModel.class);
        View root = inflater.inflate(R.layout.fragment_forecast, container, false);

        dayOneTextView = root.findViewById(R.id.forecast_day_1);
        dayTwoTextView = root.findViewById(R.id.forecast_day_2);
        dayThreeTextView = root.findViewById(R.id.forecast_day_3);
        dayFourTextView = root.findViewById(R.id.forecast_day_4);
        dayFiveTextView = root.findViewById(R.id.forecast_day_5);
        daySixTextView = root.findViewById(R.id.forecast_day_6);
        daySevenTextView = root.findViewById(R.id.forecast_day_7);


        dayOneValueTextView = root.findViewById(R.id.forecast_day_1_value);
        dayTwoValueTextView = root.findViewById(R.id.forecast_day_2_value);
        dayThreeValueTextView = root.findViewById(R.id.forecast_day_3_value);
        dayFourValueTextView = root.findViewById(R.id.forecast_day_4_value);
        dayFiveValueTextView = root.findViewById(R.id.forecast_day_5_value);
        daySixValueTextView = root.findViewById(R.id.forecast_day_6_value);
        daySevenValueTextView = root.findViewById(R.id.forecast_day_7_value);


        dayOneIcon = root.findViewById(R.id.forecast_day_1_icon);
        dayTwoIcon = root.findViewById(R.id.forecast_day_2_icon);
        dayThreeIcon = root.findViewById(R.id.forecast_day_3_icon);
        dayFourIcon = root.findViewById(R.id.forecast_day_4_icon);
        dayFiveIcon = root.findViewById(R.id.forecast_day_5_icon);
        daySixIcon = root.findViewById(R.id.forecast_day_6_icon);
        daySevenIcon = root.findViewById(R.id.forecast_day_7_icon);


        deviceTimeTextView = root.findViewById(R.id.text_device_time_forecast);
        cityTextView = root.findViewById(R.id.text_city_forecast);

        forecastViewModel.getText().observe(getViewLifecycleOwner(), s -> {

            if (MainActivity.astronomyCalculator == null) {
                deviceTimeTextView.setText(R.string.settings_not_set);
            } else {
                if (MainActivity.unit.equals("C")) {
                    dayOneValueTextView.setText(forecastViewModel.getDayOneTemp().getValue() + "°C");
                    dayTwoValueTextView.setText(forecastViewModel.getDayTwoTemp().getValue() + "°C");
                    dayThreeValueTextView.setText(forecastViewModel.getDayThreeTemp().getValue() + "°C");
                    dayFourValueTextView.setText(forecastViewModel.getDayFourTemp().getValue() + "°C");
                    dayFiveValueTextView.setText(forecastViewModel.getDayFiveTemp().getValue() + "°C");
                    daySixValueTextView.setText(forecastViewModel.getDaySixTemp().getValue() + "°C");
                    daySevenValueTextView.setText(forecastViewModel.getDaySevenTemp().getValue() + "°C");
                } else {
                    dayOneValueTextView.setText(forecastViewModel.getDayOneTemp().getValue() * 1.8 + 32 + "°F");
                    dayTwoValueTextView.setText(forecastViewModel.getDayTwoTemp().getValue() * 1.8 + 32 + "°F");
                    dayThreeValueTextView.setText(forecastViewModel.getDayThreeTemp().getValue() * 1.8 + 32 + "°F");
                    dayFourValueTextView.setText(forecastViewModel.getDayFourTemp().getValue() * 1.8 + 32 + "°F");
                    dayFiveValueTextView.setText(forecastViewModel.getDayFiveTemp().getValue() * 1.8 + 32 + "°F");
                    daySixValueTextView.setText(forecastViewModel.getDaySixTemp().getValue() * 1.8 + 32 + "°F");
                    daySevenValueTextView.setText(forecastViewModel.getDaySevenTemp().getValue()* 1.8 + 32 + "°F");
                }

                setWeatherIcon(dayOneIcon, forecastViewModel.getDayOneIcon().getValue());
                setWeatherIcon(dayTwoIcon, forecastViewModel.getDayTwoIcon().getValue());
                setWeatherIcon(dayThreeIcon, forecastViewModel.getDayThreeIcon().getValue());
                setWeatherIcon(dayFourIcon, forecastViewModel.getDayFourIcon().getValue());
                setWeatherIcon(dayFiveIcon, forecastViewModel.getDayFiveIcon().getValue());
                setWeatherIcon(daySixIcon, forecastViewModel.getDaySixIcon().getValue());
                setWeatherIcon(daySevenIcon, forecastViewModel.getDaySevenIcon().getValue());


                dayOneTextView.setText(forecastViewModel.getDayOneName().getValue());
                dayTwoTextView.setText(forecastViewModel.getDayOneName().getValue());
                dayThreeTextView.setText(forecastViewModel.getDayOneName().getValue());
                dayFourTextView.setText(forecastViewModel.getDayOneName().getValue());
                dayFiveTextView.setText(forecastViewModel.getDayOneName().getValue());
                daySixTextView.setText(forecastViewModel.getDayOneName().getValue());
                daySevenTextView.setText(forecastViewModel.getDayOneName().getValue());

                cityTextView.setText(forecastViewModel.getCity().getValue());

                runTimers();
                updateTime();

            }
        });
        return root;
    }


    public void setWeatherIcon(ImageView imageView, String whichIcon) {
        switch (whichIcon) {
            case "01d":
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable._01d));
                break;
            case "02d":
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable._02d));
                break;
            case "03d":
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable._03d));
                break;
            case "04d":
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable._04d));
                break;
            case "09d":
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable._09d));
                break;
            case "10d":
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable._10d));
                break;
            case "11d":
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable._11d));
                break;
            case "13d":
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable._13d));
                break;
            case "50d":
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable._50d));
                break;
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

                handler.postDelayed(this, MainActivity.refreshRate.longValue() * 1000);
            }
        };
        handler.postDelayed(updateTask, MainActivity.refreshRate.longValue() * 1000);
    }

    public void updateTime() {
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        deviceTimeTextView.setText(currentTime);
    }
}