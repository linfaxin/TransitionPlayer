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
public class ChangeTransition extends Transition {
    private static final String PROPNAME_NAME1 = "android:transition:translationx";
    private static final String PROPNAME_NAME2 = "android:transition:translationy";
//    private static final String PROPNAME_NAME3 = "android:transition:translationz";
    private static final String FIELD_NAME1 = "translationX";
    private static final String FIELD_NAME2 = "translationY";
//    private static final String FIELD_NAME3 = "translationZ";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_NAME1, transitionValues.view.getTranslationX());
        transitionValues.values.put(PROPNAME_NAME2, transitionValues.view.getTranslationY());
//        transitionValues.values.put(PROPNAME_NAME3, transitionValues.view.getTranslationZ());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_NAME1, transitionValues.view.getTranslationX());
        transitionValues.values.put(PROPNAME_NAME2, transitionValues.view.getTranslationY());
//        transitionValues.values.put(PROPNAME_NAME3, transitionValues.view.getTranslationZ());
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
//        float startValueZ = (Float) startValues.values.get(PROPNAME_NAME3);
        float endValueX = (Float) endValues.values.get(PROPNAME_NAME1);
        float endValueY = (Float) endValues.values.get(PROPNAME_NAME2);
//        float endValueZ = (Float) endValues.values.get(PROPNAME_NAME3);
        AnimatorSet animatorSet = new AnimatorSet();
        if (startValueX != endValueX) {
            view.setTranslationX(startValueX);
            animatorSet.playTogether(ObjectAnimator.ofFloat(view, FIELD_NAME1, startValueX, endValueX));
        }
        if (startValueY != endValueY) {
            view.setTranslationY(startValueY);
            animatorSet.playTogether(ObjectAnimator.ofFloat(view, FIELD_NAME2, startValueY, endValueY));
        }
//        if (startValueZ != endValueZ) {
//            view.setTranslationZ(startValueZ);
//            animatorSet.playTogether(ObjectAnimator.ofFloat(view, FIELD_NAME3, startValueZ, endValueZ));
//        }
        if(animatorSet.getChildAnimations()!=null && animatorSet.getChildAnimations().size()>0){
            return animatorSet;
        }
        return null;
    }
}
