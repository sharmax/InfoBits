package com.example.suryansh.infobits;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Abhishek on 3/10/2016.
 */
public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
    }

    public void userReg(View view){
        Intent i = new Intent(login.this, signup.class);
        startActivity(i);
    }

    public void userLogin(View view){

    }
}
