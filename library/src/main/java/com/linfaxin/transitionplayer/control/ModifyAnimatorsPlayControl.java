package com.linfaxin.transitionplayer.control;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.transitions.everywhere.Transition;

import com.linfaxin.transitionplayer.AnimatorUtils;

import java.util.ArrayList;

/**
 * Created by linfaxin on 2015/8/8.
 * Email: linlinfaxin@163.com
 */
public abstract class ModifyAnimatorsPlayControl implements PlayControl{
    @Override
    public void onPreRunAnimator(Transition transition, ArrayList<ValueAnimator> animators) {
        ArrayList<ValueAnimator> newAnimators = new ArrayList<>();
        for(int i=0, size=animators.size(); i<size; i++){
            Animator animator = onModifyAnimator(transition, animators.get(i));
            newAnimators.addAll(AnimatorUtils.collectValueAnimators(animator));
        }
        animators.clear();
        animators.addAll(newAnimators);
        newAnimators.clear();
    }
    protected abstract Animator onModifyAnimator(Transition transition, ValueAnimator animator);
}
