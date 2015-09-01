package com.davidwestberry.stormy;

import org.json.JSONException;
import org.json.JSONObject;

public class ParseHelper {
    public static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    public static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    public static long getLong(String tagName, JSONObject jObj) throws JSONException{
        return jObj.getLong(tagName);
    }

    public static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    public static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
}