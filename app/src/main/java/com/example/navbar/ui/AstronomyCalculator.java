package com.example.navbar.ui;

import android.util.Log;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class AstronomyCalculator {
    private AstroCalculator astroCalculator;
    private AstroCalculator.Location location;

    public AstronomyCalculator(double altitude, double latitude) {

        location = new AstroCalculator.Location(altitude, latitude);
        AstroDateTime astroDateTime = new AstroDateTime(
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue(),
                //TODO: zrobić automatyczne wybieranie strefy czasowej
                LocalDateTime.now().getDayOfMonth() + 1,
                LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond(),
                1,
                true
        );

        astroCalculator = new AstroCalculator(astroDateTime, location);
    }

    public String getSunRiseInfo() {
        return trimGMT(astroCalculator.getSunInfo().getSunrise().toString());
    }

    public String getSunSetInfo() {
        return trimGMT(astroCalculator.getSunInfo().getSunset().toString());
    }

    public double getSunRiseAzimuthInfo() {
        return astroCalculator.getSunInfo().getAzimuthRise();
    }

    public double getSunSetAzimuthInfo() {
        return astroCalculator.getSunInfo().getAzimuthSet();
    }

    public String getCivilTwilightMorning() {
        return trimGMT(astroCalculator.getSunInfo().getTwilightMorning().toString());
    }

    public String getCivilTwilightEvening() {
        return trimGMT(astroCalculator.getSunInfo().getTwilightEvening().toString());
    }

    public String trimGMT(String info) {
        int endTrim = info.indexOf("GMT");
        return info.substring(0, endTrim);
    }

    public String getMoonRiseInfo() {
        return trimGMT(astroCalculator.getMoonInfo().getMoonrise().toString());
    }

    public String getMoonSetInfo() {
        return trimGMT(astroCalculator.getMoonInfo().getMoonset().toString());
    }

    public String getNextNewMoon() {
        return trimGMT(astroCalculator.getMoonInfo().getNextNewMoon().toString());
    }

    public String getNextFullMoon() {
        return trimGMT(astroCalculator.getMoonInfo().getNextFullMoon().toString());
    }

    public double getMoonPhase() {
        return astroCalculator.getMoonInfo().getIllumination() * 100;
    }

    public void getNumberOfSonodicMounthDay() {
       //TODO: dowiedzieć się co to ma niby być XD
    }

    public double getAltitude() {
        return location.getLatitude();
    }

    public double getLatitude() {
        return location.getLongitude();
    }

}
