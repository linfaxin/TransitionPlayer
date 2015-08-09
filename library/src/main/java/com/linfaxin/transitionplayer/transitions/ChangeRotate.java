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
public class ChangeRotate extends Transition {
    private static final String PROPNAME_NAME = "android:rotate:rotate";
    private static final String PROPNAME_NAME_X = "android:rotate:rotateX";
    private static final String PROPNAME_NAME_Y = "android:rotate:rotateY";
    private static final String FIELD_NAME = "rotation";
    private static final String FIELD_NAME_X = "rotationX";
    private static final String FIELD_NAME_Y = "rotationY";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_NAME, transitionValues.view.getRotation());
        transitionValues.values.put(PROPNAME_NAME_X, transitionValues.view.getRotationX());
        transitionValues.values.put(PROPNAME_NAME_Y, transitionValues.view.getRotationY());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_NAME, transitionValues.view.getRotation());
        transitionValues.values.put(PROPNAME_NAME_X, transitionValues.view.getRotationX());
        transitionValues.values.put(PROPNAME_NAME_Y, transitionValues.view.getRotationY());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        final View view = endValues.view;
        float startValue = (Float) startValues.values.get(PROPNAME_NAME);
        float startValueX = (Float) startValues.values.get(PROPNAME_NAME_X);
        float startValueY = (Float) startValues.values.get(PROPNAME_NAME_Y);
        float endValue = (Float) endValues.values.get(PROPNAME_NAME);
        float endValueX = (Float) endValues.values.get(PROPNAME_NAME_X);
        float endValueY = (Float) endValues.values.get(PROPNAME_NAME_Y);
        AnimatorSet animatorSet = new AnimatorSet();
        if (startValue != endValue) {
            view.setRotation(startValue);
            animatorSet.playTogether(ObjectAnimator.ofFloat(view, FIELD_NAME, startValue, endValue));
        }
        if (startValueX != endValueX) {
            view.setRotationX(startValueX);
            animatorSet.playTogether(ObjectAnimator.ofFloat(view, FIELD_NAME_X, startValueX, endValueX));
        }
        if (startValueY != endValueY) {
            view.setRotationY(startValueY);
            animatorSet.playTogether(ObjectAnimator.ofFloat(view, FIELD_NAME_Y, startValueY, endValueY));
        }
        if(animatorSet.getChildAnimations()!=null && animatorSet.getChildAnimations().size()>0){
            return animatorSet;
        }
        return null;
    }
}
