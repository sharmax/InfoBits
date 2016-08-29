package com.example.suryansh.infobits;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.suryansh.infobits.Responses.BulletinResponse;
import com.example.suryansh.infobits.Responses.NewsResponse;
import com.example.suryansh.infobits.network.VolleySingleton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class infoBitsBulletin extends homepage {

    private DBHandler dbhandler;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mPager;
    private MyPagerAdapter mAdapter;
    private ListView listView;
    ProgressBar spinner;
    JSONObject internal;
    TextView msg;
    List<String> updatevalues;
    String[] arr;

    public String[] tabTitles = {"CHEMICAL", "CIVIL", "EEE", "CS", "MECH", "PHARMA", "BIO", "CHEM", "ECO", "MATHS", "PHY", "HUM", "MAN"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infobitsbulletin);
        msg = (TextView) findViewById(R.id.textView14);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mPager = (ViewPager) findViewById(R.id.pager);
        listView = (ListView) findViewById(R.id.listView);
        JSONObject internal = new JSONObject();
        listView.setVisibility(View.INVISIBLE);
        dbhandler = new DBHandler(this, null, null);
        internal = dbhandler.selectData(5, "1 ORDER BY id ASC");
//        msg.append(internal.toString());
        try {
            if (internal.length() != 0){

                SharedPreferences sp = getSharedPreferences("bulletinMonth", Activity.MODE_PRIVATE);
                int bulletinYear = sp.getInt("bulletinYear", 0);
                int bulletinMonth = sp.getInt("bulletinMonth", 0);
                Calendar cal = Calendar.getInstance();
                java.util.Date date= new Date();
                cal.setTime(date);

                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                dailyNewsServerCall();
                if (bulletinYear <  year){
                    serverCalls();
                }else{
                    if (bulletinMonth <= month){
                       // serverCalls();
                    }else{

                    }
                }

            }else{
                if (isConnected()){
                    serverCalls();
                }else{
                    alertShow("Connect to intranet and try again", "Not Connected to Intranet");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            mPager.setVisibility(View.INVISIBLE);
            mTabLayout.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.INVISIBLE);

            alertShow("Connect to intranet and try again", "Not Connected to Intranet");

        }

    }

    public void alertShow(String message, String title) {
        //  AlertDialog.Builder alertDialog = new AlertDialog.Builder()
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                this);

        // Setting Dialog Title
        alertDialog2.setTitle(title);

        // Setting Dialog Message
        alertDialog2.setMessage(message);

        // Setting Icon to Dialog
        alertDialog2.setIcon(R.drawable.delete);

        // Setting Negative "Yes" Btn
        alertDialog2.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        finish();
                        Toast.makeText(getApplicationContext(),
                                "Closed Bulletin", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        // Showing Alert Dialog
        alertDialog2.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();

        try {


            for (int i = 0; i < 13; i++) {
                MyFragment fragment = new MyFragment();
//                internal = dbhandler.getData(5);
                internal = dbhandler.selectData(5,"1 ORDER BY id ASC");
//                msg.append(internal.toString());
                Iterator intiter = internal.keys();
                String[] bookNameList = new String[4];
                String[] bookImageList = new String[4];
                String[] journalNameList = new String[4];
                String[] journalImageList = new String[4];
                String[] journalLinks = new String[4];
                String[] bookLinks = new String[4];
                Integer[] count = {0, 0, 0, 0, 0, 0};
                while (intiter.hasNext()){
                    JSONObject data = (JSONObject) internal.get(intiter.next().toString());
                    Iterator iter = data.keys();
                    while(iter.hasNext()){
                        String key = iter.next().toString();
                        switch(key.substring(0, key.length() - 1)){
                            case "pic":
//                                msg.append(String.valueOf(count[0]) + " " + data.get(key).toString());
                                bookImageList[count[0]] = data.get(key).toString();
                                count[0]++;
                                break;
                            case "url":
                                bookLinks[count[1]] = data.get(key).toString();
                                count[1]++;
                                break;
                            case "type":
                                bookNameList[count[2]] = data.get(key).toString();
                                count[2]++;
                                break;
                            case "jpic":
                                journalImageList[count[3]] = data.get(key).toString();
                                count[3]++;
                                break;
                            case "jurl":
                                journalLinks[count[4]] = data.get(key).toString();
                                count[4]++;
                                break;
                            case "jtype":
                                journalNameList[count[5]] = data.get(key).toString();
                                count[5]++;
                                break;
                        }

                    }
                }
//                JSONObject books = internal.getJSONObject("books");
//                JSONObject journals = internal.getJSONObject("journals");
//                String[] booksKeys = {"book1", "book2", "book3, book4"};
//                String[] journalKeys = {"journal1", "journal2", "journal3, journal4"};

//                for (int j = 0; j < booksKeys.length; j++) {
//                    try {
//                        JSONObject book = books.getJSONObject(booksKeys[j]);
//                        String pic = book.getString("pic");
//                        String name = book.getString("type");
//                        String url = book.getString("url");
//
//                        bookNameList[j] = name;
//                        bookImageList[j] = pic;
//                        bookLinks[j] = url;
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    JSONObject journal = books.getJSONObject(journalKeys[j]);
//                    String pic = journal.getString("pic");
//                    String name = journal.getString("type");
//                    String url = journal.getString("url");
//
//                    journalNameList[j] = name;
//                    journalImageList[j] = pic;
//                    journalLinks[j] = url;
//
//                }
//                msg.append(journalNameList.toString());
//                msg.append(bookNameList.toString());
//                msg.append(bookImageList.toString());
//                msg.append(journalImageList.toString());
//                msg.append(bookLinks.toString());
//                msg.append(journalLinks.toString());
                fragment.newInstance("Fragment").setList(journalNameList, bookNameList, bookImageList, journalImageList, bookLinks, journalLinks);
                fList.add(MyFragment.newInstance("Fragment " + i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fList;
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragments;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
            //return 13;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tabTitles[position];
        }
    }

    public void serverCalls() {

        //  spinner.setVisibility(View.VISIBLE);
        // Instantiate the RequestQueue.
        final RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();

        String url = apiURL + "bulletin.php?" + "username=" + username + "&password=" + password;
        // Request a string response from the provided URL.

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                SharedPreferences sp = getSharedPreferences("bulletinMonth", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                java.util.Date date= new Date();

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                editor.putInt("bulletinMonth", month);
                editor.putInt("bulletinYear", year);
                editor.commit();

                try {

                    if(response.has("err_message") && !response.get("err_message").toString().isEmpty()){
                        String error = response.get("err_message").toString();
                        Toast.makeText(getApplicationContext(),response.get("err_message").toString(),Toast.LENGTH_LONG).show();
                    }else{
                        JSONObject data = response.getJSONObject("data");
//                        msg.append(data.toString());
                        BulletinResponse bulletinResponse = new BulletinResponse(data.toString());
                        bulletinResponse.parseJSON();
                        saveToDB(bulletinResponse);
                        List<Fragment> fragments = getFragments();
                        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
                        mPager.setAdapter(mAdapter);
                        mTabLayout.setTabsFromPagerAdapter(mAdapter);
                        mTabLayout.setupWithViewPager(mPager);
                        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
                        Toast.makeText(getApplicationContext(), "Response: " + data, Toast.LENGTH_SHORT).show();
                    }

                   // adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();

                    //alertShow("Connect to intranet and try again", "Not Connected to Intranet");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //  spinner.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

                List<Fragment> fragments = getFragments();
                mAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
                mPager.setAdapter(mAdapter);
                mTabLayout.setTabsFromPagerAdapter(mAdapter);
                mTabLayout.setupWithViewPager(mPager);
                mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
               // System.out.println(map);

            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsonObjReq);
    }

    public void saveToDB(BulletinResponse bulletinResponse){

//        dbhandler.checkTable(5);
//        msg.append(bulletinResponse.toString());
        for (int i = 0; i < tabTitles.length; i++) {
            arr = new String[]{};
            switch(i){
                case 0:
                    com.example.suryansh.infobits.Responses.Subject CHEMICAL = bulletinResponse.CHEMICAL;
                    arr = new String[]{CHEMICAL.book1.pic, CHEMICAL.book2.pic, CHEMICAL.book3.pic, CHEMICAL.book4.pic,
                            CHEMICAL.book1.type, CHEMICAL.book2.type, CHEMICAL.book3.type, CHEMICAL.book4.type,
                            CHEMICAL.book1.url, CHEMICAL.book2.url, CHEMICAL.book3.url, CHEMICAL.book4.url,
                            CHEMICAL.journal1.pic, CHEMICAL.journal2.pic, CHEMICAL.journal3.pic, CHEMICAL.journal4.pic,
                            CHEMICAL.journal1.type, CHEMICAL.journal2.type, CHEMICAL.journal3.type, CHEMICAL.journal4.type,
                            CHEMICAL.journal1.url, CHEMICAL.journal2.url, CHEMICAL.journal3.url, CHEMICAL.journal4.url};
                    break;
                case 1:
                    com.example.suryansh.infobits.Responses.Subject CIVIL = bulletinResponse.CIVIL;
                    msg.append(String.valueOf(i));
                    arr = new String[]{
                            CIVIL.book1.pic, CIVIL.book2.pic, CIVIL.book3.pic, CIVIL.book4.pic,
                            CIVIL.book1.type, CIVIL.book2.type, CIVIL.book3.type, CIVIL.book4.type,
                            CIVIL.book1.url, CIVIL.book2.url, CIVIL.book3.url, CIVIL.book4.url,
                            CIVIL.journal1.pic, CIVIL.journal2.pic, CIVIL.journal3.pic, CIVIL.journal4.pic,
                            CIVIL.journal1.type, CIVIL.journal2.type, CIVIL.journal3.type, CIVIL.journal4.type,
                            CIVIL.journal1.url, CIVIL.journal2.url, CIVIL.journal3.url, CIVIL.journal4.url
                    };
                    break;
                case 2:
                    com.example.suryansh.infobits.Responses.Subject EEE = bulletinResponse.EEE;
                    arr = new String[]{EEE.book1.pic, EEE.book2.pic, EEE.book3.pic, EEE.book4.pic,
                            EEE.book1.type, EEE.book2.type, EEE.book3.type, EEE.book4.type,
                            EEE.book1.url, EEE.book2.url, EEE.book3.url, EEE.book4.url,
                            EEE.journal1.pic, EEE.journal2.pic, EEE.journal3.pic, EEE.journal4.pic,
                            EEE.journal1.type, EEE.journal2.type, EEE.journal3.type, EEE.journal4.type,
                            EEE.journal1.url, EEE.journal2.url, EEE.journal3.url, EEE.journal4.url};
                    break;
                case 3:
                    com.example.suryansh.infobits.Responses.Subject CS = bulletinResponse.EEE;
                    arr = new String[]{CS.book1.pic, CS.book2.pic, CS.book3.pic, CS.book4.pic,
                            CS.book1.type, CS.book2.type, CS.book3.type, CS.book4.type,
                            CS.book1.url, CS.book2.url, CS.book3.url, CS.book4.url,
                            CS.journal1.pic, CS.journal2.pic, CS.journal3.pic, CS.journal4.pic,
                            CS.journal1.type, CS.journal2.type, CS.journal3.type, CS.journal4.type,
                            CS.journal1.url, CS.journal2.url, CS.journal3.url, CS.journal4.url};
                    break;
                case 4:
                    com.example.suryansh.infobits.Responses.Subject MECH = bulletinResponse.MECH;
                    arr = new String[]{MECH.book1.pic, MECH.book2.pic, MECH.book3.pic, MECH.book4.pic,
                            MECH.book1.type, MECH.book2.type, MECH.book3.type, MECH.book4.type,
                            MECH.book1.url, MECH.book2.url, MECH.book3.url, MECH.book4.url,
                            MECH.journal1.pic, MECH.journal2.pic, MECH.journal3.pic, MECH.journal4.pic,
                            MECH.journal1.type, MECH.journal2.type, MECH.journal3.type, MECH.journal4.type,
                            MECH.journal1.url, MECH.journal2.url, MECH.journal3.url, MECH.journal4.url};
                    break;
                case 5:
                    com.example.suryansh.infobits.Responses.Subject PHARMA = bulletinResponse.PHARMA;
                    arr = new String[]{PHARMA.book1.pic, PHARMA.book2.pic, PHARMA.book3.pic, PHARMA.book4.pic,
                            PHARMA.book1.type, PHARMA.book2.type, PHARMA.book3.type, PHARMA.book4.type,
                            PHARMA.book1.url, PHARMA.book2.url, PHARMA.book3.url, PHARMA.book4.url,
                            PHARMA.journal1.pic, PHARMA.journal2.pic, PHARMA.journal3.pic, PHARMA.journal4.pic,
                            PHARMA.journal1.type, PHARMA.journal2.type, PHARMA.journal3.type, PHARMA.journal4.type,
                            PHARMA.journal1.url, PHARMA.journal2.url, PHARMA.journal3.url, PHARMA.journal4.url};
                    break;
                case 6:
                    com.example.suryansh.infobits.Responses.Subject BIO = bulletinResponse.BIO;
                    arr = new String[]{String.valueOf(i), BIO.book1.pic, BIO.book2.pic, BIO.book3.pic, BIO.book4.pic,
                            BIO.book1.type, BIO.book2.type, BIO.book3.type, BIO.book4.type,
                            BIO.book1.url, BIO.book2.url, BIO.book3.url, BIO.book4.url,
                            BIO.journal1.pic, BIO.journal2.pic, BIO.journal3.pic, BIO.journal4.pic,
                            BIO.journal1.type, BIO.journal2.type, BIO.journal3.type, BIO.journal4.type,
                            BIO.journal1.url, BIO.journal2.url, BIO.journal3.url, BIO.journal4.url};
                    break;
                case 7:
                    com.example.suryansh.infobits.Responses.Subject CHEM = bulletinResponse.CHEM;
                    arr = new String[]{CHEM.book1.pic, CHEM.book2.pic, CHEM.book3.pic, CHEM.book4.pic,
                            CHEM.book1.type, CHEM.book2.type, CHEM.book3.type, CHEM.book4.type,
                            CHEM.book1.url, CHEM.book2.url, CHEM.book3.url, CHEM.book4.url,
                            CHEM.journal1.pic, CHEM.journal2.pic, CHEM.journal3.pic, CHEM.journal4.pic,
                            CHEM.journal1.type, CHEM.journal2.type, CHEM.journal3.type, CHEM.journal4.type,
                            CHEM.journal1.url, CHEM.journal2.url, CHEM.journal3.url, CHEM.journal4.url};
                    break;
                case 8:
                    com.example.suryansh.infobits.Responses.Subject ECO = bulletinResponse.ECO;
                    arr = new String[]{ECO.book1.pic, ECO.book2.pic, ECO.book3.pic, ECO.book4.pic,
                            ECO.book1.type, ECO.book2.type, ECO.book3.type, ECO.book4.type,
                            ECO.book1.url, ECO.book2.url, ECO.book3.url, ECO.book4.url,
                            ECO.journal1.pic, ECO.journal2.pic, ECO.journal3.pic, ECO.journal4.pic,
                            ECO.journal1.type, ECO.journal2.type, ECO.journal3.type, ECO.journal4.type,
                            ECO.journal1.url, ECO.journal2.url, ECO.journal3.url, ECO.journal4.url};
                    break;
                case 9:
                    com.example.suryansh.infobits.Responses.Subject MATH = bulletinResponse.MATH;
                    arr = new String[]{MATH.book1.pic, MATH.book2.pic, MATH.book3.pic, MATH.book4.pic,
                            MATH.book1.type, MATH.book2.type, MATH.book3.type, MATH.book4.type,
                            MATH.book1.url, MATH.book2.url, MATH.book3.url, MATH.book4.url,
                            MATH.journal1.pic, MATH.journal2.pic, MATH.journal3.pic, MATH.journal4.pic,
                            MATH.journal1.type, MATH.journal2.type, MATH.journal3.type, MATH.journal4.type,
                            MATH.journal1.url, MATH.journal2.url, MATH.journal3.url, MATH.journal4.url};
                    break;
                case 10:
                    com.example.suryansh.infobits.Responses.Subject PHY = bulletinResponse.PHY;
                    arr = new String[]{PHY.book1.pic, PHY.book2.pic, PHY.book3.pic, PHY.book4.pic,
                            PHY.book1.type, PHY.book2.type, PHY.book3.type, PHY.book4.type,
                            PHY.book1.url, PHY.book2.url, PHY.book3.url, PHY.book4.url,
                            PHY.journal1.pic, PHY.journal2.pic, PHY.journal3.pic, PHY.journal4.pic,
                            PHY.journal1.type, PHY.journal2.type, PHY.journal3.type, PHY.journal4.type,
                            PHY.journal1.url, PHY.journal2.url, PHY.journal3.url, PHY.journal4.url};
                    break;
                case 11:
                    com.example.suryansh.infobits.Responses.Subject HUM = bulletinResponse.HUM;
                    arr = new String[]{HUM.book1.pic, HUM.book2.pic, HUM.book3.pic, HUM.book4.pic,
                            HUM.book1.type, HUM.book2.type, HUM.book3.type, HUM.book4.type,
                            HUM.book1.url, HUM.book2.url, HUM.book3.url, HUM.book4.url,
                            HUM.journal1.pic, HUM.journal2.pic, HUM.journal3.pic, HUM.journal4.pic,
                            HUM.journal1.type, HUM.journal2.type, HUM.journal3.type, HUM.journal4.type,
                            HUM.journal1.url, HUM.journal2.url, HUM.journal3.url, HUM.journal4.url};
                    break;
                case 12:
                    com.example.suryansh.infobits.Responses.Subject MAN = bulletinResponse.MAN;
                    arr = new String[]{MAN.book1.pic, MAN.book2.pic, MAN.book3.pic, MAN.book4.pic,
                            MAN.book1.type, MAN.book2.type, MAN.book3.type, MAN.book4.type,
                            MAN.book1.url, MAN.book2.url, MAN.book3.url, MAN.book4.url,
                            MAN.journal1.pic, MAN.journal2.pic, MAN.journal3.pic, MAN.journal4.pic,
                            MAN.journal1.type, MAN.journal2.type, MAN.journal3.type, MAN.journal4.type,
                            MAN.journal1.url, MAN.journal2.url, MAN.journal3.url, MAN.journal4.url};
                    break;
            }
            msg.append(arr[0]);
            updatevalues = new ArrayList<String>(Arrays.asList(arr));
            JSONObject check = dbhandler.selectData(1, "id = " + i);
            if (check.length() != 0){
                dbhandler.updateData(5, updatevalues.toArray(new String[0]), i);
            }
            else{
                updatevalues.add(0, String.valueOf(i));
                dbhandler.addData(5, updatevalues.toArray(new String[0]));
            }
        }
    }

    public void dailyNewsServerCall(){

        final RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();

        String url = apiURL + "daily_news.php?" + "username=" + username + "&password=" + password + "&action=update";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                url, null, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if(response.has("err_message") && !response.get("err_message").toString().isEmpty()){
                                String error = response.get("err_message").toString();
                                Toast.makeText(getApplicationContext(),response.get("err_message").toString(),Toast.LENGTH_LONG).show();
                            }else{
                                JSONObject data = response.getJSONObject("data");
                                NewsResponse newsResponse = new NewsResponse(data.toString());
                                newsResponse.parseJSON();

                                Toast.makeText(getApplicationContext(), "Response: " + data, Toast.LENGTH_SHORT).show();

                                final ArrayList<String> news = newsResponse.news;
                                final ArrayList<String> links = newsResponse.urls;
                                final ArrayList<String> newspapers = newsResponse.newsPaperAndDate;
                                listView.setVisibility(View.VISIBLE);

                                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, news) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getView(position, convertView, parent);
                                        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                                        text1.setTextColor(Color.BLACK);
                                        TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                                        text2.setTextColor(Color.DKGRAY);
                                        text1.setText(news.get(position).toString());
                                        text2.setText(newspapers.get(position).toString());
                                        return view;
                                    }
                                };
//                                ArrayAdapter<String> adapter =
//                                        new ArrayAdapter<String>(getApplicationContext(),
//                                                android.R.layout.simple_list_item_2,
//                                                news) {
//
//                                            @Override
//                                            public View getView(int position, View convertView, ViewGroup parent) {
//
//                                                View view = super.getView(position, convertView, parent);
//                                                TextView text = (TextView) view.findViewById(android.R.id.text1);
//                                                text.setTextColor(Color.BLACK);
//                                                return view;
//                                            }
//                                        };
                                listView.setAdapter(adapter);
                                listView.setClickable(true);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                        Uri uri = Uri.parse(links.get(position)); // missing 'http://' will cause crashed
                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                        startActivity(intent);
                                    }
                                });
                                Toast.makeText(getApplicationContext(), "Response is: "+ response, Toast.LENGTH_LONG).show();
                            }

                            // adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();

                            //alertShow("Connect to intranet and try again", "Not Connected to Intranet");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR: "+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjReq);
    }
}



