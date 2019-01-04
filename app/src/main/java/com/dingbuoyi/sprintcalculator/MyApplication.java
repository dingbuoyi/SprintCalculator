package com.dingbuoyi.sprintcalculator;

import android.app.Application;
import net.danlew.android.joda.JodaTimeAndroid;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
