package com.example.suryansh.infobits;

import android.support.v7.app.AppCompatActivity;
/**
 * Created by Suryansh on 2/3/2016.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class homepage extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    Swipe_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        toolbar = (Toolbar)findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new Swipe_adapter(this);
        viewPager.setAdapter(adapter);
 }


}
