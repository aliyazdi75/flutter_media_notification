#import "FlutterMediaNotificationPlugin.h"
#import <flutter_media_notification/flutter_media_notification-Swift.h>

@implementation FlutterMediaNotificationPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterMediaNotificationPlugin registerWithRegistrar:registrar];
}
@end
