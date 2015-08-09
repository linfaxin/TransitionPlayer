package com.linfaxin.transitionplayer.interpolators;

import android.animation.TimeInterpolator;

/**
 * Created by linfaxin on 2015/8/9.
 * Email: linlinfaxin@163.com
 */
public abstract class WrappedTimeInterpolator implements TimeInterpolator{
    TimeInterpolator wrapped;
    public WrappedTimeInterpolator(TimeInterpolator wrapped) {
        this.wrapped = wrapped;
    }
}
