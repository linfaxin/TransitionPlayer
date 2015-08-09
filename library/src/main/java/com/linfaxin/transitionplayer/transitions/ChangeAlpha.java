package com.linfaxin.transitionplayer.transitions;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.transitions.everywhere.Transition;
import android.transitions.everywhere.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by linfaxin on 2015/5/29.
 * Email: linlinfaxin@163.com
 */
public class ChangeAlpha extends Transition {
    private static final String PROPNAME_ALPHA = "android:alpha:alpha";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_ALPHA, transitionValues.view.getAlpha());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_ALPHA, transitionValues.view.getAlpha());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        final View view = endValues.view;
        float startAlpha = (Float) startValues.values.get(PROPNAME_ALPHA);
        float endAlpha = (Float) endValues.values.get(PROPNAME_ALPHA);
        if (startAlpha != endAlpha) {
            view.setAlpha(startAlpha);
            return ObjectAnimator.ofFloat(view, "alpha", startAlpha, endAlpha);
        }
        return null;
    }
}
