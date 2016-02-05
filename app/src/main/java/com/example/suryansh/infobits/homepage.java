/**
 * Created by Suryansh on 2/3/2016.
 */
package com.example.suryansh.infobits;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class homepage extends Login{

    ViewPager viewPager;
    Swipe_adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new Swipe_adapter(this);
        viewPager.setAdapter(adapter);


    }

}
