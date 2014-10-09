package com.smg.videoview;

import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by 庹大伟 on 2014/9/23.
 */
public class MyUtils {

    public static void startAnimOut(RelativeLayout view, int duration) {
        RotateAnimation animation = new RotateAnimation(0, 180, view.getWidth() / 2, view.getHeight());
        animation.setDuration(duration);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    public static void startAnimIn(RelativeLayout view, int duration) {
        RotateAnimation animation = new RotateAnimation(180, 360, view.getWidth() / 2, view.getHeight());
        animation.setDuration(duration);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }
}
