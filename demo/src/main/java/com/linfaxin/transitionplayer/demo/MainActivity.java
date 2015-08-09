package com.linfaxin.transitionplayer.demo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ShowDrawerLayoutDemo(View view) {
        startActivity(new Intent(this, DrawerLayoutDemo.class));
    }

    public void ShowMaterialMenuDemo(View view) {
        startActivity(new Intent(this, MaterialMenuDemo.class));
    }

    public void ShowChromeHomeDemo(View view) {
        startActivity(new Intent(this, ChromeHomeDemo.class));
    }
}
