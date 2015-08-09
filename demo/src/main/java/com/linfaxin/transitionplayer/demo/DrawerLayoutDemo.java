package com.linfaxin.transitionplayer.demo;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.transitions.everywhere.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.linfaxin.transitionplayer.TransitionPlayer;

/**
 * Created by linfaxin on 2015/8/1.
 * Email: linlinfaxin@163.com
 */
public class DrawerLayoutDemo extends ActionBarActivity {
    DrawerLayout drawerLayout;
    ImageView imageView;
    ViewGroup mSceneRoot;
    TransitionPlayer transitionPlayer = new TransitionPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_drawerlayout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        imageView = (ImageView) findViewById(R.id.imageView);
        mSceneRoot = (ViewGroup) findViewById(R.id.sceneRootView);

        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                //after layout is ok, then change view
                TransitionManager.beginDelayedTransition(mSceneRoot, transitionPlayer);

                imageView.setRotation(360);
                imageView.setRotationX(270);
                imageView.setRotationY(180);
                imageView.setTranslationX(70 * getResources().getDisplayMetrics().density);
                imageView.setScaleX(0.2f);
                imageView.setScaleY(0f);
                imageView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                return false;
            }
        });

        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                transitionPlayer.setCurrentFraction(slideOffset);
            }
        });
    }
}
