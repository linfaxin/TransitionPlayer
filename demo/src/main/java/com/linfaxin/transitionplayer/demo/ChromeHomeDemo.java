package com.linfaxin.transitionplayer.demo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.transitions.everywhere.ChangeBounds;
import android.transitions.everywhere.Scene;
import android.transitions.everywhere.Transition;
import android.transitions.everywhere.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;

import com.daimajia.swipe.SwipeLayout;
import com.linfaxin.transitionplayer.AnimatorUtils;
import com.linfaxin.transitionplayer.interpolators.LockEndTimeInterpolator;
import com.linfaxin.transitionplayer.interpolators.LockStartTimeInterpolator;
import com.linfaxin.transitionplayer.interpolators.MultiTimeInterpolator;
import com.linfaxin.transitionplayer.interpolators.ReverseTimeInterpolator;
import com.linfaxin.transitionplayer.transitions.ChangeAlpha;
import com.linfaxin.transitionplayer.TransitionPlayer;
import com.linfaxin.transitionplayer.control.ModifyAnimatorsPlayControl;
import com.linfaxin.transitionplayer.transitions.ChangeTextColor;

/**
 * Created by linfaxin on 2015/8/2.
 * Email: linlinfaxin@163.com
 */
public class ChromeHomeDemo extends ActionBarActivity{
    SwipeLayout swipeLayout;
    TransitionPlayer transitionPlayer = new TransitionPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_chrome_home);

        swipeLayout = (SwipeLayout) findViewById(R.id.swipeLayout);
        swipeLayout.getDragEdgeMap().clear();
        swipeLayout.addDrag(SwipeLayout.DragEdge.Top, swipeLayout.getChildAt(0));

        swipeLayout.addRevealListener(R.id.placeHolder, new SwipeLayout.OnRevealListener() {
            @Override
            public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
                transitionPlayer.setCurrentFraction(fraction);

            }
        });

        swipeLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                swipeLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                ViewGroup mSceneRoot = (ViewGroup) findViewById(R.id.sceneRootView);
                Scene scene = Scene.getSceneForLayout(mSceneRoot, R.layout.chrome_home_head_state_expand, ChromeHomeDemo.this);

                transitionPlayer.setPlayControl(new ModifyAnimatorsPlayControl() {
                    @Override
                    protected Animator onModifyAnimator(Transition transition, ValueAnimator animator) {
                        if (!(animator instanceof ObjectAnimator)) return animator;
                        Object target = ((ObjectAnimator) animator).getTarget();
                        if (!(target instanceof View)) return animator;
                        View targetView = (View) target;

                        //input view
                        if (targetView.getId() == R.id.editText) {
                            if (transition instanceof ChangeBounds) {
                                AnimatorSet animatorSet = new AnimatorSet();
                                for (ValueAnimator subAnimator : AnimatorUtils.splitAnimatorByValues(animator)) {
                                    animatorSet.playTogether(subAnimator);

                                    //let input view fast animate to right edge
                                    if (subAnimator instanceof ObjectAnimator
                                            && "right".equals(((ObjectAnimator) subAnimator).getPropertyName())) {
                                        MultiTimeInterpolator multi = new MultiTimeInterpolator();
                                        TimeInterpolator i = animator.getInterpolator();
                                        multi.addInterpolator(i, 6);
                                        LockEndTimeInterpolator end = new LockEndTimeInterpolator(i);
                                        multi.addInterpolator(end, 4);
                                        subAnimator.setInterpolator(multi);
                                    }
                                }
                                return animatorSet;
                            } else if (transition instanceof ChangeTextColor) {
                                TimeInterpolator i = animator.getInterpolator();
                                LockEndTimeInterpolator end = new LockEndTimeInterpolator(i);
                                ReverseTimeInterpolator r = new ReverseTimeInterpolator(i);
                                animator.setInterpolator(new MultiTimeInterpolator(i, end, r));
                            }
                        }

                        //logo view
                        if (targetView.getId() == R.id.imageView && transition instanceof ChangeAlpha) {
                            MultiTimeInterpolator multi = new MultiTimeInterpolator();
                            TimeInterpolator i = new DecelerateInterpolator(1.6f);
                            TimeInterpolator start = new LockStartTimeInterpolator(i);
                            multi.addInterpolator(start, 4);
                            multi.addInterpolator(i, 6);
                            animator.setInterpolator(multi);
                        }
                        return animator;
                    }
                });



                TransitionManager.go(scene, transitionPlayer);

                //after start transition, then init click.
                mSceneRoot.findViewById(R.id.editText).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        swipeLayout.close();
                    }
                });

                return false;
            }
        });
    }
}
