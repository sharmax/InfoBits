package com.example.suryansh.infobits;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
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

public class homepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        Toolbar toolbar;
        ViewPager viewPager;
        Swipe_adapter adapter;
        DrawerLayout drawerlayout;
        ActionBarDrawerToggle actionBarDrawerToggle;

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new Swipe_adapter(this);
        viewPager.setAdapter(adapter);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerlayout.setDrawerListener(actionBarDrawerToggle);

            drawerlayout.setDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
            navigationView.setNavigationItemSelectedListener(this);

    }

        @Override
        protected void onPostCreate (Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.user:
                Toast.makeText(getApplicationContext(), "User icon is selected", Toast.LENGTH_LONG).show();
            case R.id.settings:
                Toast.makeText(getApplicationContext(), "Settings icon is selected", Toast.LENGTH_LONG).show();
            case R.id.help:
                Toast.makeText(getApplicationContext(), "Help icon is selected", Toast.LENGTH_LONG).show();
            default:
                return super.onOptionsItemSelected(item);

        }

    }
  @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home_id) {
            // Handle the camera action
        } else if (id == R.id.service1_id) {
            Intent i = new Intent(homepage.this, CommunicationPanel.class);
            startActivity(i);
        } else if (id == R.id.service2_id) {

        } else if (id == R.id.settings_id) {
            Intent i1 = new Intent(homepage.this, user_settings.class);
            startActivity(i1);
        } else if (id == R.id.help) {

        } else if (id == R.id.share_id) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



        //Methods to handle button clicks on homescreen

    public void onClickLibr(View view) {


    }

    public void onClickLibs(View view) {


    }

    public void onClickLost(View view) {


    }

    public void onClickFinder(View view) {
        Intent i = new Intent(homepage.this, CommunicationPanel.class);
        startActivity(i);
    }


}
