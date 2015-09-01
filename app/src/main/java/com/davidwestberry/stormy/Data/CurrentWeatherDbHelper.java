package com.davidwestberry.stormy.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.davidwestberry.stormy.Data.Contract.CurrentWeather;

/**
 * Created by DavidW on 5/20/2015.
 */
public class CurrentWeatherDbHelper extends SQLiteOpenHelper {

    public CurrentWeatherDbHelper(Context context) {
        super(context, Contract.CURRENT_WEATHER_DB_NAME, null, Contract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {db.execSQL(CurrentWeather.SQL_CREATE_CURRENT_WEATHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Contract.CurrentWeather.SQL_DELETE_CURRENT_WEATHER);
        onCreate(db);
    }
}
