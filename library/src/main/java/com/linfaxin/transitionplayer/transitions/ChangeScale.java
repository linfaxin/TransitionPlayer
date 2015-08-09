package com.linfaxin.transitionplayer.transitions;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.transitions.everywhere.Transition;
import android.transitions.everywhere.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by linfaxin on 2015/5/29.
 * Email: linlinfaxin@163.com
 */
public class ChangeScale extends Transition {
    private static final String PROPNAME_NAME1 = "android:scale:scalex";
    private static final String PROPNAME_NAME2 = "android:scale:scaley";
    private static final String FIELD_NAME1 = "scaleX";
    private static final String FIELD_NAME2 = "scaleY";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_NAME1, transitionValues.view.getScaleX());
        transitionValues.values.put(PROPNAME_NAME2, transitionValues.view.getScaleY());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_NAME1, transitionValues.view.getScaleX());
        transitionValues.values.put(PROPNAME_NAME2, transitionValues.view.getScaleY());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        final View view = endValues.view;
        float startValueX = (Float) startValues.values.get(PROPNAME_NAME1);
        float startValueY = (Float) startValues.values.get(PROPNAME_NAME2);
        float endValueX = (Float) endValues.values.get(PROPNAME_NAME1);
        float endValueY = (Float) endValues.values.get(PROPNAME_NAME2);
        AnimatorSet animatorSet = new AnimatorSet();
        if (startValueX != endValueX) {
            view.setScaleX(startValueX);
            animatorSet.playTogether(ObjectAnimator.ofFloat(view, FIELD_NAME1, startValueX, endValueX));
        }
        if (startValueY != endValueY) {
            view.setScaleY(startValueY);
            animatorSet.playTogether( ObjectAnimator.ofFloat(view, FIELD_NAME2, startValueY, endValueY));
        }
        if(animatorSet.getChildAnimations()!=null && animatorSet.getChildAnimations().size()>0){
            return animatorSet;
        }
        return null;
    }
}
