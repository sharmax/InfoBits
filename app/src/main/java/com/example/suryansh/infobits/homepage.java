package com.example.suryansh.infobits;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    ViewPager viewPager;
    Swipe_Adapter adapter;
    DrawerLayout drawerlayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DBHandler dbhandler;
    JSONObject internal;
    ArrayList<String> urls = new ArrayList<>();
    ArrayList<Bitmap> images = new ArrayList<>();
    public final static String username = "library";
    public final static String password = "123456789";
    public final static String usercat = "Student";
    public final static String apiURL = "http://172.21.1.15/apis/";
    public final static String imageApiURL = "http://172.21.1.15/uploads/";
    File dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        
        toolbar = (Toolbar)findViewById(R.id.nav_toolbar);
        drawerlayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        dir = getFilesDir();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerlayout.setDrawerListener(actionBarDrawerToggle);
        drawerlayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        dbhandler = new DBHandler(this,null,null);
        internal = dbhandler.selectData(2,"1 ORDER BY id ASC");
        getNotices();
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
            Intent i = new Intent(homepage.this, ConnectWithLibrary.class);
            startActivity(i);
        } else if (id == R.id.news_id) {
            Intent i = new Intent(homepage.this, DailyNews.class);
            startActivity(i);
        } else if (id == R.id.ibb_id) {

        } else if (id == R.id.ill_id) {

        } else if (id == R.id.lf_id) {

        } else if (id == R.id.qp_id) {
            Intent qpI = new Intent(homepage.this, downloadable_links.class);
            qpI.putExtra("title", "Question Papers");
            qpI.putExtra("reference", "Question Papers");
            startActivity(qpI);

        }else if (id == R.id.eb_id) {
            Intent i = new Intent(homepage.this, ebooks.class);
            startActivity(i);

        }else if (id == R.id.od_id) {

        }else if (id == R.id.opac_id) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

        //Methods to handle button clicks on homescreen

    public void onClickLibr(View view) {
        Intent i = new Intent(homepage.this, LibRes.class);
        startActivity(i);
    }

    public void onClickLibs(View view) {

    }

    public void onClickOs(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://search.ebscohost.com/login.aspx?authtype=uid&user=bits2015&password=pilani&profile=eds"));
        startActivity(browserIntent);
    }

    public void onClickDN(View view) {
        Intent i = new Intent(homepage.this, DailyNews.class);
        startActivity(i);
    }

    public void onClickOPAC(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://172.21.1.37"));
        startActivity(browserIntent);
    }

    public void onClickCWL(View view) {
        Intent i = new Intent(homepage.this, ConnectWithLibrary.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if (this.drawerlayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerlayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void getNotices(){
        urls.clear();
        images.clear();
        internal = dbhandler.selectData(2,"1 ORDER BY id ASC");
        Iterator iter = internal.keys();
        try {
            String url = "";
            while(iter.hasNext()){
                String key = iter.next().toString();
                JSONObject data = (JSONObject) internal.get(key);
                File image = new File(dir, data.get("image").toString());
                FileInputStream fileInput = new FileInputStream(image);
                images.add(BitmapFactory.decodeStream(fileInput));
                try{
                    url = URLDecoder.decode(data.get("link").toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Boolean m = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$").matcher(url).find();
                if(!url.isEmpty()) {
                    if (url.contains("http://")) {
                        if (!url.contains("www.")) {
                            if (!m) {
                                url = "http://www." + url.substring(url.indexOf("http://") + 7);
                            }
                        }
                    } else {
                        if (url.contains("www.") || m) {
                            url = "http://" + url;
                        } else {
                            url = "http://www." + url;
                        }
                    }
                }
                urls.add(url);
                fileInput.close();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter = new Swipe_Adapter(this, images, urls);
        viewPager.setAdapter(adapter);
        viewPager.setVisibility(View.VISIBLE);
    }

    public class Swipe_Adapter extends PagerAdapter {
        private ArrayList<Bitmap> image_resources = new ArrayList<>();
        private ArrayList<String> urls = new ArrayList<>();
        private Context ctx;
        private LayoutInflater layoutinflator;

        public Swipe_Adapter(Context ctx, ArrayList<Bitmap> images,  ArrayList<String> urls){
            this.ctx = ctx;
            this.image_resources = images;
            this.urls = urls;
        }

        @Override
        public int getCount() {
            return image_resources.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == (RelativeLayout)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            layoutinflator =(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View item_view = layoutinflator.inflate(R.layout.swipe_image,container,false);
            ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);
            imageView.setImageBitmap(image_resources.get(position));
            imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String url = urls.get(viewPager.getCurrentItem());
                    if(!url.isEmpty()) {
                        Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                        startActivity(browserIntent);
                    }
                }
            });
            container.addView(item_view);
            return item_view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout)object);
        }
    }
}
