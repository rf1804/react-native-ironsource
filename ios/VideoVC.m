
#import "VideoVC.h"
#import <IronSource/IronSource.h>

@interface VideoVC () <ISRewardedVideoDelegate, ISOfferwallDelegate>

@end

@implementation VideoVC

- (void)viewDidLoad {
    
    [super viewDidLoad];
    
    if (_AdsType == NULL) {
        printf("Crash due to empty adtype");
    }
    
    [IronSource initWithAppKey:_AppKey adUnits:@[_AdsType]];
    
    if ([_AdsType isEqualToString:IS_OFFERWALL]) {
        [IronSource setOfferwallDelegate:self];

    } else {
        [IronSource setRewardedVideoDelegate:self];
    }
    
    
    [IronSource setUserId: _userId];
    [ISIntegrationHelper validateIntegration];
    [IronSource shouldTrackReachability:YES];

    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

//MARK: ---- ISRewardedVideoDelegate
//@param available The new rewarded video availability. YES if available //and ready to be shown, NO otherwise.
- (void)rewardedVideoHasChangedAvailability:(BOOL)available {
    NSLog(@"availability called");
    
    if (available) {
        
        ISPlacementInfo *placementInfo = [IronSource rewardedVideoPlacementInfo:@"DefaultRewardedVideo"];
        if(placementInfo != NULL)
        {
            NSString *rewardName = [placementInfo rewardName];
            NSNumber *rewardAmount = [placementInfo rewardAmount];
            
        }
        
        [IronSource showRewardedVideoWithViewController:self placement:placementInfo];

    }
    
}

//Called after a rewarded video has been viewed completely and the user is //eligible for reward.@param placementInfo An object that contains the //placement's reward name and amount.
- (void)didReceiveRewardForPlacement:(ISPlacementInfo *)placementInfo {
    
}
//Called after a rewarded video has attempted to show but failed.
//@param error The reason for the error
- (void)rewardedVideoDidFailToShowWithError:(NSError *)error {
}
//Called after a rewarded video has been opened.
- (void)rewardedVideoDidOpen {
    
}
//Called after a rewarded video has been dismissed.
- (void)rewardedVideoDidClose {
}
//Note: the events below are not available for all supported rewarded video ad networks. Check which events are available per ad network you choose //to include in your build.
//We recommend only using events which register to ALL ad networks you //include in your build.
//Called after a rewarded video has started playing.
- (void)rewardedVideoDidStart {
}
//Called after a rewarded video has finished playing.
- (void)rewardedVideoDidEnd {
}
//Invoked when the end user clicked on the RewardedVideo ad
- (void)didClickRewardedVideo:(ISPlacementInfo *)placementInfo {
}

//MARK: ---- ISOfferwallDelegate
- (void)offerwallHasChangedAvailability:(BOOL)available{
    NSLog(@"Offerwall --- availability called");
    
    if (available)
    {
        [IronSource showOfferwallWithViewController:self];

    }
}
//Called each time the Offerwall successfully loads for the user
-(void)offerwallDidShow {
    
}
//Called each time the Offerwall fails to show
//@param error - will contain the failure code and description
- (void)offerwallDidFailToShowWithError:(NSError *)error {
    
}
//Called each time the user completes an offer.
//@param creditInfo - A dictionary with the following key-value pairs:
//@"credits" - (integer) The number of credits the user has earned since //the last (void)didReceiveOfferwallCredits:(NSDictionary *)creditInfo event //that returned 'YES'. Note that the credits may represent multiple //completions (see return parameter).
//@"totalCredits" - (integer) The total number of credits ever earned by the
//user.
//@"totalCreditsFlag" - (boolean) In some cases, we won’t be able to //provide the exact amount of credits since the last event(specifically if the user clears the app’s data). In this case the ‘credits’ will be equal to the
//@"totalCredits", and this flag will be @(YES).
//@return The publisher should return a boolean stating if he handled this
//call (notified the user for example). if the return value is 'NO' the
//'credits' value will be added to the next call.
- (BOOL)didReceiveOfferwallCredits:(NSDictionary *)creditInfo {
    
    return true;
}
// Called when the method ‘-getOWCredits’
//failed to retrieve the users credit balance info.
//@param error - the error object with the failure info
- (void)didFailToReceiveOfferwallCreditsWithError:(NSError *)error {
    
}
//Called when the user closes the Offerwall
-(void)offerwallDidClose {
}

@end
