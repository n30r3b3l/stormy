package com.davidwestberry.stormy.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.davidwestberry.stormy.Model.Forecast;
import com.davidwestberry.stormy.Model.Weather;

import java.util.ArrayList;

/**
 * Created by davidw on 7/28/2015.
 */

/**
 * This Class is used for saving information to the CurrentWeather table and the ForecastWeather
 * table. It also contains a method for deleting all rows from each of those tables.
 */
public class WeatherPersister {

    public static final String TAG = WeatherPersister.class.getSimpleName();
    private static CurrentWeatherDbHelper mCurrentWeatherDbHelper;
    private static DailyForecastDbHelper mForecastDbHelper;
    public static final int DELETE_SUCCESSFUL = 0;
    public static final int DELETE_FAILED = 1;

    public static void SaveCurrentWeather(Context context, Weather weather) {

        mCurrentWeatherDbHelper = new CurrentWeatherDbHelper(context);
        SQLiteDatabase db = mCurrentWeatherDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.CurrentWeather.COLUMN_NAME_CITY_NAME, weather.location.getCity());
        values.put(Contract.CurrentWeather.COLUMN_NAME_SUNRISE, weather.location.getSunrise());
        values.put(Contract.CurrentWeather.COLUMN_NAME_SUNSET, weather.location.getSunset());
        values.put(Contract.CurrentWeather.COLUMN_NAME_TEMP, String.valueOf(weather.temperature.getTemp()));
        values.put(Contract.CurrentWeather.COLUMN_NAME_DT, weather.getDatetime());
        values.put(Contract.CurrentWeather.COLUMN_NAME_HUMIDITY, weather.summary.getHumidity());
        values.put(Contract.CurrentWeather.COLUMN_NAME_WEATHER_DESC, weather.summary.getDescr());
        //values.put(Contract.CurrentWeather.COLUMN_NAME_WEATHER_ICON, current.getIcon());
        db.insert(Contract.CurrentWeather.TABLE_NAME, null, values);
        db.close();

    }

    /**
     * This method saves the Forecast data to the DailyForecast table in the local SQLite database
     *
     * @param context
     * @param forecast a Forecast object
     */
    public void SaveWeatherForecast(Context context, Forecast forecast) {

        mForecastDbHelper = new DailyForecastDbHelper(context);
        SQLiteDatabase db = mForecastDbHelper.getWritableDatabase();

        ArrayList<Weather> weathers = forecast.weatherArray;

        for (Weather weather : weathers) {

            ContentValues values = new ContentValues();

            values.put(Contract.DailyForecast.COLUMN_NAME_HIGH_TEMP, weather.temperature.getMaxTemp());
            values.put(Contract.DailyForecast.COLUMN_NAME_LOW_TEMP, weather.temperature.getMinTemp());
            values.put(Contract.DailyForecast.COLUMN_NAME_HUMIDITY, weather.summary.getHumidity());
            values.put(Contract.DailyForecast.COLUMN_NAME_WEATHER_DESC, weather.summary.getDescr());
            values.put(Contract.DailyForecast.COLUMN_NAME_DT, weather.getDatetime());

            db.insert(Contract.DailyForecast.TABLE_NAME, null, values);
            db.close();
        }
    }

    /**
     * This method is used to delete all rows in the CurrentWeather and DailyForecast tables.
     *
     * @param context
     * @return an int to let the caller know if the delete was successful.
     */
    public static int DeleteAll(Context context) {

        try {
            mCurrentWeatherDbHelper = new CurrentWeatherDbHelper(context);
            SQLiteDatabase cdb = mCurrentWeatherDbHelper.getWritableDatabase();

            //mForecastDbHelper = new DailyForecastDbHelper(context);
            //SQLiteDatabase fdb = mForecastDbHelper.getWritableDatabase();

            String deleteCurrent = "DELETE FROM " + Contract.CurrentWeather.TABLE_NAME;
            String deleteForecast = "DELETE FROM " + Contract.DailyForecast.TABLE_NAME;

            //cdb.rawQuery(deleteCurrent, null);
            int currentRowsDeleted = cdb.delete(Contract.CurrentWeather.TABLE_NAME, "1", null);
            //fdb.rawQuery(deleteForecast, null);

            Log.d(TAG, "Deleted " + currentRowsDeleted + " rows from the current_weather table");

            return DELETE_SUCCESSFUL;

        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
            return DELETE_FAILED;
        }
    }
}
