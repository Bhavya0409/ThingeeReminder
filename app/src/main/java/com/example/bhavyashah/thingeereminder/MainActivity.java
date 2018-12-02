package com.example.bhavyashah.thingeereminder;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private BluetoothConnectionReceiver bluetoothConnectionReceiver;

    @BindView(R.id.reminder_button) Button reminderButton;
    @BindView(R.id.reminder_toggle_text) TextView enabledTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        bluetoothConnectionReceiver = new BluetoothConnectionReceiver(getApplicationContext());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);

        registerReceiver(bluetoothConnectionReceiver, intentFilter);
    }

    @OnClick(R.id.reminder_button)
    public void onClick(View v) {
        //TODO Toggle broadcast receiver on/off

//        Intent intent = new Intent(this, SearchService.class);
//        if (isMyServiceRunning(SearchService.class)) {
//            stopService(intent);
//            enabledTextView.setText("Disabled");
//        } else {
//            startService(intent);
//            enabledTextView.setText("Enabled");
//        }
    }

//    private boolean isMyServiceRunning(Class<?> serviceClass) {
//        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
//            if (serviceClass.getName().equals(service.service.getClassName())) {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothConnectionReceiver);
    }
}
