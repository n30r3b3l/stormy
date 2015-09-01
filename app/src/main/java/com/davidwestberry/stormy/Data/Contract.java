package com.davidwestberry.stormy.Data;

import android.provider.BaseColumns;

/**
 * Created by DavidW on 5/20/2015.
 */
public class Contract {
    public static final int DATABASE_VERSION = 1;
    public static final String CURRENT_WEATHER_DB_NAME = "CurrentWeather.db";
    public static final String DAILY_FORECAST_DB_NAME = "DailyForecast.db";

    private Contract() {
    }

    public static abstract class CurrentWeather implements BaseColumns {
        public static final String TABLE_NAME = "current_weather";
        public static final String COLUMN_NAME_CITY_NAME = "city_name";
        public static final String COLUMN_NAME_DT = "dt";
        public static final String COLUMN_NAME_SUNRISE = "sunrise";
        public static final String COLUMN_NAME_SUNSET = "sunset";
        public static final String COLUMN_NAME_WIND_SPEED = "wind_speed";
        public static final String COLUMN_NAME_WIND_DIRECTION = "wind_direction";
        public static final String COLUMN_NAME_TEMP = "temp";
        public static final String COLUMN_NAME_HUMIDITY = "humidity";
        public static final String COLUMN_NAME_WEATHER_MAIN = "weather_main";
        public static final String COLUMN_NAME_WEATHER_ICON = "weather_icon";
        public static final String COLUMN_NAME_WEATHER_DESC = "weather_desc";
        public static final String COLUMN_NAME_WEATHER_ID = "weather_id";
        public static final String COLUMN_NAME_CLOUD_COVER = "cloud_cover";
        public static final String COLUMN_NAME_RAIN = "rain";
        public static final String COLUMN_NAME_SNOW = "snow";

        public static final String SQL_CREATE_CURRENT_WEATHER =
                "CREATE TABLE " + CurrentWeather.TABLE_NAME + " ("
                        + CurrentWeather._ID + " INTEGER PRIMARY KEY,"
                        + CurrentWeather.COLUMN_NAME_CITY_NAME + " TEXT" + ","
                        + CurrentWeather.COLUMN_NAME_DT + " INTEGER" + ","
                        + CurrentWeather.COLUMN_NAME_SUNRISE + " INTEGER" + ","
                        + CurrentWeather.COLUMN_NAME_SUNSET + " INTEGER" + ","
                        + CurrentWeather.COLUMN_NAME_WIND_SPEED + " REAL" + ","
                        + CurrentWeather.COLUMN_NAME_WIND_DIRECTION + " REAL" + ","
                        + CurrentWeather.COLUMN_NAME_TEMP + " REAL" + ","
                        + CurrentWeather.COLUMN_NAME_HUMIDITY + " INTEGER" + ","
                        + CurrentWeather.COLUMN_NAME_WEATHER_MAIN + " TEXT" + ","
                        + CurrentWeather.COLUMN_NAME_WEATHER_ICON + " TEXT" + ","
                        + CurrentWeather.COLUMN_NAME_WEATHER_DESC + " TEXT" + ","
                        + CurrentWeather.COLUMN_NAME_WEATHER_ID + " INTEGER" + ","
                        + CurrentWeather.COLUMN_NAME_CLOUD_COVER + " INTEGER" + ","
                        + CurrentWeather.COLUMN_NAME_RAIN + " REAL" + ","
                        + CurrentWeather.COLUMN_NAME_SNOW + " REAL"
                        + " )";

        public static final String SQL_DELETE_CURRENT_WEATHER =
                "DROP TABLE IF EXISTS " + CurrentWeather.TABLE_NAME;
    }

    public static abstract class DailyForecast implements BaseColumns {
        public static final String TABLE_NAME = "daily_forecast";
        public static final String COLUMN_NAME_CITY_NAME = "city_name";
        public static final String COLUMN_NAME_DT = "dt";
        public static final String COLUMN_NAME_WIND_SPEED = "wind_speed";
        public static final String COLUMN_NAME_WIND_DIRECTION = "wind_direction";
        public static final String COLUMN_NAME_HIGH_TEMP = "high_temp";
        public static final String COLUMN_NAME_LOW_TEMP = "low_temp";
        public static final String COLUMN_NAME_HUMIDITY = "humidity";
        public static final String COLUMN_NAME_WEATHER_MAIN = "weather_main";
        public static final String COLUMN_NAME_WEATHER_ICON = "weather_icon";
        public static final String COLUMN_NAME_WEATHER_DESC = "weather_desc";
        public static final String COLUMN_NAME_WEATHER_ID = "weather_id";
        public static final String COLUMN_NAME_CLOUD_COVER = "cloud_cover";
        public static final String COLUMN_NAME_RAIN = "rain";
        public static final String COLUMN_NAME_SNOW = "snow";

        public static final String SQL_CREATE_DAILY_FORECAST =
                "CREATE TABLE " + CurrentWeather.TABLE_NAME + " ("
                        + DailyForecast._ID + " INTEGER PRIMARY KEY,"
                        + DailyForecast.COLUMN_NAME_CITY_NAME + " TEXT" + ","
                        + DailyForecast.COLUMN_NAME_DT + " INTEGER" + ","
                        + DailyForecast.COLUMN_NAME_WIND_SPEED + " REAL" + ","
                        + DailyForecast.COLUMN_NAME_WIND_DIRECTION + " REAL" + ","
                        + DailyForecast.COLUMN_NAME_HIGH_TEMP + " REAL" + ","
                        + DailyForecast.COLUMN_NAME_LOW_TEMP + " REAL" + ","
                        + DailyForecast.COLUMN_NAME_HUMIDITY + " INTEGER" + ","
                        + DailyForecast.COLUMN_NAME_WEATHER_MAIN + " TEXT" + ","
                        + DailyForecast.COLUMN_NAME_WEATHER_ICON + " TEXT" + ","
                        + DailyForecast.COLUMN_NAME_WEATHER_DESC + " TEXT" + ","
                        + DailyForecast.COLUMN_NAME_WEATHER_ID + " INTEGER" + ","
                        + DailyForecast.COLUMN_NAME_CLOUD_COVER + " INTEGER" + ","
                        + DailyForecast.COLUMN_NAME_RAIN + " REAL" + ","
                        + DailyForecast.COLUMN_NAME_SNOW + " REAL"
                        + " )";

        public static final String SQL_DELETE_DAILY_FORECAST=
                "DROP TABLE IF EXISTS " + DailyForecast.TABLE_NAME;
    }
}
