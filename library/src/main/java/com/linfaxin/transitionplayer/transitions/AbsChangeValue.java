package com.linfaxin.transitionplayer.transitions;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.RectEvaluator;
import android.animation.TypeEvaluator;
import android.transitions.everywhere.Transition;
import android.transitions.everywhere.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by linfaxin on 2015/8/9.
 * Email: linlinfaxin@163.com
 */
public abstract class AbsChangeValue extends Transition {
    protected String[] valueNames;
    protected final String propertyPrefix;
    private TypeEvaluator typeEvaluator;

    public AbsChangeValue(String... valueNames) {
        this(null, valueNames);
    }
    public AbsChangeValue(TypeEvaluator typeEvaluator, String... valueNames) {
        setTypeEvaluator(typeEvaluator);
        this.valueNames = valueNames;
        propertyPrefix = "android:" + getClass().getSimpleName() + ":";
    }

    public void setTypeEvaluator(TypeEvaluator typeEvaluator) {
        this.typeEvaluator = typeEvaluator;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        for(String valueName : valueNames) {
            transitionValues.values.put(propertyPrefix + valueName, getPropertyValue(transitionValues.view, valueName));
        }
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        for(String valueName : valueNames) {
            transitionValues.values.put(propertyPrefix + valueName, getPropertyValue(transitionValues.view, valueName));
        }
    }


    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        final View view = endValues.view;

        for(String valueName : valueNames) {
            Object startValue = startValues.values.get(propertyPrefix + valueName);
            Object endValue = endValues.values.get(propertyPrefix + valueName);
            if(startValue!=null && endValue!=null && startValue.getClass()!=endValue.getClass()) continue;
            Class c = null;
            if(startValue!=null) c= startValue.getClass();
            else if(endValue!=null) c=endValue.getClass();
            if(c==null) continue;
            ObjectAnimator animator;
            if(c==Float.class || c==float.class){
                animator = ObjectAnimator.ofFloat(view, valueName, (Float) startValue, (Float) endValue);
                animatorSet.playTogether(animator);

            }else if(c==Integer.class || c==int.class){
                animator = ObjectAnimator.ofInt(view, valueName, (Integer) startValue, (Integer) endValue);
                animatorSet.playTogether(animator);

            }else{
                animator = ObjectAnimator.ofObject(view, valueName, typeEvaluator, startValue, endValue);
                animatorSet.playTogether(animator);
            }
            setPropertyValue(view, valueName, startValue);
            if(typeEvaluator!=null) animator.setEvaluator(typeEvaluator);
        }

        ArrayList<Animator> animators = animatorSet.getChildAnimations();
        if(animators!=null && animators.size()>0){
            if(animators.size()==1) return animators.get(0);
            return animatorSet;
        }
        return null;
    }

    protected Object getPropertyValue(View view, String propertyName){
        Class c = view.getClass();
        while(c!=Object.class){
            try {
                String methodName = getMethodName("get", propertyName);
                Method m = c.getDeclaredMethod(methodName);
                m.setAccessible(true);
                return m.invoke(view);
            }catch (Exception ignore){
            }
            c = c.getSuperclass();
        }
        return null;
    }

    // We try several different types when searching for appropriate setter/getter functions.
    // The caller may have supplied values in a type that does not match the setter/getter
    // functions (such as the integers 0 and 1 to represent floating point values for alpha).
    // Also, the use of generics in constructors means that we end up with the Object versions
    // of primitive types (Float vs. float). But most likely, the setter/getter functions
    // will take primitive types instead.
    // So we supply an ordered array of other types to try before giving up.
    private static Class[] FLOAT_VARIANTS = {float.class, Float.class, double.class, int.class,
            Double.class, Integer.class};
    private static Class[] INTEGER_VARIANTS = {int.class, Integer.class, float.class, double.class,
            Float.class, Double.class};
    private static Class[] DOUBLE_VARIANTS = {double.class, Double.class, float.class, int.class,
            Float.class, Integer.class};
    protected void setPropertyValue(View view, String propertyName, Object value){
        Class c = view.getClass();
        Class valueType = value.getClass();
        while(c!=Object.class){
            String methodName = getMethodName("set", propertyName);
            Class[] typeVariants;
            if (valueType.equals(Float.class)) {
                typeVariants = FLOAT_VARIANTS;
            } else if (valueType.equals(Integer.class)) {
                typeVariants = INTEGER_VARIANTS;
            } else if (valueType.equals(Double.class)) {
                typeVariants = DOUBLE_VARIANTS;
            } else {
                typeVariants = new Class[1];
                typeVariants[0] = valueType;
            }
            for (Class typeVariant : typeVariants) {
                try {
                    Method m = c.getMethod(methodName, typeVariant);
                    m.setAccessible(true);
                    m.invoke(view, value);
                    return;
                } catch (Exception e) {
                    // Swallow the error and keep trying other variants
                }
            }
            c = c.getSuperclass();
        }
    }

    /**
     * Utility method to derive a setter/getter method name from a property name, where the
     * prefix is typically "set" or "get" and the first letter of the property name is
     * capitalized.
     *
     * @param prefix The precursor to the method name, before the property name begins, typically
     * "set" or "get".
     * @param propertyName The name of the property that represents the bulk of the method name
     * after the prefix. The first letter of this word will be capitalized in the resulting
     * method name.
     * @return String the property name converted to a method name according to the conventions
     * specified above.
     */
    static String getMethodName(String prefix, String propertyName) {
        if (propertyName == null || propertyName.length() == 0) {
            // shouldn't get here
            return prefix;
        }
        char firstLetter = Character.toUpperCase(propertyName.charAt(0));
        String theRest = propertyName.substring(1);
        return prefix + firstLetter + theRest;
    }
}
