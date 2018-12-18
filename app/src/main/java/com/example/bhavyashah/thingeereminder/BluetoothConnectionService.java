package com.example.bhavyashah.thingeereminder;

import android.app.Notification;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BluetoothConnectionService extends Service {

    private BluetoothConnectionReceiver bluetoothConnectionReceiver = null;

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1, new Notification());
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
