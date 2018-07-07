
#import "RNIronsource.h"
#import "VideoVC.h"

#if __has_include(<React/RCTBridge.h>)
#import <React/RCTBridge.h>
#else // Compatibility for RN version < 0.40
#import "RCTBridge.h"
#endif

@interface RNIronsource()

@end

@implementation RNIronsource
@synthesize bridge = _bridge;
- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE();
RCT_EXPORT_METHOD(startIronSource:(NSString *)appkey userid:(NSString *)userid adtype:(NSString *)adtype) {
    VideoVC *video = [[VideoVC alloc] initWithNibName:@"VideoVC" bundle:nil];
    video.AppKey = appkey;
    video.userId = userid;
    video.AdsType = adtype;
    
    [self presentViewController:video animated:true completion:nil];
}
@end
