import 'package:flutter/material.dart';
import 'package:flutter_media_notification/flutter_media_notification.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String status = 'hidden';

  @override
  void initState() {
    super.initState();

    MediaNotification.setListener('pause', () {
      setState(() => status = 'pause');
    });

    MediaNotification.setListener('play', () {
      setState(() => status = 'play');
    });

    MediaNotification.setListener('next', () {});

    MediaNotification.setListener('prev', () {});

    MediaNotification.setListener('select', () {});
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: new Scaffold(
        appBar: new AppBar(
          title: const Text('Plugin example app'),
        ),
        body: new Center(
            child: Container(
          height: 250.0,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: <Widget>[
              FlatButton(
                child: Text('Show notification'),
                onPressed: () => MediaNotification.showNotification(
                    title: 'Title', author: 'Song author'),
              ),
              FlatButton(
                child: Text('Update notification'),
                onPressed: () => MediaNotification.showNotification(
                    title: 'New Title',
                    author: 'New Song author',
                    isPlaying: false),
              ),
              FlatButton(
                child: Text('Hide notification'),
                onPressed: MediaNotification.hideNotification,
              ),
              Text('Status: ' + status)
            ],
          ),
        )),
      ),
    );
  }
}
