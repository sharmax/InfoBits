package com.example.suryansh.infobits;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Suryansh on 2/10/2016.
 */
public class Swipe_adapter extends PagerAdapter {
    private int[] image_resources = {R.drawable.sample_1,R.drawable.sample_2,R.drawable.sample_3,R.drawable.sample_4,R.drawable.sample_5};
    private Context ctx;
    private LayoutInflater layoutinflator;

    public Swipe_adapter(Context ctx){
        this.ctx = ctx;
    }


    @Override
    public int getCount() {

        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutinflator =(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutinflator.inflate(R.layout.swipe_image,container,false);
        ImageView imageView = (ImageView)item_view.findViewById(R.id.image_view);
        TextView textView = (TextView)item_view.findViewById(R.id.image_count);
        imageView.setImageResource(image_resources[position]);
        textView.setText("Image: " + position);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
        super.destroyItem(container, position, object);
    }
}

