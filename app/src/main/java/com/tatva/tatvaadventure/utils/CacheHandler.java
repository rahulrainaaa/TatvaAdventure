package com.tatva.tatvaadventure.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Cache Handling Singleton Class.
 */
public class CacheHandler {
    private static CacheHandler ourInstance = new CacheHandler();

    private String CACHE_NAME = "tatvacache";

    public static CacheHandler getInstance() {
        return ourInstance;
    }

    public String getCache(Context activity, String key, String defaultValue) {
        SharedPreferences s = activity.getSharedPreferences(CACHE_NAME, Activity.MODE_PRIVATE);
        return s.getString(key.trim(), defaultValue.trim()).toString();
    }

    public Integer getCache(Context activity, String key, Integer defaultValue) {
        SharedPreferences s = activity.getSharedPreferences(CACHE_NAME, Activity.MODE_PRIVATE);
        return s.getInt(key.trim(), defaultValue);
    }

    public void setCache(Context activity, String key, String value) {
        SharedPreferences.Editor se = activity.getSharedPreferences(CACHE_NAME, Activity.MODE_PRIVATE).edit();
        se.putString(key.trim(), value.trim());
        se.commit();
        se = null;
        return;
    }

    public void setCache(Context activity, String key, Integer value) {
        SharedPreferences.Editor se = activity.getSharedPreferences(CACHE_NAME, Activity.MODE_PRIVATE).edit();
        se.putInt(key.trim(), value);
        se.commit();
        se = null;
        return;
    }
}
