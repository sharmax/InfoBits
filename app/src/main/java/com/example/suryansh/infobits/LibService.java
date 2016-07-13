package com.example.suryansh.infobits;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class LibService extends homepage {

    DrawerLayout drawerlayout;
    NavigationView navigationView;
    MenuItem cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar);
        drawerlayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        cat = navigationView.getMenu().getItem(0);
        cat.setChecked(true);
        View navHeader = navigationView.getHeaderView(0);
        ((TextView) navHeader.findViewById(R.id.name)).setText(name);
        ((TextView) navHeader.findViewById(R.id.email)).setText(email);
        ((ImageView) navHeader.findViewById(R.id.profile)).setImageResource(R.drawable.gk);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerlayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerlayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        super.onNavigationItemSelected(item);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public void onClickBulletin(View view) {
        Intent i = new Intent(LibService.this, infoBitsBulletin.class);
        startActivity(i);
    }

    public void onClickDailyNews(View view){
        Intent i = new Intent(LibService.this, DailyNews.class);
        startActivity(i);
    }

}
