package com.example.suryansh.infobits;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.menu_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.user:
                Toast.makeText(getApplicationContext(),"User icon is selected",Toast.LENGTH_LONG).show();
            case R.id.settings:
                Toast.makeText(getApplicationContext(),"Settings icon is selected",Toast.LENGTH_LONG).show();
            case R.id.help:
                Toast.makeText(getApplicationContext(),"Help icon is selected",Toast.LENGTH_LONG).show();
            default:
                return super.onOptionsItemSelected(item);

        }

    }
    //Methods to handle button clicks on homescreen
    public void onClickLibr(View view){


    }

    public void onClickLibs(View view){


    }

    public void onClickLost(View view){


    }

    public void onClickFinder(View view){


    }



}
