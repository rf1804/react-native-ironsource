package com.ironsource;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ironsource.adapters.supersonicads.SupersonicConfig;
import com.ironsource.mediationsdk.EBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.OfferwallListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;

public class IronSourceActivity extends Activity implements RewardedVideoListener, OfferwallListener, InterstitialListener {

    private final String TAG = "IronSourceActivity";
    private String APP_KEY = "4ea90fad";
    private String FALLBACK_USER_ID = "userId";
  /*  private Button mVideoButton;
    private Button mOfferwallButton;
    private Button mInterstitialLoadButton;
    private Button mInterstitialShowButton;*/

    private Placement mPlacement;

    private FrameLayout mBannerParentLayout;
    private IronSourceBannerLayout mIronSourceBannerLayout;
    private final String  AppKey="AppKey";
    private final String userId="userId";
    private final String AdsType="AdsType";
    private final String IS_REWARDED_VIDEO="IS_REWARDED_VIDEO";
    private final String IS_OFFERWALL="IS_OFFERWALL";
    private final String IS_BANNER="IS_BANNER";
    private final String IS_INTERSTITIAL="IS_INTERSTITIAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        if(getIntent().hasExtra(AppKey) && getIntent().hasExtra(userId)){
            APP_KEY=getIntent().getStringExtra(AppKey);
            FALLBACK_USER_ID=getIntent().getStringExtra(userId);

            //The integrationHelper is used to validate the integration. Remove the integrationHelper before going live!
            IntegrationHelper.validateIntegration(this);
            initUIElements();
            startIronSourceInitTask();

            //Network Connectivity Status
            IronSource.shouldTrackNetworkState(this, true);

