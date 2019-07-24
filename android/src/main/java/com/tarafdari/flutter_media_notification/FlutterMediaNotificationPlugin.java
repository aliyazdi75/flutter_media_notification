package com.tarafdari.flutter_media_notification;


import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

/** FlutterMediaNotificationPlugin */
public class FlutterMediaNotificationPlugin implements MethodCallHandler {
  private static final String CHANNEL = "flutter_media_notification";
  private static Registrar registrar;
  private static NotificationPanel nPanel;
  private static MethodChannel channel;

  private FlutterMediaNotificationPlugin(Registrar r) {
    registrar = r;
  }

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    FlutterMediaNotificationPlugin.channel = new MethodChannel(registrar.messenger(), "flutter_media_notification");
    FlutterMediaNotificationPlugin.channel.setMethodCallHandler(new FlutterMediaNotificationPlugin(registrar));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    switch (call.method) {
      case "showNotification":
        final String title = call.argument("title");
        final String author = call.argument("author");
        final boolean isPlaying = call.argument("isPlaying");
        showNotification(title, author, isPlaying);
        result.success(null);
        break;
      case "hideNotification":
        hideNotification();
        result.success(null);
        break;
      default:
        result.notImplemented();
    }
  }

  static void callEvent(String event) {

    FlutterMediaNotificationPlugin.channel.invokeMethod(event, null, new Result() {
      @Override
      public void success(Object o) {
        // this will be called with o = "some string"
      }

      @Override
      public void error(String s, String s1, Object o) {}

      @Override
      public void notImplemented() {}
    });
  }

  static void showNotification(String title, String author, boolean play) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      int importance = NotificationManager.IMPORTANCE_DEFAULT;
      NotificationChannel channel = new NotificationChannel(CHANNEL, CHANNEL, importance);
      channel.enableVibration(false);
      channel.setSound(null, null);
      NotificationManager notificationManager = registrar.context().getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(channel);
    }

    nPanel = new NotificationPanel(registrar.context(), title, author, play);
  }

  private void hideNotification() {
    nPanel.notificationCancel();
  }
}