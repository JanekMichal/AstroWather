package com.example.navbar.ui.forecast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ForecastViewModel extends ViewModel {

    private MutableLiveData<Integer> dayOneTemp;
    private MutableLiveData<Integer> dayTwoTemp;
    private MutableLiveData<Integer> dayThreeTemp;
    private MutableLiveData<Integer> dayFourTemp;
    private MutableLiveData<Integer> dayFiveTemp;
    private MutableLiveData<Integer> daySixTemp;
    private MutableLiveData<Integer> daySevenTemp;

    private MutableLiveData<String> dayOneIcon;
    private MutableLiveData<String> dayTwoIcon;
    private MutableLiveData<String> dayThreeIcon;
    private MutableLiveData<String> dayFourIcon;
    private MutableLiveData<String> dayFiveIcon;
    private MutableLiveData<String> daySixIcon;
    private MutableLiveData<String> daySevenIcon;

    private MutableLiveData<String> dayOneName;
    private MutableLiveData<String> dayTwoName;
    private MutableLiveData<String> dayThreeName;
    private MutableLiveData<String> dayFourName;
    private MutableLiveData<String> dayFiveName;
    private MutableLiveData<String> daySixName;
    private MutableLiveData<String> daySevenName;

    private MutableLiveData<String> city;
    private MutableLiveData<String> mText;

    public ForecastViewModel() {

        dayOneTemp = new MutableLiveData<>();
        dayTwoTemp = new MutableLiveData<>();
        dayThreeTemp = new MutableLiveData<>();
        dayFourTemp = new MutableLiveData<>();
        dayFiveTemp = new MutableLiveData<>();
        daySixTemp = new MutableLiveData<>();
        daySevenTemp = new MutableLiveData<>();

        dayOneIcon = new MutableLiveData<>();
        dayTwoIcon = new MutableLiveData<>();
        dayThreeIcon = new MutableLiveData<>();
        dayFourIcon = new MutableLiveData<>();
        dayFiveIcon = new MutableLiveData<>();
        daySixIcon = new MutableLiveData<>();
        daySevenIcon = new MutableLiveData<>();

        dayOneName = new MutableLiveData<>();
        dayTwoName = new MutableLiveData<>();
        dayThreeName = new MutableLiveData<>();
        dayFourName = new MutableLiveData<>();
        dayFiveName = new MutableLiveData<>();
        daySixName = new MutableLiveData<>();
        daySevenName = new MutableLiveData<>();

        city = new MutableLiveData<>();
        mText = new MutableLiveData<>();
        mText.setValue("This is forecast fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<Integer> getDayOneTemp() {
        return dayOneTemp;
    }

    public void setDayOneTemp(MutableLiveData<Integer> dayOneTemp) {
        this.dayOneTemp = dayOneTemp;
    }

    public MutableLiveData<Integer> getDayTwoTemp() {
        return dayTwoTemp;
    }

    public void setDayTwoTemp(MutableLiveData<Integer> dayTwoTemp) {
        this.dayTwoTemp = dayTwoTemp;
    }

    public MutableLiveData<Integer> getDayThreeTemp() {
        return dayThreeTemp;
    }

    public void setDayThreeTemp(MutableLiveData<Integer> dayThreeTemp) {
        this.dayThreeTemp = dayThreeTemp;
    }

    public MutableLiveData<Integer> getDayFourTemp() {
        return dayFourTemp;
    }

    public void setDayFourTemp(MutableLiveData<Integer> dayFourTemp) {
        this.dayFourTemp = dayFourTemp;
    }

    public MutableLiveData<Integer> getDayFiveTemp() {
        return dayFiveTemp;
    }

    public void setDayFiveTemp(MutableLiveData<Integer> dayFiveTemp) {
        this.dayFiveTemp = dayFiveTemp;
    }

    public MutableLiveData<Integer> getDaySixTemp() {
        return daySixTemp;
    }

    public void setDaySixTemp(MutableLiveData<Integer> daySixTemp) {
        this.daySixTemp = daySixTemp;
    }

    public MutableLiveData<Integer> getDaySevenTemp() {
        return daySevenTemp;
    }

    public void setDaySevenTemp(MutableLiveData<Integer> daySevenTemp) {
        this.daySevenTemp = daySevenTemp;
    }

    public MutableLiveData<String> getDayOneIcon() {
        return dayOneIcon;
    }

    public void setDayOneIcon(MutableLiveData<String> dayOneIcon) {
        this.dayOneIcon = dayOneIcon;
    }

    public MutableLiveData<String> getDayTwoIcon() {
        return dayTwoIcon;
    }

    public void setDayTwoIcon(MutableLiveData<String> dayTwoIcon) {
        this.dayTwoIcon = dayTwoIcon;
    }

    public MutableLiveData<String> getDayThreeIcon() {
        return dayThreeIcon;
    }

    public void setDayThreeIcon(MutableLiveData<String> dayThreeIcon) {
        this.dayThreeIcon = dayThreeIcon;
    }

    public MutableLiveData<String> getDayFourIcon() {
        return dayFourIcon;
    }

    public void setDayFourIcon(MutableLiveData<String> dayFourIcon) {
        this.dayFourIcon = dayFourIcon;
    }

    public MutableLiveData<String> getDayFiveIcon() {
        return dayFiveIcon;
    }

    public void setDayFiveIcon(MutableLiveData<String> dayFiveIcon) {
        this.dayFiveIcon = dayFiveIcon;
    }

    public MutableLiveData<String> getDaySixIcon() {
        return daySixIcon;
    }

    public void setDaySixIcon(MutableLiveData<String> daySixIcon) {
        this.daySixIcon = daySixIcon;
    }

    public MutableLiveData<String> getDaySevenIcon() {
        return daySevenIcon;
    }

    public void setDaySevenIcon(MutableLiveData<String> daySevenIcon) {
        this.daySevenIcon = daySevenIcon;
    }

    public MutableLiveData<String> getDayOneName() {
        return dayOneName;
    }

    public void setDayOneName(MutableLiveData<String> dayOneName) {
        this.dayOneName = dayOneName;
    }

    public MutableLiveData<String> getDayTwoName() {
        return dayTwoName;
    }

    public void setDayTwoName(MutableLiveData<String> dayTwoName) {
        this.dayTwoName = dayTwoName;
    }

    public MutableLiveData<String> getDayThreeName() {
        return dayThreeName;
    }

    public void setDayThreeName(MutableLiveData<String> dayThreeName) {
        this.dayThreeName = dayThreeName;
    }

    public MutableLiveData<String> getDayFourName() {
        return dayFourName;
    }

    public void setDayFourName(MutableLiveData<String> dayFourName) {
        this.dayFourName = dayFourName;
    }

    public MutableLiveData<String> getDayFiveName() {
        return dayFiveName;
    }

    public void setDayFiveName(MutableLiveData<String> dayFiveName) {
        this.dayFiveName = dayFiveName;
    }

    public MutableLiveData<String> getDaySixName() {
        return daySixName;
    }

    public void setDaySixName(MutableLiveData<String> daySixName) {
        this.daySixName = daySixName;
    }

    public MutableLiveData<String> getDaySevenName() {
        return daySevenName;
    }

    public void setDaySevenName(MutableLiveData<String> daySevenName) {
        this.daySevenName = daySevenName;
    }

    public MutableLiveData<String> getCity() {
        return city;
    }

    public void setCity(MutableLiveData<String> city) {
        this.city = city;
    }
}