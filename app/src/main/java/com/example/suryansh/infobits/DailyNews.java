package com.example.suryansh.infobits;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class DailyNews extends homepage{

    ListView newscast;
    DBHandler dbhandler;
    TextView msg, smsg;
    ProgressBar spinner;
    JSONObject internal;
    String urlString = "", message = "", search_message = "", error = "";
    ArrayList<Item> news = new ArrayList<Item>();
    public ArrayList<String> urls = new ArrayList<String>();
    public final static String actString = "daily_news";
    Dialog dialog;
    FloatingActionButton search, refresh, close;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_news);
        dialog = new Dialog(DailyNews.this);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        newscast = (ListView) findViewById(R.id.newsList);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        msg = (TextView) findViewById(R.id.message);
        smsg = (TextView) findViewById(R.id.search_message);
        setSupportActionBar(toolbar);
        search = (FloatingActionButton) findViewById(R.id.search);
        refresh = (FloatingActionButton) findViewById(R.id.refresh);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.newssearch);
                dialog.setTitle("Search News ...");
                dialog.show();
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNews(true);
            }
        });
        newscast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!urls.get(position).isEmpty()) {
                    if (!urls.get(position).equals("header")) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls.get(position)));
                        startActivity(browserIntent);
                    }
                } else {
                    Toast.makeText(DailyNews.this, "No Link Available!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dbhandler = new DBHandler(this,null,null);
        setNews(true);
    }

    public void setNews(boolean update){
        newscast.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        smsg.setVisibility(View.GONE);
        msg.setVisibility(View.GONE);
        if(update && isConnected()) {
            urlString = apiURL + actString + ".php?username=" + username + "&password=" + password + "&action=update";
            new APICall().execute(urlString);
        }
        else if(update){
            Toast.makeText(DailyNews.this,"Not Connected to BITS Intranet!",Toast.LENGTH_LONG).show();
        }
        Date today = new Date();
        news.clear();
        urls.clear();
        getNews("(JULIANDAY('" + df.format(today) + "') - JULIANDAY(date)) <= 7 ORDER BY date DESC");
        if(msg.getVisibility() == View.GONE) {
            MyAdapter adapter = new MyAdapter(this, news);
            spinner.setVisibility(View.GONE);
            newscast.setVisibility(View.VISIBLE);
            newscast.setAdapter(adapter);
        }
    }

    public void getNews(String sql){
        internal = dbhandler.selectData(1,sql);
        Iterator iter = internal.keys();
        String pH = "";
        int i = 0;
        while(iter.hasNext()){
            String key = iter.next().toString();
            try {
                JSONObject dataval = (JSONObject) internal.get(key);
                Date headerDate;
                SimpleDateFormat ndf = new SimpleDateFormat("EEE, MMM dd yyyy");
                try {
                    headerDate = df.parse(dataval.get("date").toString());
                    String nDS  = ndf.format(headerDate);
                    if(!pH.equals(nDS)) {
                        news.add(i, new SectionItem(nDS));
                        pH = nDS;
                        urls.add(i, "header");
                        i++;
                    }
                    news.add(i, new EntryItem(dataval.get("title").toString(), dataval.get("newspaper").toString() + ", pg. " + dataval.get("pages").toString()));
                    urls.add(i, dataval.get("url").toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            i++;
        }
        if(i == 0 && !message.isEmpty()){
            msg.setText(message);
            msg.setVisibility(View.VISIBLE);
            message = "";
        }
        else if(i == 0){
            msg.setText("No Headlines Found");
            msg.setVisibility(View.VISIBLE);
        }
        if(!search_message.isEmpty()){
            smsg.setText(search_message);
            smsg.setVisibility(View.VISIBLE);
            search_message = "";
        }
    }

    public String getStringDate(DatePicker date){
        String s;
        if(date.getMonth() + 1 > 9){
            s = String.valueOf(date.getYear()) + "-" + String.valueOf(date.getMonth() + 1) + "-" + String.valueOf(date.getDayOfMonth());
        }
        else{
            s = String.valueOf(date.getYear()) + "-" + "0" + String.valueOf(date.getMonth() + 1) + "-" + String.valueOf(date.getDayOfMonth());
        }
        return s;
    }

    public void getSearchNews(View view){
        String start = getStringDate(((DatePicker) dialog.findViewById(R.id.startDatePicker)));
        String end = getStringDate(((DatePicker) dialog.findViewById(R.id.endDatePicker)));
        if(start.equals(end)){
            Toast.makeText(DailyNews.this,"Dates can't be same!",Toast.LENGTH_LONG).show();
        }
        else {
            String keyword = ((EditText) dialog.findViewById(R.id.keywords)).getText().toString() + " ";
            dialog.dismiss();
            search.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.VISIBLE);
            newscast.setVisibility(View.GONE);
            smsg.setVisibility(View.GONE);
            msg.setVisibility(View.GONE);
            spinner.setVisibility(View.VISIBLE);
            if (isConnected()) {
                urlString = apiURL + actString + ".php?username=" + username + "&password=" + password + "&action=search&startDate=" + start + "&endDate=" + end + "&keyword=" + keyword;
                new APICall().execute(urlString);
            } else {
                Toast.makeText(DailyNews.this, "Not Connected to BITS Intranet!", Toast.LENGTH_LONG).show();
            }
            String sql = "";
            if(!keyword.equals("")){
                int s = 0;
                int e = keyword.indexOf(" ", s);
                for(int i = 0; e >= 0; i++){
                    if(i > 0){
                        sql = sql + " OR ";
                    }
                    sql = sql + "keywords LIKE '%" + keyword.substring(s, e) + "%'";
                    s = e + 1;
                    e = keyword.indexOf(" ", s);
                }
            }
            if(!sql.isEmpty()){
                sql =  " and (" + sql + ")";
            }
            news.clear();
            urls.clear();
            getNews("(JULIANDAY(date) - JULIANDAY('" + start + "')) >= 0 and (JULIANDAY(date) - JULIANDAY('" + end + "')) <= 0" + sql + " ORDER BY date DESC");
        }
        if(msg.getVisibility() == View.GONE) {
            MyAdapter adapter = new MyAdapter(this, news);
            spinner.setVisibility(View.GONE);
            newscast.setVisibility(View.VISIBLE);
            newscast.setAdapter(adapter);
        }
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void updateInternalData(JSONObject json){
        try{
            if(json.has("err_message") && !json.get("err_message").toString().isEmpty()){
                spinner.setVisibility(View.GONE);
                error = json.get("err_message").toString();
                Toast.makeText(DailyNews.this, json.get("err_message").toString(), Toast.LENGTH_LONG).show();
            }
            if(json.has("message") && !json.get("message").toString().isEmpty()){
                message = json.get("message").toString();
            }
            if(json.has("search_message") && !json.get("search_message").toString().isEmpty()){
                search_message = json.get("search_message").toString();
            }
            if(error.isEmpty() && !json.get("data").toString().equals("[]")){
                JSONObject data = (JSONObject) json.get("data");
                Iterator<String> iter = data.keys();
                while(iter.hasNext()) {
                    String key = iter.next();
                    JSONObject dataval = (JSONObject) data.get(key);
                    JSONObject check = dbhandler.selectData(1, "id = " + key);
                    if (!check.has(key)) {
                        String[] addvalues = {key, dataval.get("news_type").toString(), dataval.get("title").toString(), dataval.get("url").toString(), dataval.get("date").toString(), dataval.get("added_by").toString(), dataval.get("newspaper").toString(), dataval.get("keywords").toString(), dataval.get("pages").toString()};
                        dbhandler.addData(1, addvalues);
                    }
                }
            }
            error = "";
        } catch (JSONException e) {
            Toast.makeText(DailyNews.this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private class APICall extends AsyncTask<String,Integer,String> {

        String err;
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
                err = "Network Error! Ensure you're connected to BITS Intranet";
            }
            return responseStrBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if(!result.isEmpty()) {
                try {
                    JSONObject json = new JSONObject(result);
                    updateInternalData(json);
                } catch (Exception e) {
                    Toast.makeText(DailyNews.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }else{
                if(!err.isEmpty()){
                    spinner.setVisibility(View.GONE);
                    Toast.makeText(DailyNews.this,err,Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public class MyAdapter extends ArrayAdapter<Item> {

        private ArrayList<Item> news;
        public MyAdapter(Context context, ArrayList<Item> items) {
            super(context,0, items);
            news = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            final Item i = news.get(position);
            if (i != null) {
                v = getLayoutInflater().inflate(R.layout.newslistitem, null);
                final TextView header = (TextView) v.findViewById(R.id.header);
                final TextView title = (TextView)v.findViewById(R.id.title);
                final TextView paper = (TextView)v.findViewById(R.id.paper);
                if(i.isSection()){
                    title.setVisibility(View.GONE);
                    paper.setVisibility(View.GONE);
                    header.setVisibility(View.VISIBLE);
                    SectionItem si = (SectionItem) i;
                    header.setText(si.getDate());
                }else{
                    title.setVisibility(View.VISIBLE);
                    paper.setVisibility(View.VISIBLE);
                    header.setVisibility(View.GONE);
                    EntryItem ei = (EntryItem) i;
                    title.setText(ei.title);
                    paper.setText(ei.paper);
                }
            }
            return v;
        }
    }

    interface Item {
        boolean isSection();
    }

    class SectionItem implements Item{
        private final String date;

        public SectionItem(String date) {
            this.date = date;
        }
        public String getDate(){
            return date;
        }
        @Override
        public boolean isSection() {
            return true;
        }
    }

    class EntryItem implements Item{
        public final String title;
        public final String paper;

        public EntryItem(String title, String paper) {
            this.title = title;
            this.paper = paper;
        }
        @Override
        public boolean isSection() {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if(dialog.isShowing()){
            dialog.dismiss();
        }
        else{
            super.onBackPressed();
        }
    }
}
