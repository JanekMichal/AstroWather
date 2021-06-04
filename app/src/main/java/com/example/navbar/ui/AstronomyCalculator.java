package com.example.navbar.ui;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.time.LocalDateTime;

public class AstronomyCalculator {
    private AstroCalculator astroCalculator;
    private AstroCalculator.Location location;

    public AstronomyCalculator(double altitude, double latitude) {

        location = new AstroCalculator.Location(altitude, latitude);
        AstroDateTime astroDateTime = new AstroDateTime(
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue(),
                LocalDateTime.now().getDayOfMonth(),
                LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond(),
                1,
                true
        );

        astroCalculator = new AstroCalculator(astroDateTime, location);
    }

    public String getSunRiseInfo() {
        return astroCalculator.getSunInfo().getSunrise().getHour() + ":" +
                astroCalculator.getSunInfo().getSunrise().getMinute() + ":" +
                astroCalculator.getSunInfo().getSunrise().getSecond();
    }

    public String getSunRiseInfoWithDay() {
        return trimGMT(astroCalculator.getSunInfo().getSunrise().toString());
    }

    public String getSunSetInfo() {
        return astroCalculator.getSunInfo().getSunset().getHour() + ":" +
                astroCalculator.getSunInfo().getSunset().getMinute() + ":" +
                astroCalculator.getSunInfo().getSunset().getSecond();
    }

    public String getSunSetInfoWithDay() {
        return trimGMT(astroCalculator.getSunInfo().getSunset().toString());
    }

    public double getSunRiseAzimuthInfo() {
        return astroCalculator.getSunInfo().getAzimuthRise();
    }

    public double getSunSetAzimuthInfo() {
        return astroCalculator.getSunInfo().getAzimuthSet();
    }

    public String getCivilTwilightMorning() {
        return astroCalculator.getSunInfo().getTwilightMorning().getHour() + ":" +
                astroCalculator.getSunInfo().getTwilightMorning().getMinute() + ":" +
                astroCalculator.getSunInfo().getTwilightMorning().getMinute();
    }

    public String getCivilTwilightMorningWithDay() {
        return trimGMT(astroCalculator.getSunInfo().getTwilightMorning().toString());
    }

    public String getCivilTwilightEvening() {
        return astroCalculator.getSunInfo().getTwilightEvening().getHour() + ":" +
                astroCalculator.getSunInfo().getTwilightEvening().getMinute() + ":" +
                astroCalculator.getSunInfo().getTwilightEvening().getMinute();
    }

    public String getCivilTwilightEveningWithDay() {
        return trimGMT(astroCalculator.getSunInfo().getTwilightEvening().toString());
    }

    public String trimGMT(String info) {
        int endTrim = info.indexOf("GMT");
        return info.substring(0, endTrim);
    }

    public String getMoonRiseInfo() {
        return astroCalculator.getMoonInfo().getMoonrise().getHour() + ":" +
                astroCalculator.getMoonInfo().getMoonrise().getMinute() + ":" +
                astroCalculator.getMoonInfo().getMoonrise().getSecond();
    }

    public String getMoonRiseInfoWithDay() {
        return trimGMT(astroCalculator.getMoonInfo().getMoonrise().toString());
    }

    public String getMoonSetInfo() {
        return astroCalculator.getMoonInfo().getMoonset().getHour() + ":" +
                astroCalculator.getMoonInfo().getMoonset().getMinute() + ":" +
                astroCalculator.getMoonInfo().getMoonset().getSecond();
    }

    public String getMoonSetInfoWithDay() {
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

    public double getAltitude() {
        return location.getLatitude();
    }

    public double getLatitude() {
        return location.getLongitude();
    }

}
