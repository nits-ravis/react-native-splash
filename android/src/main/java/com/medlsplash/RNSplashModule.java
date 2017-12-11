package com.medlsplash;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.lang.ref.WeakReference;

public class RNSplashModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static WeakReference<Activity> mActivity;
  private static Dialog mSplashDialog;
  private static ImageView splashImageView;

  public RNSplashModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }
  public static void showSplash(final Activity activity, final int drawableResource){
    if (activity == null) return;
    mActivity = new WeakReference<Activity>(activity);
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (!activity.isFinishing()) {
          mSplashDialog = new Dialog(activity, R.style.SplashScreen_Fullscreen);
          splashImageView = new ImageView(activity);
          splashImageView.setImageResource(drawableResource);
          LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
          splashImageView.setLayoutParams(layoutParams);
          splashImageView.setBackgroundColor(Color.TRANSPARENT);
          splashImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
          mSplashDialog.setCancelable(false);
          mSplashDialog.setContentView(splashImageView);
          if (!mSplashDialog.isShowing()) {
            mSplashDialog.show();
          }
        }
      }
    });
  }
  
  @ReactMethod
  public void hideLoadingViewWithDurationAndDelay(final double animationDuration, final double delay){
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        hideLoadingView(animationDuration);
      }
    }, ((long) delay * 1000));
  }
  
  @ReactMethod
  public void hideLoadingView(final double animationDuration){

    if (mActivity == null) return;

    mActivity.get().runOnUiThread(new Runnable() {
      @Override
      public void run() {

        if (mSplashDialog != null && mSplashDialog.isShowing()) {

          AlphaAnimation animation = new AlphaAnimation(1, 0);
          animation.setDuration((int)(animationDuration*1000));
          View view = ((ViewGroup)mSplashDialog.getWindow().getDecorView()).getChildAt(0);
          view.startAnimation(animation);

          animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
              mSplashDialog.dismiss();
              splashImageView = null;
              mSplashDialog = null;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
          });
        }
      }
    });
  }
  @Override
  public String getName() {
    return "RNSplash";
  }
}