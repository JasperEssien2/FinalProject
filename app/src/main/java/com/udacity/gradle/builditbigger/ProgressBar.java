package com.udacity.gradle.builditbigger;


import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class ProgressBar {
    private Animation mLoadingProgressBarAnim;
    private ImageView mLoadingProgressBar;

    public ProgressBar(ImageView view, Activity context){
        mLoadingProgressBar = view;
        view.setVisibility(View.GONE);
        mLoadingProgressBarAnim = AnimationUtils.loadAnimation(context, R.anim.anim_custom_loading_progress);
        mLoadingProgressBarAnim.setRepeatMode(Animation.RESTART);
        mLoadingProgressBarAnim.setRepeatCount(Animation.INFINITE);
    }

    public void startLoading(){
        mLoadingProgressBar.setVisibility(View.VISIBLE);
        mLoadingProgressBar.startAnimation(mLoadingProgressBarAnim);
    }

    public void stopLoading(){
        mLoadingProgressBarAnim.cancel();
        mLoadingProgressBar.clearAnimation();
        mLoadingProgressBar.setVisibility(View.GONE);
    }
}
