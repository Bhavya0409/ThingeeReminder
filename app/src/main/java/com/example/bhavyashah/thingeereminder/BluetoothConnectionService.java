package com.example.bhavyashah.thingeereminder;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BluetoothConnectionService extends Service {

    private BluetoothConnectionReceiver bluetoothConnectionReceiver = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);

        bluetoothConnectionReceiver = new BluetoothConnectionReceiver();
        registerReceiver(bluetoothConnectionReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (bluetoothConnectionReceiver != null) {
            unregisterReceiver(bluetoothConnectionReceiver);
        }
    }
}
