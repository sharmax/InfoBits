package com.example.suryansh.infobits;

import android.app.Application;
import android.content.Context;

/**
 * Created by SowmyaY on 27/03/16.
 */
public class MyApplication extends Application{
    private static MyApplication sInstance;

    @Override
    public void onCreate(){

        super.onCreate();
        sInstance = this;
    }
    public static MyApplication getInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
