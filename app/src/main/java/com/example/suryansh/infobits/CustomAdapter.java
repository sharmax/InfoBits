package com.example.suryansh.infobits;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CustomAdapter extends FragmentPagerAdapter{

    String [] result;
    Context context;
    String [] imageUrl;
    File dir;
    LayoutInflater inflater;

    public CustomAdapter(FragmentManager fm, Context c, String[] prgmNameList, String[] prgmImages) {
        super(fm);
        // TODO Auto-generated constructor stub
        result = prgmNameList;
        context = c;
        imageUrl = prgmImages;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        super.instantiateItem(container, position);
        View rowView;
        dir = context.getFilesDir();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.infobits_customcell, container, false);
        ((TextView) rowView.findViewById(R.id.bookTitle)).setText(result[position]);
        File image = new File(dir, imageUrl[position]);
        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(image);
            ((ImageView) rowView.findViewById(R.id.bookImage)).setImageBitmap(BitmapFactory.decodeStream(fileInput));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        container.addView(rowView);
        return rowView;
    }
}