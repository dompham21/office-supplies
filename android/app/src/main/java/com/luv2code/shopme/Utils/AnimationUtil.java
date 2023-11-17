package com.luv2code.shopme.Utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.luv2code.shopme.R;

public class AnimationUtil {
    private static int ANIMATION_DURATION = 1000;
    private static boolean isAnimationStart;

    public static void translateAnimation(final ImageView viewAnimation, final View startView, final View endView,
                                          final Animation.AnimationListener animationListener) {

        float startViewWidthCenter = startView.getWidth() / 2;
        float startViewHeightCenter = startView.getHeight() / 2;

        float endViewWidthCenter = endView.getWidth() / 2;
        float endViewHeightCenter = endView.getHeight() / 2;

        final int[] startPos = new int[2];
        startView.getLocationOnScreen(startPos);

        final int[] endPos = new int[2];
        endView.getLocationOnScreen(endPos);

        float fromX = startPos[0];
        float toX = endPos[0] + endView.getWidth() - startViewWidthCenter;

        float fromY = startPos[1] - startViewHeightCenter;

        float toY = -800;

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new AccelerateInterpolator());

        int animationDuration = 200;

        ScaleAnimation startScaleAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f,
                startViewWidthCenter, startViewHeightCenter);

        startScaleAnimation.setDuration(animationDuration);

        TranslateAnimation translateAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        translateAnimation.setStartOffset(animationDuration);
        translateAnimation.setDuration(ANIMATION_DURATION);

        ScaleAnimation translateScaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                toX, toY);
        translateScaleAnimation.setStartOffset(animationDuration);
        translateScaleAnimation.setDuration(ANIMATION_DURATION);

        animationSet.addAnimation(startScaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(translateScaleAnimation);

        viewAnimation.startAnimation(animationSet);

        if(isAnimationStart) {
            viewAnimation.clearAnimation();
            if(animationListener != null) {
                animationListener.onAnimationEnd(null);
            }
        }
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimationStart = true;
                viewAnimation.setVisibility(View.VISIBLE);

                if(animationListener != null) {
                    animationListener.onAnimationStart(animation);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewAnimation.setVisibility(View.GONE);
                if(animationListener != null) {
                    animationListener.onAnimationEnd(animation);
                }
                isAnimationStart = false;

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if(animationListener != null) {
                    animationListener.onAnimationRepeat(animation);
                }
            }
        });

    }
}
