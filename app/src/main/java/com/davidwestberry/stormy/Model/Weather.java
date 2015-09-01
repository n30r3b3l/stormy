package com.davidwestberry.stormy.Model;

import android.database.Cursor;

import com.davidwestberry.stormy.Data.Contract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by davidw on 7/17/2015.
 */
public class Weather {
    public Location location;
    public long datetime;
    public Summary summary = new Summary();
    public Temperature temperature = new Temperature();
    public Clouds clouds = new Clouds();
    public Wind wind = new Wind();
    public Rain rain = new Rain();
    public Snow snow = new Snow();

    public long getDatetime() {
        return datetime;
    }

    public String getPrettyTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getDefault());
        Date datetime = new Date(getDatetime());
        String timeString = formatter.format(datetime);

        return timeString;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public class Summary {
        private int id;
        private String condition;
        private String descr;
        private String icon;

        private float pressure;
        private float humidity;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public float getPressure() {
            return pressure;
        }

        public void setPressure(float pressure) {
            this.pressure = pressure;
        }

        public float getHumidity() {
            return humidity;
        }

        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }
    }

    public class Temperature{
        private float temp;
        private float minTemp;
        private float maxTemp;

        public int getTemp() {
            return (int) Math.round(temp);
        }

        public void setTemp(float temp) {
            this.temp = temp;
        }

        public int getMinTemp() {
            return (int) Math.round(minTemp);
        }

        public void setMinTemp(float minTemp) {
            this.minTemp = minTemp;
        }

        public int getMaxTemp() {
            return (int) Math.round(maxTemp);
        }

        public void setMaxTemp(float maxTemp) {
            this.maxTemp = maxTemp;
        }
    }

    public class Clouds{
        private int all;

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }
    }

    public class Wind{
        private float speed;
        private float deg;

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public float getDeg() {
            return deg;
        }

        public void setDeg(float deg) {
            this.deg = deg;
        }
    }

    public class Rain{
        private float rainAmt;

        public float getRainAmt() {
            return rainAmt;
        }

        public void setRainAmt(float rainAmt) {
            this.rainAmt = rainAmt;
        }
    }

    public class Snow{
        public double snowAmt;

        public double getSnowAmt() {
            return snowAmt;
        }

        public void setSnowAmt(double snowAmt) {
            this.snowAmt = snowAmt;
        }
    }

    public Weather(){}

    public Weather(Cursor cursor) {
        setDatetime(cursor.getLong(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_DT)));

        summary.setId(cursor.getInt(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_WEATHER_ID)));
        summary.setCondition(cursor.getString(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_WEATHER_DESC)));
        summary.setDescr(cursor.getString(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_WEATHER_DESC)));
        summary.setHumidity(cursor.getFloat(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_HUMIDITY)));

        temperature.setTemp(cursor.getFloat(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_TEMP)));

        clouds.setAll(cursor.getInt(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_CLOUD_COVER)));

        wind.setDeg(cursor.getFloat(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_WIND_DIRECTION)));
        wind.setSpeed(cursor.getFloat(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_WIND_SPEED)));

        rain.setRainAmt(cursor.getFloat(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_RAIN)));

        location = new Location();
        location.setCity(cursor.getString(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_CITY_NAME)));
        location.setSunrise(cursor.getLong(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_SUNRISE)));
        location.setSunset(cursor.getLong(cursor.getColumnIndex(Contract.CurrentWeather.COLUMN_NAME_SUNSET)));
    }
}
