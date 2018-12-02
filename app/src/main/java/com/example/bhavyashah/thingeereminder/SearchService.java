package com.example.bhavyashah.thingeereminder;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class SearchService extends Service {

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        System.out.println("bhavya on task removed");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("bhavya on start command");
        super.onStartCommand(intent, flags, startId);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("bhavya running...");
            }
        }, 0, 5000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        System.out.println("bhavya destroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println("bhavya on service create");
        super.onCreate();
        //search bluetooth devices
    }
}
