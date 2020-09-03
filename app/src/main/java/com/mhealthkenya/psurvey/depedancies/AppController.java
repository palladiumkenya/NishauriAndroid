package com.mhealthkenya.psurvey.depedancies;

import android.app.Application;
import android.content.Context;


import androidx.appcompat.app.AppCompatDelegate;


import com.fxn.stash.Stash;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private static AppController instance;
    private static Context appContext;


    public static Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context mAppContext) {
        this.appContext = mAppContext;
    }




    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Stash.init(this);


        this.setAppContext(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }



    public static synchronized AppController getInstance() {
        return instance;
    }




}
