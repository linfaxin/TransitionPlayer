package com.linfaxin.transitionplayer;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.transitions.everywhere.Transition;
import android.transitions.everywhere.TransitionSet;

import com.linfaxin.transitionplayer.interpolators.ReverseTimeInterpolator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linfaxin on 2015/8/8.
 * Email: linlinfaxin@163.com
 */
public class AnimatorUtils {

    public static List<ValueAnimator> splitAnimatorByValues(ValueAnimator animator){
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        PropertyValuesHolder[] valuesHolders = animator.getValues();
        if(valuesHolders!=null && valuesHolders.length>0){
            for(PropertyValuesHolder valuesHolder : valuesHolders){
                ValueAnimator clone = animator.clone();
                if(clone instanceof ObjectAnimator){
                    ((ObjectAnimator) clone).setPropertyName(valuesHolder.getPropertyName());
                }
                clone.setValues(valuesHolder);
                animators.add(clone);
            }
        }else{
            animators.add(animator);
        }
        return animators;
    }

    public static float getAnimatorDurationScale() {
        try {
            Method method = ValueAnimator.class.getDeclaredMethod("getDurationScale");
            method.setAccessible(true);
            float scale = ((Float) method.invoke(ValueAnimator.class)).floatValue();
            if(scale<=0){
                method = ValueAnimator.class.getDeclaredMethod("setDurationScale", float.class);
                method.setAccessible(true);
                method.invoke(ValueAnimator.class, 1);
                return 1;
            }
            return scale;
        } catch (Exception ignore) {
        }
        return 1;
    }

    public static List<ValueAnimator> collectValueAnimators(Animator animator){
        ArrayList<ValueAnimator> valueAnimators = new ArrayList<ValueAnimator>();
        for(Animator anim : collectAnimators(animator)){
            if(anim instanceof ValueAnimator){
                valueAnimators.add((ValueAnimator) anim);
            }
        }
        return valueAnimators;
    }

    public static List<Animator> collectAnimators(Animator animator){
        if(animator instanceof AnimatorSet){
            return collectAnimatorsFromSet((AnimatorSet) animator);

        }else{
            ArrayList<Animator> animators = new ArrayList<Animator>();
            animators.add(animator);
            return animators;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static List<Animator> collectAnimatorsFromSet(AnimatorSet animatorSet){
        ArrayList<Animator> animators = new ArrayList<Animator>();
        for(Animator animator : animatorSet.getChildAnimations()){
            animators.addAll(collectAnimators(animator));
        }
        return animators;
    }

    public static List<Animator> collectAnimators(Transition transition){
        if(transition instanceof TransitionSet){
            return collectAnimatorsFromSet((TransitionSet) transition);
        }
        return collectAnimatorsFromTransition(transition);
    }

    private static List<Animator> collectAnimatorsFromSet(TransitionSet transitionSet){
        ArrayList<Animator> animators = new ArrayList<Animator>();
        for(int i=0, count = transitionSet.getTransitionCount(); i<count; i++){
            animators.addAll(collectAnimators(transitionSet.getTransitionAt(i)));
        }
        return animators;
    }

    private static List<Animator> collectAnimatorsFromTransition(Transition transition){
        try {
            Field field = Transition.class.getDeclaredField("mAnimators");
            field.setAccessible(true);
            return (List<Animator>) field.get(transition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Animator>();
    }

    public static void reverseAnimator(Animator animator){
        if(animator instanceof ValueAnimator){
            animator.setInterpolator(new ReverseTimeInterpolator(((ValueAnimator)animator).getInterpolator()));
        }else if(Build.VERSION.SDK_INT>=18){
            animator.setInterpolator(new ReverseTimeInterpolator(animator.getInterpolator()));
        }
    }
}
