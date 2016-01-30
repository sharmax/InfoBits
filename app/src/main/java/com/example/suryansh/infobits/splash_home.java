package com.example.suryansh.infobits;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Suryansh on 1/26/2016.
 */
public class splash_home extends Login{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_home);
        String Username = getIntent().getStringExtra("UserName");
        TextView tx_user2;
        TextView tx_pass;

        tx_user2 = (TextView)findViewById(R.id.tx_user2);
        tx_user2.setText(Username);


    }
}
