package com.example.suryansh.infobits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.suryansh.infobits.network.VolleySingleton;

public class CustomAdapter extends BaseAdapter{

    String [] result;
    Context context;
    String [] imageUrl;

    private ImageLoader mImageLoader;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context c, String[] prgmNameList, String[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=c;
        imageUrl=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        NetworkImageView image;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.infobits_customcell, null);
        holder.tv=(TextView) rowView.findViewById(R.id.bookTitle);
        holder.image=(NetworkImageView) rowView.findViewById(R.id.bookImage);

        holder.tv.setText(result[position]);
        final ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();
        //Image URL - This can point to any image file supported by Android

        mImageLoader.get(imageUrl[position], ImageLoader.getImageListener(holder.image,
                R.drawable.ebxii, android.R.drawable
                        .ic_dialog_alert));
        holder.image.setImageUrl(imageUrl[position], mImageLoader);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
            }
        });

        return rowView;
    }

}