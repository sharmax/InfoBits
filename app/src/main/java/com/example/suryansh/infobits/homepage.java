package com.example.suryansh.infobits;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class homepage extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    Swipe_adapter adapter;
    DrawerLayout drawerlayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        toolbar = (Toolbar)findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new Swipe_adapter(this);
        viewPager.setAdapter(adapter);
        drawerlayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerlayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerlayout.setDrawerListener(actionBarDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}
