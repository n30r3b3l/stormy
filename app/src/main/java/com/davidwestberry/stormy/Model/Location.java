package com.davidwestberry.stormy.Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by DavidW on 7/27/2015.
 */
public class Location implements Serializable {

    private long sunrise;
    private long sunset;
    private String country;
    private String city;

    public long getSunrise() {
        return sunrise;
    }

    public String getPrettySunrise() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getDefault());
        Date datetime = new Date(getSunrise() * 1000);
        String timeString = formatter.format(datetime);

        return timeString;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public String getPrettySunset() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getDefault());
        Date datetime = new Date(getSunset() * 1000);
        String timeString = formatter.format(datetime);

        return timeString;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
