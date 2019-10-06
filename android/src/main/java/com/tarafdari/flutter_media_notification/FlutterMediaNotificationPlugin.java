package com.tarafdari.flutter_media_notification;


import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.core.content.ContextCompat;

/** FlutterMediaNotificationPlugin */
public class FlutterMediaNotificationPlugin implements MethodCallHandler {
  private static Registrar registrar;
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
      // todo get channelid and cover
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

    Intent serviceIntent = new Intent(registrar.context(), NotificationPanel.class);
    serviceIntent.putExtra("title", title);
    serviceIntent.putExtra("author", author);
    serviceIntent.putExtra("isPlaying", play);

    ContextCompat.startForegroundService(registrar.context(), serviceIntent);
  }

  private void hideNotification() {
    Intent serviceIntent = new Intent(registrar.context(), NotificationPanel.class);
    registrar.context().stopService(serviceIntent);
  }
}