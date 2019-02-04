package com.example.bhavyashah.thingeereminder;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class BluetoothConnectionService extends Service {

    private BluetoothConnectionReceiver bluetoothConnectionReceiver = null;

    @TargetApi(Build.VERSION_CODES.P)
    @Override
    public void onCreate() {
        super.onCreate();
        String NOTIFICATION_CHANNEL_ID = BluetoothConnectionReceiver.CHANNEL_ID;
        String channelName = BluetoothConnectionReceiver.CHANNEL_NAME;
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.THINGEE_REMINDER_SHARED_PREFS, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(MainActivity.REMINDER_STATUS, true)) {
            // If the reminder status was set to true, that means it wasn't the user that called
            // onDestroy, but rather the android system. In this case, restart the service
            Intent restartService = new Intent(this, RestartReceiver.class);
            sendBroadcast(restartService);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);

        bluetoothConnectionReceiver = new BluetoothConnectionReceiver();
        registerReceiver(bluetoothConnectionReceiver, intentFilter);
        return START_STICKY;
    }
}
