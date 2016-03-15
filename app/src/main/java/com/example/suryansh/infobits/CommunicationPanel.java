package com.example.suryansh.infobits;

import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class CommunicationPanel extends homepage /*implements CallbackListener<String>*/ {

    DrawerLayout drawerlayout;
    NavigationView navigationView;
    MenuItem cat;
    DBHandler dbhandler;
    JSONObject internal = new JSONObject();
    String urlString;
    ProgressBar spinner;
    int[] comms = {R.id.comm1, R.id.comm2, R.id.comm3, R.id.comm4};
    String[] cats = {"breco","ill","ao","grieve","breview","feedback"};
    int convform;
    public final static String actString = "communication_panel";
    Integer start = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_panel);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerlayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerlayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerlayout.setDrawerListener(actionBarDrawerToggle);
        dbhandler = new DBHandler(this,null,null);
        internal = dbhandler.selectData(0,"status like '%open%' and cat = '" + cats[0] + "' ORDER BY id ASC");
        ((TextView) findViewById(comms[0])).setText(internal.toString());
        String id = "1";
        if(internal.keys().hasNext()){
            id = internal.keys().next();
        }
        urlString = apiURL + actString + ".php?username=" + username + "&password=" + password + "&action=update&id=" + id + "&cat=6";
        ((TextView) findViewById(comms[1])).setText(urlString);
        new APICall().execute(urlString);
        setComm(start);
        // Delete data after 2 months
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

    public void setComm(int start){
        JSONObject dispjson = dbhandler.selectData(0, "status not like '%inactive%' and cat = '" + cat + "' ORDER BY status desc, id desc LIMIT " + start + ",4");
        Iterator<String> iter = dispjson.keys();
        TextView comm;
        try {
            spinner.setVisibility(View.GONE);
            for (int i = 0; iter.hasNext(); i++) {
                String key = iter.next();
                comm = (TextView) findViewById(comms[i]);
                String text = dispjson.get("topic").toString() + "<br />" + dispjson.get("date").toString() + " | " + dispjson.get("time").toString() + "<br />" + dispjson.get("admins").toString();
                comm.setText(text);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void conversation(){

    }

    public void updateInternalData(JSONObject json){
        try {
            if(json.has("err_message") && json.get("err_message") != ""){
                TextView error = (TextView) findViewById(R.id.error);
                spinner.setVisibility(View.GONE);
                error.setText(json.get("err_message").toString());
            }
            else{
                JSONObject data = (JSONObject) json.get("data");
                Iterator<String> iter = data.keys();
                Iterator<String> intiter = internal.keys();
                int x = 0;
                String intkey = "";
                JSONObject intval =  new JSONObject();
                while (iter.hasNext()){
                    String key = iter.next();
                    JSONObject dataval = (JSONObject) data.get(key);

                    if(intiter.hasNext()){
                        if(x == 0) {
                            intkey = intiter.next();
                            intval = (JSONObject) internal.get(intkey);
                            x = 1;
                        }
                        if(data.get(key) == internal.get(intkey)) {
                            if (dataval.get("admins") != intval.get("admins")) {
                                intval.put("admins",dataval.get("admins"));
                            }
                            if (dataval.get("talk") != intval.get("talk")) {
                                intval.put("talk",dataval.get("talk"));
                            }
                            if (dataval.get("status") != intval.get("status")) {
                                intval.put("status",dataval.get("status"));
                            }
                            String[] updatevalues = {dataval.get("admins").toString(),dataval.get("talk").toString(),dataval.get("status").toString()};
                            dbhandler.updateData(0,updatevalues,(int) internal.get(intkey));
                            x = 0;
                        }
                    }
                    else{
                        JSONObject check = dbhandler.selectData(0, "id = " + key);
                        if(!check.has(key)) {
                            String[] addvalues = {key, dataval.get("bitsid").toString(), dataval.get("category").toString(), dataval.get("name").toString(), dataval.get("topic").toString(), dataval.get("date").toString(), dataval.get("time").toString(), dataval.get("admins").toString(), dataval.get("talk").toString(), data.get("cat").toString(), dataval.get("status").toString()};
                            dbhandler.addData(0, addvalues);
                            while (intiter.hasNext()) {
                                JSONObject intjson = new JSONObject(internal.get(intkey).toString());
                                if (intjson.get("status") == "inactive") {
                                    internal.remove(intkey);
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class APICall extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String[] params) {
            String urlString= params[0];
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;

            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);
            } catch (Exception e ) {
                System.out.println(e.getMessage());
                return e.getMessage();
            }
            return responseStrBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject json;
            ((TextView) findViewById(comms[2])).setText(result);
            try{
                json = new JSONObject(result);
                if(json.get("action") == "update"){
                    updateInternalData(json);
                }
                else if(json.get("action")=="delete"){

                }
                else if(json.get("action")=="reply"){

                }
            }catch (Exception e ) {
                System.out.println(e.getMessage());
            }
        }
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
