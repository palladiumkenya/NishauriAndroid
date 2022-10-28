package com.example.mhealth.appointment_diary.config;

import com.orm.SugarApp;
import com.orm.SugarContext;

public class App extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);

    }

    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
}
