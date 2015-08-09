package com.linfaxin.transitionplayer.control;

import android.animation.ValueAnimator;
import android.transitions.everywhere.Transition;

import java.util.ArrayList;

/**
 * Created by linfaxin on 2015/8/8.
 * Email: linlinfaxin@163.com
 */
public interface PlayControl {
    void onPreRunAnimator(Transition transition, ArrayList<ValueAnimator> animators);
}
