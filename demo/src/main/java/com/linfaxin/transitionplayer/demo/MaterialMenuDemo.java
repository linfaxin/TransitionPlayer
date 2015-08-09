package com.linfaxin.transitionplayer.demo;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.transitions.everywhere.Scene;
import android.transitions.everywhere.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.linfaxin.transitionplayer.TransitionPlayer;

import java.util.Random;

/**
 * Created by linfaxin on 2015/8/1.
 * Email: linlinfaxin@163.com
 */
public class MaterialMenuDemo extends ActionBarActivity {
    DrawerLayout drawerLayout;
    ViewGroup mSceneRoot;
    TransitionPlayer transitionPlayer = new TransitionPlayer();
    int[] layouts = new int[]{R.layout.material_menu_option, R.layout.material_menu_back,
            R.layout.material_menu_x, R.layout.material_menu_y};
    int currentLayoutIndex = 0;
    boolean willOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_material_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mSceneRoot = (ViewGroup) findViewById(R.id.sceneRootView);


        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                transitionPlayer.setCurrentFraction(willOpen ? slideOffset : 1-slideOffset);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                preTransition();
                willOpen = true;
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                preTransition();
                willOpen = false;
            }
        });

        preTransition();
    }

    private void preTransition(){
        Random r = new Random();
        mSceneRoot.removeAllViews();

        int toIndex = r.nextInt(layouts.length);
        while (toIndex == currentLayoutIndex){
            toIndex = r.nextInt(layouts.length);
        }

        //lock current layout
        View.inflate(this, layouts[currentLayoutIndex], mSceneRoot);
        //pre next layout
        final Scene scene = Scene.getSceneForLayout(mSceneRoot, layouts[toIndex], MaterialMenuDemo.this);
        mSceneRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                TransitionManager.go(scene, transitionPlayer);
                return false;
            }
        });

        currentLayoutIndex = toIndex;
    }
}
