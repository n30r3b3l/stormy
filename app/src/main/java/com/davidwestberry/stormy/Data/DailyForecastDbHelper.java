package com.davidwestberry.stormy.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DavidW on 5/20/2015.
 */
public class DailyForecastDbHelper extends SQLiteOpenHelper {

    public DailyForecastDbHelper(Context context) {
        super(context, Contract.DAILY_FORECAST_DB_NAME, null, Contract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contract.DailyForecast.SQL_CREATE_DAILY_FORECAST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Contract.DailyForecast.SQL_DELETE_DAILY_FORECAST);
        onCreate(db);
    }
}
