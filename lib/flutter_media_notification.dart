import 'dart:async';

import 'package:flutter/services.dart';
import 'package:meta/meta.dart';

class FlutterMediaNotification {
  static const MethodChannel _channel =
      const MethodChannel('flutter_media_notification');
  static Map<String, Function> _listeners = new Map();

  static Future<dynamic> _utilsHandler(MethodCall methodCall) async {
    _listeners.forEach((event, callback) {
      if (methodCall.method == event) {
        callback();
      }
    });
  }

  static Future showNotification(
      {@required title, @required author, isPlaying = true}) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'title': title,
      'author': author,
      'isPlaying': isPlaying
    };
    await _channel.invokeMethod('showNotification', params);

    _channel.setMethodCallHandler(_utilsHandler);
  }

  static Future hideNotification() async {
    await _channel.invokeMethod('hideNotification');
  }

  static setListener(String event, Function callback) {
    _listeners.addAll({event: callback});
  }
}
