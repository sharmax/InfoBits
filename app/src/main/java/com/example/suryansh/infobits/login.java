package com.example.suryansh.infobits;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Abhishek on 3/10/2016.
 */
public class login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button bt_signin;
    private Button bt_forgot;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        username = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pass);
        bt_forgot = (Button) findViewById(R.id.bt_forgot);
        bt_signin = (Button) findViewById(R.id.bt_signin);


        setContentView(R.layout.user_login);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void OnClick_sign_in(View view) {


        //if username and password fields are not empty. then do this.
        String UserName =username.getText().toString();
        String Password =password.getText().toString();
        Context context = getApplicationContext();
        if(UserName.isEmpty() || Password.isEmpty()){
            Toast toast = Toast.makeText(context, "Please fill in all the fields.", Toast.LENGTH_LONG);
            toast.show();
        }else{
            /*BackgroundTask backgroundTask = new BackgroundTask();
            backgroundTask.execute(,login_name,login_pass);*/

        }



    }

    public void OnClick_forgot_pass(View view) {
        Intent i = new Intent(login.this, forgot_password.class);
        startActivity(i);
    }




    public void userReg(View view) {
        Intent i = new Intent(login.this, signup.class);
        startActivity(i);
    }

    public void userLogin(View view) {

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.suryansh.infobits/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.suryansh.infobits/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
