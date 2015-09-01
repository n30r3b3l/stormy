package com.davidwestberry.stormy.Weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by davidw on 3/5/2015.
 */
public class Day implements Parcelable{
    private long mTime;
    private String mSummary;
    private double mTemperatureMax;
    private double mTemperatureMin;
    private String mIcon;
    private TimeZone mTimezone;

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getTemperatureMax() {
        return (int) Math.round(mTemperatureMax);
    }

    public void setTemperatureMax(double temperatureMax) {
        mTemperatureMax = temperatureMax;
    }

    public int getTemperatureMin() {
        return (int) Math.round(mTemperatureMin);
    }

    public void setTemperatureMin(double temperatureMin) {
        mTemperatureMin = temperatureMin;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimezone() {
        return mTimezone.getDisplayName();
    }

    public void setTimezone(TimeZone timezone) {
        mTimezone = timezone;
    }

    public int getIconId() {
        return Forecast.getIconId(mIcon);
    }

    public String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d", Locale.getDefault());
        formatter.setTimeZone(mTimezone);
        Date date = new Date(mTime*1000);
        return formatter.format(date);
    }

    public String getDayOfTheWeek(){
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE", Locale.getDefault());
        formatter.setTimeZone(mTimezone);
        Date dateTime = new Date(mTime * 1000);
        return formatter.format(dateTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTime);
        dest.writeString(mSummary);
        dest.writeDouble(mTemperatureMax);
        dest.writeDouble(mTemperatureMin);
        dest.writeString(mIcon);
        dest.writeString(mTimezone.getID());
    }

    private Day(Parcel in) {
        mTime = in.readLong();
        mSummary = in.readString();
        mTemperatureMax = in.readDouble();
        mTemperatureMin = in.readDouble();
        mIcon = in.readString();
        mTimezone = TimeZone.getTimeZone(in.readString());
    }
// Default constructor because the private constructor can't be used with parcelable data
    public Day() {}

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
}
