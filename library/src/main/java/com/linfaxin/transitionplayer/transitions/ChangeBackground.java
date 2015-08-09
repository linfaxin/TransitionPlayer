package com.linfaxin.transitionplayer.transitions;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.transitions.everywhere.Transition;
import android.transitions.everywhere.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by linfaxin on 2015/5/29.
 * Email: linlinfaxin@163.com
 */
public class ChangeBackground extends AbsChangeValue {
    private static final String FIELD_NAME = "backgroundColor";

    public ChangeBackground() {
        super(new ArgbEvaluator(), FIELD_NAME);
    }

    @Override
    protected Object getPropertyValue(View view, String propertyName) {
        if(FIELD_NAME.equals(propertyName)){
            Drawable drawable = view.getBackground();
            if(drawable instanceof ColorDrawable){
                return ((ColorDrawable) drawable).getColor();
            }
        }
        return super.getPropertyValue(view, propertyName);
    }
}
