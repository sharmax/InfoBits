package com.example.suryansh.infobits;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    TextView tx_user;
    TextView tx_pass;
    EditText ed_user;
    EditText ed_pass;
    Button bt_login;
    Button bt_forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void OnClickLogin(View view) {
        ed_user = (EditText) findViewById(R.id.ed_user);
        ed_pass = (EditText) findViewById(R.id.ed_pass);
        String username = ed_user.getText().toString();
        String password = ed_pass.getText().toString();

        if (view.getId() == R.id.bt_login) {

            Intent i = new Intent(Login.this, splash_home.class);
            i.putExtra("UserName", username);
            startActivity(i);
        }

    }

    public void OnClickForgot(View view){
        if (view.getId() == R.id.tx_forgotpass) {

            Intent i = new Intent(Login.this, forgot_pass.class);
            startActivity(i);
        }
    }

    public void OnClickHomepage(View view){
        if (view.getId() == R.id.bt_homepage) {

            Intent i = new Intent(Login.this, homepage.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
