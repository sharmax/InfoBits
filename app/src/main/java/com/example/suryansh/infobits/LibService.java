package com.example.suryansh.infobits;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;





public class LibService extends homepage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_service);
    }

    public void onClickBulletin(View view) {
        Intent i = new Intent(LibService.this, infoBitsBulletin.class);
        startActivity(i);
    }

    public void onClickDailyNews(View view){
        Intent i = new Intent(LibService.this, DailyNews.class);
        startActivity(i);
    }

}
