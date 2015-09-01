package com.davidwestberry.stormy;

import android.database.Cursor;

import com.davidwestberry.stormy.Data.Contract;
import com.davidwestberry.stormy.Model.Forecast;
import com.davidwestberry.stormy.Model.Location;
import com.davidwestberry.stormy.Model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by davidw on 7/29/2015.
 *
 * This class is used to Parse the JSON information from the OpenWeatherMap API.
 *
 * When we receive a JSON object from the Current Conditions API call,
 * we pass it to parseCurrentWeatherItem to parse.
 *
 * However, when we receive the JSON object from the Forecast API call, we receive
 * a forecast JSON object which has a JSON Array of objects similar to the Current Conditions
 * JSON Object. So we pass it to the parseForecast method which parses the data from the Forecast
 * JSON Object and iterates through the list of weather conditions and creates an array of
 * {@link Weather} objects. The parseForecast method returns a complete Forecast object with the
 * Array of {@link Weather} objects parsed as well.
 *
 */
public class JSONParser {

    /**
     *
     * @param data a JSON object containing Forecast Information from the
     *             OpenWeatherMap API
     * @return a {@link Forecast} object complete with the Array of already parsed
     *             {@link Weather} objects
     * @throws JSONException
     *
     * This method calls the parseForecastWeatherItem method for us to create an Array of
     * {@link Weather} objects
     */
    public static Forecast parseForecast(String data) throws JSONException{
        Forecast forecast = new Forecast();

        JSONObject jObj = new JSONObject(data);

        JSONObject cObj = ParseHelper.getObject("city", jObj);
        forecast.city.setName(ParseHelper.getString("name", cObj));
        forecast.city.setCountry(ParseHelper.getString("country", cObj));

        JSONArray list = jObj.getJSONArray("list");
        int listLength = list.length();

        for(int i = 0; i < listLength; i++){
            JSONObject lObj = list.getJSONObject(i);
            Weather forecastWeatherItem = parseForecastWeatherItem(lObj.toString());
            forecast.weatherArray.add(forecastWeatherItem);
        }

        return forecast;
    }

    /**
     *
     * @param data a string of JSON data containing Current Weather Information from the
     *             OpenWeatherMap API
     * @return a {@link Weather} object containing the parsed weather information
     * @throws JSONException
     */
    public static Weather parseCurrentWeatherItem(String data) throws JSONException {
        Weather weather = new Weather();

        JSONObject jObj = new JSONObject(data);

        Location loc = new Location();

        weather.setDatetime(System.currentTimeMillis());

        JSONObject sysObj = ParseHelper.getObject("sys", jObj);
        loc.setCountry(ParseHelper.getString("country", sysObj));
        loc.setSunrise(ParseHelper.getInt("sunrise", sysObj));
        loc.setSunset(ParseHelper.getInt("sunset", sysObj));
        loc.setCity(ParseHelper.getString("name", jObj));
        weather.location = loc;

        JSONArray jArr = jObj.getJSONArray("weather");

        JSONObject JSONWeather = jArr.getJSONObject(0);

        weather.summary.setId(ParseHelper.getInt("id", JSONWeather));
        weather.summary.setDescr(ParseHelper.getString("description", JSONWeather));
        weather.summary.setCondition(ParseHelper.getString("main", JSONWeather));
        weather.summary.setIcon(ParseHelper.getString("icon", JSONWeather));

        JSONObject mainObj = ParseHelper.getObject("main", jObj);
        weather.summary.setHumidity(ParseHelper.getFloat("humidity", mainObj));
        weather.summary.setPressure(ParseHelper.getFloat("pressure", mainObj));
        weather.temperature.setMaxTemp(ParseHelper.getFloat("temp_max", mainObj));
        weather.temperature.setMinTemp(ParseHelper.getFloat("temp_min", mainObj));
        weather.temperature.setTemp(ParseHelper.getFloat("temp", mainObj));

        // Wind
        JSONObject wObj = ParseHelper.getObject("wind", jObj);
        weather.wind.setSpeed(ParseHelper.getFloat("speed", wObj));
        weather.wind.setDeg(ParseHelper.getFloat("deg", wObj));

        // Clouds
        JSONObject cObj = ParseHelper.getObject("clouds", jObj);
        weather.clouds.setAll(ParseHelper.getInt("all", cObj));

        // Rain
        if(jObj.has("rain")){
            JSONObject rObj = ParseHelper.getObject("rain", jObj);

            if(rObj.has("3h")) {
                weather.rain.setRainAmt(ParseHelper.getFloat("3h", rObj));
            }
        }

        // Snow
        if(jObj.has("snow")){
            JSONObject sObj = ParseHelper.getObject("snow", jObj);

            if(sObj.has("3h")) {
                weather.snow.setSnowAmt(ParseHelper.getFloat("3h", sObj));
            }
        }

        return weather;
    }

    /**
     *
     * @param data a string of JSON data containing Weather Condition information from
     *             one item in the Forecast list object returned from the
     *             OpenWeatherMap API.
     * @return a {@link Weather} object containing parsed weather information from
     * @throws JSONException
     */
    public static Weather parseForecastWeatherItem(String data) throws JSONException{
        Weather weather = new Weather();

        JSONObject jObj = new JSONObject(data);

        JSONArray jArr = jObj.getJSONArray("weather");

        JSONObject JSONWeather = jArr.getJSONObject(0);
        weather.summary.setId(ParseHelper.getInt("id", JSONWeather));
        weather.summary.setDescr(ParseHelper.getString("description", JSONWeather));
        weather.summary.setCondition(ParseHelper.getString("main", JSONWeather));
        weather.summary.setIcon(ParseHelper.getString("icon", JSONWeather));

        JSONObject mainObj = ParseHelper.getObject("main", jObj);
        weather.summary.setHumidity(ParseHelper.getFloat("humidity", mainObj));
        weather.summary.setPressure(ParseHelper.getFloat("pressure", mainObj));
        weather.temperature.setMaxTemp(ParseHelper.getFloat("temp_max", mainObj));
        weather.temperature.setMinTemp(ParseHelper.getFloat("temp_min", mainObj));
        weather.temperature.setTemp(ParseHelper.getFloat("temp", mainObj));

        // Wind
        JSONObject wObj = ParseHelper.getObject("wind", jObj);
        weather.wind.setSpeed(ParseHelper.getFloat("speed", wObj));
        weather.wind.setDeg(ParseHelper.getFloat("deg", wObj));

        // Clouds
        JSONObject cObj = ParseHelper.getObject("clouds", jObj);
        weather.clouds.setAll(ParseHelper.getInt("all", cObj));

        // Rain
        if(jObj.has("rain")){
            JSONObject rObj = ParseHelper.getObject("rain", jObj);

            if(rObj.has("3h")) {
                weather.rain.setRainAmt(ParseHelper.getFloat("3h", rObj));
            }
        }

        // Snow
        if(jObj.has("snow")){
            JSONObject sObj = ParseHelper.getObject("sno", jObj);

            if(sObj.has("3h")) {
                weather.snow.setSnowAmt(ParseHelper.getFloat("3h", sObj));
            }
        }

        return weather;
    }
}
