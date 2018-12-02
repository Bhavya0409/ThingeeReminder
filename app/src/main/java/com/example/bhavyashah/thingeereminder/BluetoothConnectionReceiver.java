package com.example.bhavyashah.thingeereminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class BluetoothConnectionReceiver extends BroadcastReceiver {

    private static final String CHANNEL_NAME = "Thingee Reminder";
    private static final String CONTENT_TITLE = "REMINDER!";
    private static final String CONTENT_TEXT = "Do you have your thingee??";
    private static final String CHANNEL_ID = "thingee_reminder";

    private static final String BLUETOOTH_DEVICE_TO_COMPARE = "Beats Solo³";
//    private static final String BLUETOOTH_DEVICE_TO_COMPARE = "HandsFreeLink";

    private Context context;

    public BluetoothConnectionReceiver() {
        context = MyApplication.getAppContext();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BluetoothDevice.ACTION_ACL_CONNECTED) ||
                intent.getAction().equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (device.getName().equals(BLUETOOTH_DEVICE_TO_COMPARE)) {
                sendNotification();
            }
        }
    }

    private void sendNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_NAME);
        Intent ii = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle(CONTENT_TITLE);
        mBuilder.setContentText(CONTENT_TEXT);
        mBuilder.setPriority(Notification.PRIORITY_MAX);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = CHANNEL_ID;
            NotificationChannel channel = new NotificationChannel(channelId, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }
}
