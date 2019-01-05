package com.dingbuoyi.sprintcalculator;

import android.app.Application;
import android.content.ContextWrapper;
import com.pixplicity.easyprefs.library.Prefs;
import net.danlew.android.joda.JodaTimeAndroid;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }
}
