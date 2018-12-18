package com.example.bhavyashah.thingeereminder;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.reminder_button) Button reminderButton;
    @BindView(R.id.reminder_toggle_text) TextView enabledTextView;

    Intent bluetoothConnectionService;
    SharedPreferences sharedpreferences;

    public static final String THINGEE_REMINDER_SHARED_PREFS = "THINGEE_REMINDER_SHARED_PREFS" ;
    public static final String REMINDER_STATUS = "REMINDER_STATUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        enabledTextView.setText(isMyServiceRunning(BluetoothConnectionService.class) ?
                getString(R.string.enabled) : getString(R.string.disabled));

        bluetoothConnectionService = new Intent(MainActivity.this, BluetoothConnectionService.class);

        sharedpreferences = getSharedPreferences(THINGEE_REMINDER_SHARED_PREFS, Context.MODE_PRIVATE);

        if (!sharedpreferences.contains(REMINDER_STATUS)) {
            sharedpreferences.edit().putBoolean(REMINDER_STATUS, false).apply();
        }
    }

    @OnClick(R.id.reminder_button)
    public void onClick(View v) {
        if (isMyServiceRunning(BluetoothConnectionService.class)) {
            // Was enabled, so now disable it
            // Need commit since this needs to all be done synchronously
            sharedpreferences.edit().putBoolean(REMINDER_STATUS, false).commit();
            stopService(bluetoothConnectionService);
            enabledTextView.setText(getString(R.string.disabled));
        } else {
            // Was disabled, so now enable it
            // Need commit since this needs to all be done synchronously
            sharedpreferences.edit().putBoolean(REMINDER_STATUS, true).commit();
            startService(bluetoothConnectionService);
            enabledTextView.setText(getString(R.string.enabled));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(bluetoothConnectionService);
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
