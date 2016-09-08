package com.example.suryansh.infobits;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by abhishekhsharma on 08/09/16.
 */
public class InfoBitsPageAdapter extends FragmentPagerAdapter {

    String [] result;
    Context context;
    String [] imageUrl;
    public InfoBitsPageAdapter(FragmentManager fm,String[] prgmNameList, String[] prgmImages) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return result.length;
    }
}
