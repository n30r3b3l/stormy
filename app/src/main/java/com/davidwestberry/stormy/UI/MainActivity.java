package com.davidwestberry.stormy.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.davidwestberry.stormy.Data.Contract;
import com.davidwestberry.stormy.Data.CurrentWeatherDbHelper;
import com.davidwestberry.stormy.Data.WeatherPersister;
import com.davidwestberry.stormy.JSONParser;
import com.davidwestberry.stormy.Model.Weather;
import com.davidwestberry.stormy.R;
import com.davidwestberry.stormy.Weather.Day;
import com.davidwestberry.stormy.Weather.Forecast;
import com.davidwestberry.stormy.Weather.Hour;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends Activity
        implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    public static final String HOURLY_FORECAST = "HOURLY_FORECAST";

    private long mRefreshedTime;
    private long mTenMinutesAgo = System.currentTimeMillis() - (1000 * 60 * 10);

    private static final String openWeatherApiKey = "db0623224de365456a490a4511459544";

    private GoogleApiClient mGoogleApiClient;

// Inject all of our views using ButterKnife
    @InjectView(R.id.sunriseTextView) TextView mSunrise;
    @InjectView(R.id.sunsetTextView) TextView mSunset;
    @InjectView(R.id.humidityValue) TextView mHumidityValue;
    @InjectView(R.id.locationLabel) TextView mLocationLabel;
    @InjectView(R.id.timeLabel) TextView mTimeLabel;
    @InjectView(R.id.temperatureLabel) TextView mTemperatureLabel;
    @InjectView(R.id.precipValue) TextView mPrecipValue;
    @InjectView(R.id.summaryLabel) TextView mSummaryLabel;
    @InjectView(R.id.iconImageView) ImageView mIconImageView;

    private Forecast mForecast;

    public double latitude;
    public double longitude;

    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.inject(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

// Create Google Api Client for Location Services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //toggleRefresh();
        getCurrentDetails();
    }

    private void getCurrentForecast(double latitude, double longitude) {
        /* forecast.io API information from old version of app
        *
        *String openWeatherApiKey = "2a1d75a3096c16efb7da6314ebf289ab";
        *String forecastUrl = "https://api.forecast.io/forecast/" + openWeatherApiKey + "/" + latitude + "," + longitude;
        */
        String currentUrl = "http://api.openweathermap.org/data/2.5/weather?" +
                "lat=" + latitude +
                "&lon=" + longitude +
                "&APPID=" + openWeatherApiKey +
                "&units=imperial";

        if (isNetworkAvailable()) {
            //toggleRefresh();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(currentUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mSwipeRefreshLayout.isRefreshing()){
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mSwipeRefreshLayout.isRefreshing()){
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    });

                    try {
                        /**
                         * This is where we need to parse the JSON data using {@link JSONParser.parseCurrentWeatherItem}
                         * and then persist it in the SQLite database
                         */
                        String jsonData = response.body().string();

                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {

                            Weather currentWeather = JSONParser.parseCurrentWeatherItem(jsonData);

                            int deleteStatus = WeatherPersister.DeleteAll(getApplicationContext());
                            if(deleteStatus == WeatherPersister.DELETE_SUCCESSFUL){
                                Log.d(TAG, "Delete Successful");
                            }
                            else{
                                Log.e(TAG, "Error deleting data from the table");
                            }
                            WeatherPersister.SaveCurrentWeather(getApplicationContext(), currentWeather);

                            //mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "IO Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Exception caught: ", e);
                    }
                }
            });
        }
        else {
            //toggleRefresh();
            if(mSwipeRefreshLayout.isRefreshing()){
                mSwipeRefreshLayout.setRefreshing(false);
            }
            Toast.makeText(this, getString(R.string.network_unavailable_message), Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {

//        if(mProgressBar.getVisibility() == View.INVISIBLE) {
//            mProgressBar.setVisibility(View.VISIBLE);
//            mRefreshImageView.setVisibility(View.INVISIBLE);
//        }
//        else {
//            mProgressBar.setVisibility(View.INVISIBLE);
//            mRefreshImageView.setVisibility(View.VISIBLE);
//        }
    }

   /* private Forecast parseForecastDetails(String jsonData) throws JSONException{
        Forecast forecast = new Forecast();

        forecast.setCurrent(getCurrentDetails());
        //forecast.setHourlyForecast(getHourlyForecast(jsonData));
        //forecast.setDailyForecast(getDailyForecast(jsonData));

        return forecast;
    }*/

    private Day[] getDailyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        //String timezone = forecast.getString("timezone");
        TimeZone timezone = TimeZone.getDefault();
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for (int i=0; i < data.length(); i++){
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();

            day.setSummary(jsonDay.getString("summary"));
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
            day.setTemperatureMin(jsonDay.getDouble("temperatureMin"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimezone(timezone);

            days[i] = day;
        }

        return days;
    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];
        for (int i=0; i < data.length(); i++){
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();

            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemperatureMax(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timezone);

            hours[i] = hour;
        }

        return hours;
    }

    /*private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        //String timezone = forecast.getString("timezone");
        String timezone = TimeZone.getDefault().toString();

        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTimeZone(timezone);

        return current;
     }*/

    /* This is the old getCurrentDetails that parsed JSON data
      private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        //String timeZone = TimeZone.getDefault().toString();

        JSONObject location = forecast.getJSONObject("coord");
        JSONObject sys = forecast.getJSONObject("sys");
        JSONObject weather = forecast.getJSONArray("weather").getJSONObject(0);
        JSONObject main = forecast.getJSONObject("main");
        JSONObject wind = forecast.getJSONObject("wind");
        if(forecast.has("rain")){
            JSONObject rain = forecast.getJSONObject("rain");
        }
        if(forecast.has("snow")) {
            JSONObject snow = forecast.getJSONObject("snow");
        }
        JSONObject clouds = forecast.getJSONObject("clouds");


        Current current = new Current();
        current.setCityName(forecast.getString("name"));
        current.setSunrise(sys.getLong("sunrise"));
        current.setSunset(sys.getLong("sunset"));
        current.setTemperature(main.getDouble("temp"));
        current.setTime(forecast.getLong("dt"));
        current.setWeatherDesc(weather.getString("description"));
        current.setWindSpeed(wind.getDouble("speed"));
        current.setHumidity(main.getDouble("humidity"));
        current.setIcon(weather.getString("icon"));
        TimeZone timezone = TimeZone.getDefault();
        current.setTimeZone(timezone);

        //persistCurrentDetails(current);
        
        return current;
    }*/

    private Weather getCurrentDetails(){
        CurrentWeatherDbHelper currentDbHelper = new CurrentWeatherDbHelper(this);
        SQLiteDatabase currentDb = currentDbHelper.getReadableDatabase();
        Cursor cursor = currentDb.query(Contract.CurrentWeather.TABLE_NAME, null, null, null, null, null, Contract.CurrentWeather.COLUMN_NAME_DT);

        cursor.moveToFirst();
            Weather current = new Weather(cursor);
            if(current.getDatetime() >= mTenMinutesAgo) {
                cursor.close();
                return current;
            }
            else{
                cursor.close();
                Log.d(TAG, "Data is not fresh " + current.getDatetime() + " was more that 10 minutes ago  - " + mTenMinutesAgo);
                updateWeather();
                return null;
            }
        //}
        //else{
            //cursor.close();
           // updateWeather();
            //return null;
        //}

    }

//    private void persistCurrentDetails(Current current) {
//
//        CurrentWeatherDbHelper currentDbHelper = new CurrentWeatherDbHelper(this);
//        SQLiteDatabase db = currentDbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Contract.CurrentWeather.COLUMN_NAME_CITY_NAME, current.getCityName());
//        values.put(Contract.CurrentWeather.COLUMN_NAME_SUNRISE, current.getSunrise());
//        values.put(Contract.CurrentWeather.COLUMN_NAME_SUNSET, current.getSunset());
//        values.put(Contract.CurrentWeather.COLUMN_NAME_TEMP, current.getTemperature());
//        values.put(Contract.CurrentWeather.COLUMN_NAME_DT, current.getTime());
//        values.put(Contract.CurrentWeather.COLUMN_NAME_HUMIDITY, current.getHumidity());
//        values.put(Contract.CurrentWeather.COLUMN_NAME_WEATHER_DESC, current.getWeatherDesc());
//        values.put(Contract.CurrentWeather.COLUMN_NAME_WEATHER_ICON, current.getIcon());
//        db.insert(Contract.CurrentWeather.TABLE_NAME, null, values);
//    }

    private void updateDisplay() {
        /*CurrentWeatherDbHelper currentDbHelper = new CurrentWeatherDbHelper(this);
        SQLiteDatabase currentDb = currentDbHelper.getReadableDatabase();
        Cursor cursor = currentDb.query(Contract.CurrentWeather.TABLE_NAME, null, null, null, null, null, Contract.CurrentWeather.COLUMN_NAME_DT);

        if(cursor.moveToFirst()) {
            //Toast.makeText(this, "Sunrise is " + cursor.getString(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_SUNRISE)), Toast.LENGTH_LONG).show();
            long roundedTemp = Math.round(Double.valueOf(cursor.getString(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_TEMP))));
            Log.d(TAG, "Temp: " + cursor.getString(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_TEMP)));
            mTemperatureLabel.setText(String.valueOf(roundedTemp));

            Log.d(TAG, "Sunrise: " + cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_SUNRISE));
            mSunrise.setText(cursor.getString(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_SUNRISE)));

            Log.d(TAG, "Sunset: " + cursor.getString(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_SUNSET)));
            mSunset.setText(cursor.getString(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_SUNSET)));

            Log.d(TAG, "City: " + cursor.getString(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_CITY_NAME)));
            mLocationLabel.setText(cursor.getString(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_CITY_NAME)));

        } else {
            Toast.makeText(this, "Error loading weather data", Toast.LENGTH_LONG).show();
        }*/

        Weather current = getCurrentDetails();

        //Drawable drawable = getResources().getDrawable(current.getIcon());
        //mIconImageView.setImageDrawable(drawable);

        mLocationLabel.setText(getLocation(latitude, longitude));

        if(current != null) {
            mTemperatureLabel.setText(current.temperature.getTemp() + "");
            mSunrise.setText(current.location.getPrettySunrise() + "");
            mSunset.setText(current.location.getPrettySunset() + "");
            mTimeLabel.setText("Last Updated " + current.getPrettyTime());
            mHumidityValue.setText(current.summary.getHumidity() + "%");

            mPrecipValue.setText(current.rain.getRainAmt() + "\"");
            mSummaryLabel.setText(current.summary.getDescr() + "");
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view){
        Intent intent = new Intent(this,DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
        startActivity(intent);
    }

    @OnClick(R.id.hourlyButton)
    public void startHourlyActivity(View View){
        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra(HOURLY_FORECAST, mForecast.getHourlyForecast());
        startActivity(intent);
    }

    private void updateWeather() {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(lastLocation != null) {
            latitude = lastLocation.getLatitude();
            longitude = lastLocation.getLongitude();
            Log.d(TAG, "Lattitude: " + String.valueOf(latitude) + "    Longitude: " + String.valueOf(longitude));
            Log.d(TAG, getLocation(latitude, longitude));
            getCurrentForecast(latitude, longitude);
        }
        else {
            Log.d(TAG, "Last Location was null!");
        }
    }

    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getCurrentDetails();
        }
    };

// Google API Methods for handling Connections
    @Override
    public void onConnected(Bundle bundle) {
        updateWeather();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection Failed \n" + connectionResult.toString());
        mSummaryLabel.setText("Location request failed");
    }

    /**
     *
     * @param latitude
     * @param longitude
     * @return
     */
    private String getLocation(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getLocality()).append(", ");
                result.append(address.getAdminArea());
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }

}
