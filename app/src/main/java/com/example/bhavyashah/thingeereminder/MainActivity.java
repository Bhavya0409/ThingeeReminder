package com.example.bhavyashah.thingeereminder;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.reminder_button) Button reminderButton;
    @BindView(R.id.reminder_toggle_text) TextView enabledTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        enabledTextView.setText(isMyServiceRunning(BluetoothConnectionService.class) ?
                getString(R.string.enabled) : getString(R.string.disabled));
    }

    @OnClick(R.id.reminder_button)
    public void onClick(View v) {
        Intent bluetoothConnectionService = new Intent(MainActivity.this, BluetoothConnectionService.class);
        if (isMyServiceRunning(BluetoothConnectionService.class)) {
            stopService(bluetoothConnectionService);
            enabledTextView.setText(getString(R.string.disabled));
        } else {
            startService(bluetoothConnectionService);
            enabledTextView.setText(getString(R.string.enabled));
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
