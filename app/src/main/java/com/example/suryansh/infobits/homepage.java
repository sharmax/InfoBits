package com.example.suryansh.infobits;

import android.content.Intent;
import android.net.Uri;
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

public class homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    ViewPager viewPager;
    Swipe_adapter adapter;
    DrawerLayout drawerlayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    public final static String username = "F2011637p";
    public final static String password = "Andromeda";
    public final static String apiURL = "http://library/apis/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        toolbar = (Toolbar)findViewById(R.id.nav_toolbar);
        drawerlayout = (DrawerLayout)findViewById(R.id.drawer_layout);
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
        menuInflater.inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.user:
                Intent i11 = new Intent(homepage.this, user_settings.class);
                startActivity(i11);
                break;
            case R.id.login:
                Intent i12 = new Intent(homepage.this, login.class);
                startActivity(i12);
                break;
            case R.id.signup:
                Intent i13 = new Intent(homepage.this, signup.class);
                startActivity(i13);
                break;
            case R.id.logout:
                Toast.makeText(getApplicationContext(), "logout is selected", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home_id) {
            // Handle the camera action
        } else if (id == R.id.os_id) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://search.ebscohost.com/login.aspx?authtype=uid&user=bits2015&password=pilani&profile=eds"));
            startActivity(browserIntent);
        } else if (id == R.id.comm_id) {
            Intent i = new Intent(homepage.this, CommunicationPanel.class);
            startActivity(i);
        } else if (id == R.id.news_id) {

        } else if (id == R.id.ibb_id) {

        } else if (id == R.id.ill_id) {

        } else if (id == R.id.lf_id) {

        } else if (id == R.id.qp_id) {

        }else if (id == R.id.eb_id) {

        }else if (id == R.id.od_id) {

        }else if (id == R.id.opac_id) {

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

    public void onClickOPAC(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://172.21.1.37"));
        startActivity(browserIntent);
    }

    public void onClickFinder(View view) {
        Intent i = new Intent(homepage.this, CommunicationPanel.class);
        startActivity(i);
    }


}
