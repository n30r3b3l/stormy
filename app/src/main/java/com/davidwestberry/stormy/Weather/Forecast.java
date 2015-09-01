package com.davidwestberry.stormy.Weather;

import com.davidwestberry.stormy.R;

/**
 * Created by davidw on 3/5/2015.
 */
public class Forecast {
    private Current mCurrent;
    private Hour[] mHourlyForecast;
    private Day[] mDailyForecast;

    public Current getCurrent() {
        return mCurrent;
    }

    public void setCurrent(Current current) {
        mCurrent = current;
    }

    public Hour[] getHourlyForecast() {
        return mHourlyForecast;
    }

    public void setHourlyForecast(Hour[] hourlyForecast) {
        mHourlyForecast = hourlyForecast;
    }

    public Day[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(Day[] dailyForecast) {
        mDailyForecast = dailyForecast;
    }

    public static int getIconId(String iconString){
        int iconID = 0;

        if (iconString.equals("01d")) {
            iconID = R.drawable.clear_day;

        } else if (iconString.equals("01n")) {
            iconID = R.drawable.clear_night;

        } else if (iconString.equals("10d") || iconString.equals("10n")) {
            iconID = R.drawable.rain;

        } else if (iconString.equals("13d") || iconString.equals("13n")) {
            iconID = R.drawable.snow;

        } else if (iconString.equals("13d")) {
            iconID = R.drawable.sleet;

/*        } else if (iconString.equals("wind")) {
            iconID = R.drawable.wind;
*/
        } else if (iconString.equals("50d") || iconString.equals("50n")) {
            iconID = R.drawable.fog;

        } else if (iconString.equals("03d") || iconString.equals("03n")) {
            iconID = R.drawable.cloudy;

        } else if (iconString.equals("02d")) {
            iconID = R.drawable.partly_cloudy;

        } else if (iconString.equals("02n")) {
            iconID = R.drawable.cloudy_night;

        }

        return iconID;
    }
}

