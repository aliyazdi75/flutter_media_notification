package com.tarafdari.flutter_media_notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

class NotificationPanel extends Activity {
    private Context parent;
    private String title;
    private String author;
    private boolean isPlaying;
    private NotificationManager nManager;

    private static final String CHANNEL_ID = "flutter_media_notification";
    private final static String MEDIA_SESSION_TAG = "flutter_media_notification";

    NotificationPanel(Context parent, String title, String author, boolean isPlaying) {
        this.parent = parent;
        this.title = title;
        this.author = author;
        this.isPlaying = isPlaying;
        MediaSessionCompat mediaSession;
        Notification notification;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(parent);
        }
        mediaSession = new MediaSessionCompat(parent, MEDIA_SESSION_TAG);

        notification = new NotificationCompat.Builder(parent, CHANNEL_ID)
                .addAction(R.drawable.baseline_pause_black_48, "play", null)
                .addAction(R.drawable.baseline_pause_black_48, "play", null)
                .addAction(R.drawable.baseline_pause_black_48, "play", null)
                .addAction(R.drawable.baseline_pause_black_48, "play", null)
                .addAction(R.drawable.baseline_pause_black_48, "play", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3)
                        .setShowCancelButton(true)
//                        .makeContentView()
                        .setMediaSession(mediaSession.getSessionToken()))
                .setSmallIcon(R.drawable.ic_stat_music_note)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
//                    .setContentIntent(createContentIntent())
                .setContentTitle(title)
                .setContentText(author)
                .setOngoing(this.isPlaying)
                .setSubText(title)
                .setLargeIcon(BitmapFactory.decodeResource(parent.getResources(), R.drawable.ic_stat_music_note))
//                    .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(
//                            mService, PlaybackStateCompat.ACTION_STOP))
                .build();
        nManager = (NotificationManager) parent.getSystemService(Context.NOTIFICATION_SERVICE);
        assert nManager != null;
        nManager.notify(1, notification);

//        RemoteViews remoteView = new RemoteViews(parent.getPackageName(), R.layout.notificationlayout);
//
//        remoteView.setTextViewText(R.id.title, title);
//        remoteView.setTextViewText(R.id.author, author);
//
//        if (this.isPlaying) {
//            remoteView.setImageViewResource(R.id.toggle, R.drawable.baseline_pause_black_48);
//        } else {
//            remoteView.setImageViewResource(R.id.toggle, R.drawable.baseline_play_arrow_black_48);
//        }
//
//        setListeners(remoteView);
//        nBuilder.setContent(remoteView);
//

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel(Context parent) {
        NotificationManager
                mNotificationManager =
                (NotificationManager) parent
                        .getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence name = "Media playback";
        String description = "Media playback controls";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        // Configure the notification channel.
        mChannel.setDescription(description);
        mChannel.setShowBadge(false);
        mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        assert mNotificationManager != null;
        mNotificationManager.createNotificationChannel(mChannel);
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

    @Override
    protected void onDestroy() {
        nManager.cancel(1);
        super.onDestroy();
    }

    void notificationCancel() {
        nManager.cancel(1);
    }
}

