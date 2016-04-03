package com.example.suryansh.infobits;

import android.os.Bundle;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;
import android.net.Uri;
import android.content.Intent;
import java.util.List;
import java.util.ArrayList;
import android.text.Spanned;
import java.util.zip.Inflater;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class downloadable_links extends homepage implements  AdapterView.OnItemClickListener{

    ListView listView ;
     Spanned text;
    private String[] values;
    private String[] links;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloadable_links);

        listView = (ListView) findViewById(R.id.listView);
        text =  Html.fromHtml("<a href=\"http://www.google.com\">CLICK HERE</a>");
       values = new String[] {String.valueOf(text), "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        links = new String[] {"http://www.intechopen.com/books", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
               "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        List<CharSequence> styledItems = new ArrayList<CharSequence>();

        for (String article : values) {
            styledItems.add(Html.fromHtml("<a href=\"http://www.google.com\">CLICK HERE</a>"));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i<values.length){
            Uri uri = Uri.parse(links[i]);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(links[i]));
            startActivity(intent);
        }
    }

}
