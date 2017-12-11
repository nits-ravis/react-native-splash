
#import <React/RCTBridgeModule.h>
#import <React/RCTRootView.h>

@interface RNSplash : NSObject <RCTBridgeModule>

+(void)showLoadingView:(RCTRootView *)rootView andImageName:(NSString *)imageName;
+(void)showCustomView:(UIView *)view inRootView:(RCTRootView *)rootView;

@end