            if(getIntent().hasExtra(AdsType)){
                if(getIntent().getStringExtra(AdsType).equals(IS_REWARDED_VIDEO)){
                    if (IronSource.isRewardedVideoAvailable())    //show rewarded video
                        IronSource.showRewardedVideo();
                }else if(getIntent().getStringExtra(AdsType).equals(IS_OFFERWALL)){
                    if (IronSource.isOfferwallAvailable()) //show the offerwall
                        IronSource.showOfferwall();
                }else if(getIntent().getStringExtra(AdsType).equals(IS_INTERSTITIAL)){
                        IronSource.loadInterstitial();
                }else if(getIntent().getStringExtra(AdsType).equals(IS_BANNER)){
                    createAndloadBanner();
                }
            }else {
                Toast.makeText(this,"Add type missing",Toast.LENGTH_SHORT).show();
            }
        }else {
           Toast.makeText(this,"Something went wong",Toast.LENGTH_SHORT).show();
        }




    }
    private void startIronSourceInitTask(){

        // getting advertiser id should be done on a background thread
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return IronSource.getAdvertiserId(IronSourceActivity.this);
            }
            @Override
            protected void onPostExecute(String advertisingId) {
                if (TextUtils.isEmpty(advertisingId)) {
                    advertisingId = FALLBACK_USER_ID;
                }
                // we're using an advertisingId as the 'userId'
                initIronSource(APP_KEY, advertisingId);
            }
        };
        task.execute();
    }

    private void initIronSource(String appKey, String userId) {
        // Be sure to set a listener to each product that is being initiated
        // set the IronSource rewarded video listener
        IronSource.setRewardedVideoListener(this);
        // set the IronSource offerwall listener
        IronSource.setOfferwallListener(this);
        // set client side callbacks for the offerwall
        SupersonicConfig.getConfigObj().setClientSideCallbacks(true);
        // set the interstitial listener
        IronSource.setInterstitialListener(this);

        // set the IronSource user id
        IronSource.setUserId(userId);
        // init the IronSource SDK
        IronSource.init(this, appKey);

      //  updateButtonsState();

        // In order to work with IronSourceBanners you need to add Providers who support banner ad unit and uncomment next line
         //createAndloadBanner();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // call the IronSource onResume method
        IronSource.onResume(this);
       // updateButtonsState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // call the IronSource onPause method
        IronSource.onPause(this);
       // updateButtonsState();
    }

    /**
     * Handle the button state according to the status of the IronSource producs
     */
   /* private void updateButtonsState() {
            handleVideoButtonState(IronSource.isRewardedVideoAvailable());
            handleOfferwallButtonState(IronSource.isOfferwallAvailable());
            handleLoadInterstitialButtonState(true);
            handleInterstitialShowButtonState(false);

    }
*/


    /**
     * initialize the UI elements of the activity
     */
    private void initUIElements() {
    /*    mVideoButton = (Button) findViewById(R.id.rv_button);
        mVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // check if video is available
                    if (IronSource.isRewardedVideoAvailable())
                        //show rewarded video
                        IronSource.showRewardedVideo();
            }
        });

        mOfferwallButton = (Button) findViewById(R.id.ow_button);
        mOfferwallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //show the offerwall
                    if (IronSource.isOfferwallAvailable())
                        IronSource.showOfferwall();
            }
        });

        mInterstitialLoadButton = (Button) findViewById(R.id.is_button_1);
        mInterstitialLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IronSource.loadInterstitial();
            }
        });


        mInterstitialShowButton = (Button) findViewById(R.id.is_button_2);
        mInterstitialShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // check if interstitial is available
                    if (IronSource.isInterstitialReady()) {
                        //show the interstitial
                        IronSource.showInterstitial();
                }
            }
        });

        TextView versionTV = (TextView) findViewById(R.id.version_txt);
        versionTV.setText(getResources().getString(R.string.version) + " " + IronSourceUtils.getSDKVersion());*/

        mBannerParentLayout = (FrameLayout) findViewById(R.id.banner_footer);
    }


    /**
     * Creates and loads IronSource Banner
     *
     */
    private void createAndloadBanner() {
        // choose banner size
        EBannerSize size = EBannerSize.BANNER;

        // instantiate IronSourceBanner object, using the IronSource.createBanner API
        mIronSourceBannerLayout = IronSource.createBanner(this, size);

        // add IronSourceBanner to your container
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mBannerParentLayout.addView(mIronSourceBannerLayout, 0, layoutParams);

        if (mIronSourceBannerLayout != null) {
            // set the banner listener
            mIronSourceBannerLayout.setBannerListener(new BannerListener() {
                @Override
                public void onBannerAdLoaded() {
                    Log.d(TAG, "onBannerAdLoaded");
                    // since banner container was "gone" by default, we need to make it visible as soon as the banner is ready
                    mBannerParentLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onBannerAdLoadFailed(IronSourceError error) {
                    Log.d(TAG, "onBannerAdLoadFailed" + " " + error);
                }

                @Override
                public void onBannerAdClicked() {
                    Log.d(TAG, "onBannerAdClicked");
                }

                @Override
                public void onBannerAdScreenPresented() {
                    Log.d(TAG, "onBannerAdScreenPresented");
                }

                @Override
                public void onBannerAdScreenDismissed() {
                    Log.d(TAG, "onBannerAdScreenDismissed");
                }

                @Override
                public void onBannerAdLeftApplication() {
                    Log.d(TAG, "onBannerAdLeftApplication");
                }
            });

            // load ad into the created banner
            IronSource.loadBanner(mIronSourceBannerLayout);
        } else {
            Toast.makeText(IronSourceActivity.this, "IronSource.createBanner returned null", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Destroys IronSource Banner and removes it from the container
     *
     */
    private void destroyAndDetachBanner() {
        IronSource.destroyBanner(mIronSourceBannerLayout);
        if (mBannerParentLayout != null) {
            mBannerParentLayout.removeView(mIronSourceBannerLayout);
        }
    }

    /**
     * Set the Rewareded Video button state according to the product's state
     *
     * @param available if the video is available
     */
  /*  public void handleVideoButtonState(final boolean available) {
        final String text;
        final int color;
        if (available) {
            color = Color.BLUE;
            text = getResources().getString(R.string.show) + " " + getResources().getString(R.string.rv);
        } else {
            color = Color.BLACK;
            text = getResources().getString(R.string.initializing) + " " + getResources().getString(R.string.rv);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mVideoButton.setTextColor(color);
                mVideoButton.setText(text);
                mVideoButton.setEnabled(available);

            }
        });
    }*/

    /**
     * Set the Rewareded Video button state according to the product's state
     *
     * @param available if the offerwall is available
     */
   /* public void handleOfferwallButtonState(final boolean available) {
        final String text;
        final int color;
        if (available) {
            color = Color.BLUE;
            text = getResources().getString(R.string.show) + " " + getResources().getString(R.string.ow);
        } else {
            color = Color.BLACK;
            text = getResources().getString(R.string.initializing) + " " + getResources().getString(R.string.ow);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mOfferwallButton.setTextColor(color);
                mOfferwallButton.setText(text);
                mOfferwallButton.setEnabled(available);

            }
        });

    }
*/
    /**
     * Set the Interstitial button state according to the product's state
     *
     * @param available if the interstitial is available
     */
   /* public void handleLoadInterstitialButtonState(final boolean available) {
        Log.d(TAG, "handleInterstitialButtonState | available: " + available);
        final String text;
        final int color;
        if (available) {
            color = Color.BLUE;
            text = getResources().getString(R.string.load) + " " + getResources().getString(R.string.is);
        } else {
            color = Color.BLACK;
            text = getResources().getString(R.string.initializing) + " " + getResources().getString(R.string.is);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mInterstitialLoadButton.setTextColor(color);
                mInterstitialLoadButton.setText(text);
                mInterstitialLoadButton.setEnabled(available);
            }
        });

    }
*/
   /* *//**
     * Set the Show Interstitial button state according to the product's state
     *
     * @param available if the interstitial is available
     */
 /*   public void handleInterstitialShowButtonState(final boolean available) {
        final int color;
        if (available) {
            color = Color.BLUE;
        } else {
            color = Color.BLACK;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mInterstitialShowButton.setTextColor(color);
                mInterstitialShowButton.setEnabled(available);
            }
        });
    }
*/
    // --------- IronSource Rewarded Video Listener ---------

    @Override
    public void onRewardedVideoAdOpened() {
        // called when the video is opened
        Log.d(TAG, "onRewardedVideoAdOpened");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        // called when the video is closed
        Log.d(TAG, "onRewardedVideoAdClosed");
        // here we show a dialog to the user if he was rewarded
        if (mPlacement != null) {
            // if the user was rewarded
            showRewardDialog(mPlacement);
            mPlacement = null;
        }
    }

    @Override
    public void onRewardedVideoAvailabilityChanged(boolean b) {
        // called when the video availbility has changed
        Log.d(TAG, "onRewardedVideoAvailabilityChanged" + " " + b);
      //  handleVideoButtonState(b);
    }

    @Override
    public void onRewardedVideoAdStarted() {
        // called when the video has started
        Log.d(TAG, "onRewardedVideoAdStarted");
    }

    @Override
    public void onRewardedVideoAdEnded() {
        // called when the video has ended
        Log.d(TAG, "onRewardedVideoAdEnded");
    }

    @Override
    public void onRewardedVideoAdRewarded(Placement placement) {
        // called when the video has been rewarded and a reward can be given to the user
        Log.d(TAG, "onRewardedVideoAdRewarded" + " " + placement);
        mPlacement = placement;

    }

    @Override
    public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {
        // called when the video has failed to show
        // you can get the error data by accessing the IronSourceError object
        // IronSourceError.getErrorCode();
        // IronSourceError.getErrorMessage();
        Log.d(TAG, "onRewardedVideoAdShowFailed" + " " + ironSourceError);
    }

    @Override
    public void onRewardedVideoAdClicked(Placement placement) {

    }

    // --------- IronSource Offerwall Listener ---------

    @Override
    public void onOfferwallAvailable(boolean available) {
      //  handleOfferwallButtonState(available);
    }

    @Override
    public void onOfferwallOpened() {
        // called when the offerwall has opened
        Log.d(TAG, "onOfferwallOpened");
    }

    @Override
    public void onOfferwallShowFailed(IronSourceError ironSourceError) {
        // called when the offerwall failed to show
        // you can get the error data by accessing the IronSourceError object
        // IronSourceError.getErrorCode();
        // IronSourceError.getErrorMessage();
        Log.d(TAG, "onOfferwallShowFailed" + " " + ironSourceError);
    }

    @Override
    public boolean onOfferwallAdCredited(int credits, int totalCredits, boolean totalCreditsFlag) {
        Log.d(TAG, "onOfferwallAdCredited" + " credits:" + credits + " totalCredits:" + totalCredits + " totalCreditsFlag:" + totalCreditsFlag);
        return false;
    }

    @Override
    public void onGetOfferwallCreditsFailed(IronSourceError ironSourceError) {
        // you can get the error data by accessing the IronSourceError object
        // IronSourceError.getErrorCode();
        // IronSourceError.getErrorMessage();
        Log.d(TAG, "onGetOfferwallCreditsFailed" + " " + ironSourceError);
    }

    @Override
    public void onOfferwallClosed() {
        // called when the offerwall has closed
        Log.d(TAG, "onOfferwallClosed");
    }

    // --------- IronSource Interstitial Listener ---------

    @Override
    public void onInterstitialAdClicked() {
        // called when the interstitial has been clicked
        Log.d(TAG, "onInterstitialAdClicked");
    }

    @Override
    public void onInterstitialAdReady() {
        // called when the interstitial is ready
        Log.d(TAG, "onInterstitialAdReady");
        if (IronSource.isInterstitialReady()) {
            IronSource.showInterstitial();
        }
       // handleInterstitialShowButtonState(true);
    }

    @Override
    public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
        // called when the interstitial has failed to load
        // you can get the error data by accessing the IronSourceError object
        // IronSourceError.getErrorCode();
        // IronSourceError.getErrorMessage();
        Log.d(TAG, "onInterstitialAdLoadFailed" + " " + ironSourceError);
     //   handleInterstitialShowButtonState(false);
    }

    @Override
    public void onInterstitialAdOpened() {
        // called when the interstitial is shown
        Log.d(TAG, "onInterstitialAdOpened");
    }

    @Override
    public void onInterstitialAdClosed() {
        // called when the interstitial has been closed
        Log.d(TAG, "onInterstitialAdClosed");
      //  handleInterstitialShowButtonState(false);
    }

    @Override
    public void onInterstitialAdShowSucceeded() {
        // called when the interstitial has been successfully shown
        Log.d(TAG, "onInterstitialAdShowSucceeded");
    }

    @Override
    public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
        // called when the interstitial has failed to show
        // you can get the error data by accessing the IronSourceError object
        // IronSourceError.getErrorCode();
        // IronSourceError.getErrorMessage();
        Log.d(TAG, "onInterstitialAdShowFailed" + " " + ironSourceError);
      //  handleInterstitialShowButtonState(false)  ;
    }

    public void showRewardDialog(Placement placement) {
        AlertDialog.Builder builder = new AlertDialog.Builder(IronSourceActivity.this);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setTitle(getResources().getString(R.string.rewarded_dialog_header));
        builder.setMessage(getResources().getString(R.string.rewarded_dialog_message) + " " + placement.getRewardAmount() + " " + placement.getRewardName());
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
