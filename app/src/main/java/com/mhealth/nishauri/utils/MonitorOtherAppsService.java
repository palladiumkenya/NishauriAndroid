package com.mhealth.nishauri.utils;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.fxn.stash.Stash;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;

import java.util.List;

public class MonitorOtherAppsService extends Service {

    private String lastForegroundApp = "";
    private static final long LOGOUT_DELAY = 10000; // 30 seconds delay before logout

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, new Notification()); // Keep the service alive

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String currentApp = getForegroundApp();
                if (!currentApp.equals(lastForegroundApp)) {
                    // User switched to another app
                    logout();

                }
                lastForegroundApp = currentApp;
                handler.postDelayed(this, LOGOUT_DELAY);
            }
        };
        handler.postDelayed(runnable, LOGOUT_DELAY);
        return START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }
    private String getForegroundApp() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = am.getRunningAppProcesses();
        if (appProcesses != null && !appProcesses.isEmpty()) {
            return appProcesses.get(0).processName;
        }
        return "";
    }

    public void logout(){

        String endPoint = Stash.getString(Constants.AUTH_TOKEN);
        Stash.clearAll();

        Stash.put(Constants.AUTH_TOKEN, endPoint);
        // Start the background service
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);

        //context.fini;
    }
}
