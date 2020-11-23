package com.tarafdari.flutter_media_notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.media.session.MediaButtonReceiver;


public class NotificationPanel extends Service {
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "flutter_media_notification/channel";
    private static final String CHANNEL_NAME = "Foreground Service Notification Channel";
    private static final String MEDIA_SESSION_TAG = "flutter_media_notification/mediasession";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isPlaying = intent.getBooleanExtra("isPlaying", true);
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createNotificationChannel();


        MediaSessionCompat mediaSession = new MediaSessionCompat(this, MEDIA_SESSION_TAG);


        int iconPlayPause = R.drawable.baseline_play_arrow_black_48;
        String titlePlayPause = "pause";
        if(isPlaying){
            iconPlayPause = R.drawable.baseline_pause_black_48;
            titlePlayPause = "play";
        }

        Intent toggleIntent = new Intent(this, NotificationReturnSlot.class)
                .setAction("toggle")
                .putExtra("title",  title)
                .putExtra("author",  author)
                .putExtra("play", !isPlaying);
        PendingIntent pendingToggleIntent = PendingIntent.getBroadcast(this, 0, toggleIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        MediaButtonReceiver.handleIntent(mediaSession, toggleIntent);

        //TODO(ALI): add media mediaSession Buttons and handle them
        Intent nextIntent = new Intent(this, NotificationReturnSlot.class)
                .setAction("next");
        PendingIntent pendingNextIntent = PendingIntent.getBroadcast(this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        MediaButtonReceiver.handleIntent(mediaSession, nextIntent);

        Intent prevIntent = new Intent(this, NotificationReturnSlot.class)
                .setAction("prev");
        PendingIntent pendingPrevIntent = PendingIntent.getBroadcast(this, 0, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        MediaButtonReceiver.handleIntent(mediaSession, prevIntent);

        Intent selectIntent = new Intent(this, NotificationReturnSlot.class)
                .setAction("select");
        PendingIntent selectPendingIntent = PendingIntent.getBroadcast(this, 0, selectIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//        MediaButtonReceiver.handleIntent(mediaSession, selectIntent);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setOngoing(true)
                .addAction(R.drawable.baseline_skip_previous_black_48, "prev", pendingPrevIntent)
                .addAction(iconPlayPause, titlePlayPause, pendingToggleIntent)
                .addAction(R.drawable.baseline_skip_next_black_48, "next", pendingNextIntent)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1,2)
                        .setShowCancelButton(true)
                        .setMediaSession(mediaSession.getSessionToken()))
                .setSmallIcon(R.drawable.ic_stat_music_note)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setVibrate(new long[]{0L})
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle(title)
                .setContentText(author)
                .setSubText(title)
                .setContentIntent(selectPendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_stat_music_note));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setCategory(Notification.CATEGORY_SERVICE);
        }

        Notification notification = notificationBuilder.build();

        startForeground(NOTIFICATION_ID, notification);
        if(!isPlaying) {
            stopForeground(false);
        }

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel serviceChannel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                importance
        );
        serviceChannel.setImportance(importance);
        serviceChannel.setDescription("flutter_media_notification");
        serviceChannel.setShowBadge(false);
        serviceChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        NotificationManager manager = getSystemService(NotificationManager.class);
        assert manager != null;
        manager.createNotificationChannel(serviceChannel);
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopForeground(true);
    }
}

