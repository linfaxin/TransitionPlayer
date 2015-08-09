package com.linfaxin.transitionplayer.interpolators;

import android.animation.TimeInterpolator;

/**
 * Created by linfaxin on 2015/8/9.
 * Email: linlinfaxin@163.com
 */
public class ReverseTimeInterpolator extends WrappedTimeInterpolator{
    public ReverseTimeInterpolator(TimeInterpolator wrapped) {
        super(wrapped);
    }
    @Override
    public float getInterpolation(float input) {
        return wrapped.getInterpolation(1-input);
    }
}
