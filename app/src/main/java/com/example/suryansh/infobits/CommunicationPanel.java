package com.example.suryansh.infobits;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

interface CallbackListener<T> {
    void onComputingFinished(T arg);
}
public class CommunicationPanel extends homepage implements CallbackListener<String> {

    DrawerLayout drawerlayout;
    NavigationView navigationView;
    MenuItem cat;
    int convform;
    public final static String actString = "communication_panel";
    Integer start = 0;
    @Override
    public void onComputingFinished(String arg) {
        TextView comm = (TextView) findViewById(R.id.comms);
        comm.setText(arg);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerlayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerlayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerlayout.setDrawerListener(actionBarDrawerToggle);
        cat = navigationView.getMenu().getItem(0);
        cat.setChecked(true);
        getComm(start, cat.getItemId());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convform = cat.getItemId();
                Intent i = new Intent(CommunicationPanel.this,CommForms.class);
                startActivity(i);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    protected void getComm(Integer start,Integer cat){
        String urlString = apiURL + actString + ".php?username=" + username + "&password=" + password + "&start=" + start + "&cat=" + cat;
        new APICall(this).execute(urlString);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        convform = id;
        if (id == R.id.home) {
            // Handle the camera action
        } else if(id == R.id.breco) {

        } else if (id == R.id.dlost) {
            Intent i = new Intent(CommunicationPanel.this, CommForms.class);
            startActivity(i);
        } else if (id == R.id.database) {

        } else if (id == R.id.service) {

        } else if (id == R.id.breview) {

        } else if (id == R.id.feedback) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
