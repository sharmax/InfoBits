package com.example.suryansh.infobits;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SowmyaY on 26/04/16.
 */
public class MyFragment extends Fragment implements View.OnClickListener{

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static String [] bookNameList;
    public static String [] bookImages;
    public static String [] journalNameList;
    public static String [] journalImages;
    public static String [] bookLinks;
    public static String [] journalLinks;

    public static final MyFragment newInstance(String message)
    {
        MyFragment f = new MyFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String message = getArguments().getString(EXTRA_MESSAGE);
        View v = inflater.inflate(R.layout.custom_fragmet, container, false);
        GridView newArrivals = (GridView) v.findViewById(R.id.newArrivalsGrid);
        GridView journals = (GridView) v.findViewById(R.id.journalsGrid);

        newArrivals.setAdapter(new CustomAdapter(this.getContext(), bookNameList,bookImages));
        journals.setAdapter(new CustomAdapter(this.getContext(), bookNameList,bookImages));

        newArrivals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Uri uri = Uri.parse(bookLinks[position]); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        journals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Uri uri = Uri.parse(journalLinks[position]); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        return v;
    }

    public void setList(String[]journalList, String[]bookList, String[]bookImages, String[]journalImages, String[]bookLinks, String[]journalLinks){
        this.bookNameList = bookList;
        this.bookImages = bookImages;
        this.journalImages = journalImages;
        this.journalNameList = journalList;
        this.bookLinks = bookLinks;
        this.journalLinks = journalLinks;
    }

    @Override
    public void onClick(View v) {



    }
}
