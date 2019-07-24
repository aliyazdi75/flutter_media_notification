package com.tarafdari.flutter_media_notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Style;
import android.widget.RemoteViews;

class NotificationPanel {
    private Context parent;
    private NotificationManager nManager;
    private String title;
    private String author;
    private boolean isPlaying;

    NotificationPanel(Context parent, String title, String author, boolean isPlaying) {
        this.parent = parent;
        this.title = title;
        this.author = author;
        this.isPlaying = isPlaying;

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(parent, "flutter_media_notification")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_stat_music_note)
//                .setLargeIcon()
//                .setProgress()
//                .setStyle(new NotificationCompat.MediaStyle())
                .setPriority(Notification.PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOngoing(this.isPlaying)
                .setOnlyAlertOnce(true)
                .setVibrate(new long[]{0L})
                .setSound(null);

        RemoteViews remoteView = new RemoteViews(parent.getPackageName(), R.layout.notificationlayout);

        remoteView.setTextViewText(R.id.title, title);
        remoteView.setTextViewText(R.id.author, author);

        if (this.isPlaying) {
            remoteView.setImageViewResource(R.id.toggle, R.drawable.baseline_pause_black_48);
        } else {
            remoteView.setImageViewResource(R.id.toggle, R.drawable.baseline_play_arrow_black_48);
        }

        setListeners(remoteView);
        nBuilder.setContent(remoteView);

        Notification notification = nBuilder.build();

        nManager = (NotificationManager) parent.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(1, notification);
    }

    private void setListeners(RemoteViews view) {

        Intent intent = new Intent(parent, NotificationReturnSlot.class)
                .setAction("toggle")
                .putExtra("title", this.title)
                .putExtra("author", this.author)
                .putExtra("action", !this.isPlaying ? "play" : "pause");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(parent, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.toggle, pendingIntent);


        Intent nextIntent = new Intent(parent, NotificationReturnSlot.class)
                .setAction("next");
        PendingIntent pendingNextIntent = PendingIntent.getBroadcast(parent, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.next, pendingNextIntent);


        Intent prevIntent = new Intent(parent, NotificationReturnSlot.class)
                .setAction("prev");
        PendingIntent pendingPrevIntent = PendingIntent.getBroadcast(parent, 0, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.prev, pendingPrevIntent);


        Intent selectIntent = new Intent(parent, NotificationReturnSlot.class)
                .setAction("select");
        PendingIntent selectPendingIntent = PendingIntent.getBroadcast(parent, 0, selectIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        view.setOnClickPendingIntent(R.id.layout, selectPendingIntent);
    }


    void notificationCancel() {
        nManager.cancel(1);
    }
}

