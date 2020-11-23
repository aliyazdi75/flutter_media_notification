package com.tarafdari.flutter_media_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

public class NotificationReturnSlot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) return;
        switch (action) {
            case "prev":
                FlutterMediaNotificationPlugin.callEvent("prev");
                break;
            case "next":
                FlutterMediaNotificationPlugin.callEvent("next");
                break;
            case "toggle":
                String title = intent.getStringExtra("title");
                String author = intent.getStringExtra("author");
                Boolean play = intent.getBooleanExtra("play",true);

                if(play)
                    FlutterMediaNotificationPlugin.callEvent("play");
                else
                    FlutterMediaNotificationPlugin.callEvent("pause");

                FlutterMediaNotificationPlugin.showNotification(title, author,play);
                break;
            case "select":
                Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                context.sendBroadcast(closeDialog);
                String packageName = context.getPackageName();
                PackageManager pm = context.getPackageManager();
                Intent launchIntent = pm.getLaunchIntentForPackage(packageName);
                context.startActivity(launchIntent);

                FlutterMediaNotificationPlugin.callEvent("select");
        }
    }
}

