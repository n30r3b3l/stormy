package com.davidwestberry.stormy.Weather;

import com.davidwestberry.stormy.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by davidw on 3/2/2015.
 */
public class Current {
    private String mIcon;
    private String mCityName;
    private long mTime;
    private long mSunrise;
    private long mSunset;
    private double mWindSpeed;
    private double mWindDirection;
    private double mTemperature;
    private double mHumidity;
    private String mWeatherMain;
    private String mWeatherDesc;
    private int mWeatherId;
    private TimeZone mTimeZone;

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public long getSunrise() {
        return mSunrise;
    }

    public String getPrettySunrise(){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm", Locale.getDefault());
        formatter.setTimeZone(mTimeZone);
        Date datetime = new Date(getSunrise());
        String timeString = formatter.format(datetime);

        return timeString;
    }

    public void setSunrise(long sunrise) {
        mSunrise = sunrise;
    }

    public long getSunset() {
        return mSunset;
    }

    public String getPrettySunset(){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm", Locale.getDefault());
        formatter.setTimeZone(mTimeZone);
        Date datetime = new Date(getSunset());
        String timeString = formatter.format(datetime);

        return timeString;
    }

    public void setSunset(long sunset) {
        mSunset = sunset;
    }

    public double getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        mWindSpeed = windSpeed;
    }

    public double getWindDirection() {
        return mWindDirection;
    }

    public void setWindDirection(double windDirection) {
        mWindDirection = windDirection;
    }

    public String getWeatherMain() {
        return mWeatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        mWeatherMain = weatherMain;
    }

    public String getWeatherDesc() {
        return mWeatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        mWeatherDesc = weatherDesc;
    }

    public int getWeatherId() {
        return mWeatherId;
    }

    public void setWeatherId(int weatherId) {
        mWeatherId = weatherId;
    }

    public TimeZone getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        mTimeZone = timeZone;
    }

    public int getIcon() {
        return Forecast.getIconId(mIcon);
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
    }

    public String getPrettyTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(mTimeZone);
        Date datetime = new Date(getTime() * 1000);
        String timeString = formatter.format(datetime);

        return timeString;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getTemperature() {
        return (int)Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

}
