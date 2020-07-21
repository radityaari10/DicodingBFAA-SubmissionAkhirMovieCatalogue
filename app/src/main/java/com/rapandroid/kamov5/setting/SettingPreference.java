package com.rapandroid.kamov5.setting;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingPreference {
    private static final String SETTING_PREF = "setting_pref";
    private static final String DAILY_REMINDER = "daily_reminder";
    private static final String RELEASE_REMINDER = "release_reminder";
    private final SharedPreferences mSharedPreferences;

    public SettingPreference(Context context) {
        mSharedPreferences = context.getSharedPreferences(SETTING_PREF, Context.MODE_PRIVATE);
    }

    public void setDailyReminder(boolean isActive){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(DAILY_REMINDER, isActive);
        editor.apply();
    }

    public boolean getDailyReminder(){
        return mSharedPreferences.getBoolean(DAILY_REMINDER, false);
    }

    public void setReleaseReminder(boolean isActive){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(RELEASE_REMINDER, isActive);
        editor.apply();
    }

    public boolean getReleaseReminder(){
        return mSharedPreferences.getBoolean(RELEASE_REMINDER, false);
    }
}
