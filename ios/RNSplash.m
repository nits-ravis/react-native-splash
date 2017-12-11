
#import "RNSplash.h"

@implementation RNSplash

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()
+(void)showLoadingView:(RCTRootView *)rootView andImageName:(NSString *)imageName{
    UIImageView *view = [[UIImageView alloc]initWithFrame:[UIScreen mainScreen].bounds];
    view.image = [UIImage imageNamed:imageName];
    [view setContentMode:UIViewContentModeScaleAspectFill];
    [self showCustomView:view inRootView:rootView];
}

+(void)showCustomView:(UIView *)view inRootView:(RCTRootView *)rootView {
    if (!view)
        return;
    
    if (![rootView isKindOfClass:[RCTRootView class]])
        return;
    
    [view setFrame:[UIScreen mainScreen].bounds];
    [[NSNotificationCenter defaultCenter] removeObserver:rootView  name:RCTContentDidAppearNotification object:rootView];
    [rootView setLoadingView:view];
}

RCT_EXPORT_METHOD(hideLoadingViewWithDurationAndDelay:(nonnull NSNumber *)duration :(nonnull NSNumber *)delay){
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(delay.intValue * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [self hideLoadingView:duration];
    });
}

RCT_EXPORT_METHOD(hideLoadingView:(nonnull NSNumber *)duration){
    UIViewController *rootController = [[[[UIApplication sharedApplication] delegate] window] rootViewController];
    if(![rootController.view isKindOfClass:[RCTRootView class]]){
        return;
    }
    RCTRootView *view = (RCTRootView *)rootController.view;
    [UIView transitionWithView:view duration:[duration doubleValue] options:UIViewAnimationOptionTransitionCrossDissolve animations:^{
        [view.loadingView setHidden:YES];
    } completion:^(BOOL finished) {
        [view.loadingView removeFromSuperview];
    }];
}

@end
