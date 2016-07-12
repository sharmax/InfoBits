package com.example.suryansh.infobits;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.suryansh.infobits.network.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class infoBitsBulletin extends homepage {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mPager;
    private MyPagerAdapter mAdapter;
    private ListView listView;
    ProgressBar spinner;
    private Map<String, Object> map;

    public String[] tabTitles = {"CHEMICAL", "CIVIL", "EEE", "CS", "MECH", "PHARMA", "BIO", "CHEM", "ECO", "MATH", "PHY", "HUM", "MAN"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infobitsbulletin);

        List<Fragment> fragments = getFragments();
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mPager = (ViewPager) findViewById(R.id.pager);
        listView = (ListView) findViewById(R.id.listView);
        mPager.setAdapter(mAdapter);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);

        String[] news = new String[]{"news1", "news2", "news3", "news4", "news5",};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, news);
        listView.setAdapter(adapter);

        try {

            File file = new File(this.getFilesDir() + "/bulletin.plist");

            if (!file.exists()) {
                if (isConnected()) {
                    spinner.setVisibility(View.VISIBLE);
                    serverCalls(file);
                } else {
                    Toast.makeText(getApplicationContext(), "Not Connected to BITS Intranet!", Toast.LENGTH_LONG).show();
                }

            } else {

                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                map = (Map) ois.readObject();

                ois.close();
                fis.close();

                System.out.println(map);
            }
        } catch (Exception e) {

        }
        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));


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

        for (int i =0; i<13; i++){
            MyFragment fragment = new MyFragment();

            Map<String, Object> bookMap = (Map<String, Object>) map.get(tabTitles[i]);

            Map<String, Object> books = (Map<String, Object>) map.get(bookMap.get("books"));
            Map<String, Object> journals = (Map<String, Object>) map.get(bookMap.get("journals"));

            String[] booksKeys = {"book1", "book2", "book3"};
            String[] journalKeys = {"journal1", "journal2", "journal3"};
            String[] bookNameList = new String[4];
            String[] bookImageList = new String[4];
            String[] journalNameList = new String[4];
            String[] journalImageList = new String[4];
            String[] journalLinks = new String[4];
            String[] bookLinks = new String[4];

            for(int j = 0; j < 4; j++){

                Map<String, Object> book = (Map<String, Object>) books.get(booksKeys[j]);
                Map<String, Object> journal = (Map<String, Object>) journals.get(journalKeys[j]);
                bookNameList[j] = (String) book.get("type");
                bookImageList[j] = (String) book.get("image");
                journalNameList[j] = (String) journal.get("type");
                journalImageList[j] = (String) journal.get("image");
                bookLinks[j] = (String) book.get("url");
                journalLinks[j] = (String)journal.get("url");
            }

            fragment.newInstance("Fragment").setList(journalNameList,bookNameList,bookImageList,journalImageList, bookLinks, journalLinks);
            fList.add(MyFragment.newInstance("Fragment "+i));
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

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void serverCalls(final File file){

        spinner.setVisibility(View.VISIBLE);
        // Instantiate the RequestQueue.
        final RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();

        String url = apiURL+"bulletin.php/" + "get?username="+username +"?password="+ password;
        // Request a string response from the provided URL.

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    spinner.setVisibility(View.GONE);

                    FileOutputStream fos=new FileOutputStream(file);
                    ObjectOutputStream oos=new ObjectOutputStream(fos);

                    Map<String, Object> data = (Map<String, Object>) response.getJSONObject("data");
                    map = data;
                    oos.writeObject(data);
                    oos.flush();
                    oos.close();
                    fos.close();


                    mPager.getAdapter().notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                spinner.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog

            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsonObjReq);
    }
}



