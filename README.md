Flutter Media Notification
========

[![Pub Package](https://img.shields.io/pub/v/flutter_media_notification.svg)](https://pub.dev/packages/flutter_media_notification)
[![GitHub Issues](https://img.shields.io/github/issues/aliyazdi75/flutter_media_notification.svg)](https://github.com/aliyazdi75/flutter_media_notification/issues)
[![GitHub Forks](https://img.shields.io/github/forks/aliyazdi75/flutter_media_notification.svg)](https://github.com/aliyazdi75/flutter_media_notification/network)
[![GitHub Stars](https://img.shields.io/github/stars/aliyazdi75/flutter_media_notification.svg)](https://github.com/aliyazdi75/flutter_media_notification/stargazers)
[![GitHub License](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/aliyazdi75/flutter_media_notification/master/LICENSE)

### IOS not implemented
--------
I'm looking for mac to develop ios side, so it takes time.

Flutter Media Notification helps you to show your media notification and control streaming media beyond your flutter app.

This library is open source, stable and AndroidX supported. Development happens on [GitHub/flutter_media_notification](https://raw.githubusercontent.com/aliyazdi75/flutter_media_notification). Feel free to report issues or create a pull-request there. An example within a music player shares on [GitHub/flutter_music](https://github.com/aliyazdi75/flutter_music). If you found this project helpful or you learned something from the source code and want to thank me, consider donating me [Here](https://bahamta.com/41190-14395377).

The package is hosted on [dart packages](https://pub.dev/packages/flutter_media_notification).

Tutorial
--------

### Installation

Follow the installation instructions on [dart packages](https://pub.dev/packages/flutter_media_notification#-installing-tab--).

Import the package into your Dart code using:

```dart
import 'package:flutter_media_notification/flutter_media_notification.dart';
```

### Showing and Hiding

To show notification when your music is playing use the function:
```dart
MediaNotification.showNotification(title: 'Title', author: 'Song author');
```

To show notification when your music is pausing use the function:
```dart
MediaNotification.showNotification(title: 'Title', author: 'Song author', isPlaying : false);
```

To hide notification use the function:
```dart
MediaNotification.hideNotification();
```

### Setting a Listener

To setting a listener for an action use these `setListener` in `initState()` of `main.dart` to control your media beyond your app:
- set listener for play action: 
  ```dart
  MediaNotification.setListener('play', () {
    setState();
  });
  ```
- set listener for pause action: 
  ```dart
  MediaNotification.setListener('pause', () {
    setState();
  });
  ```
- set listener for next action: 
  ```dart
  MediaNotification.setListener('next', () {
    setState();
  });
  ```
  - set listener for previous action: 
  ```dart
  MediaNotification.setListener('prev', () {
    setState();
  });
  ```
- set listener for selecting on notification: 
  ```dart
  MediaNotification.setListener('select', () {
    setState();
  });
  ```
### License

The MIT License, see [LICENSE](https://github.com/aliyazdi75/flutter_media_notification/raw/masterLICENSE).