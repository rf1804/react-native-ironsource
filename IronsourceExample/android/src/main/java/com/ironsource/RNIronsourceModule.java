
package com.ironsource;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNIronsourceModule extends ReactContextBaseJavaModule {

  public RNIronsourceModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNIronsource";
  }

  @ReactMethod
  public void startIronSource(String appkey, String userid, String adtype) {
    ReactApplicationContext context = getReactApplicationContext();
    Intent intent = new Intent(context, IronSourceActivity.class);
    intent.putExtra("AppKey",appkey);
    intent.putExtra("userId",userid);
    intent.putExtra("AdsType",adtype);
    context.startActivity(intent);
  }
}
