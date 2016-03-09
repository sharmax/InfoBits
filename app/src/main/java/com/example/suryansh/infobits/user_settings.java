package com.example.suryansh.infobits;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * Created by SowmyaY on 24/02/16.
 */
public class user_settings extends homepage implements View.OnClickListener {
    private static final int RESULT_LOAD_IMAGE =1;

    EditText mobile;
    LinearLayout oldPassword;
    LinearLayout newPassword;
    LinearLayout confirmPassword;
    Button uploadBtn;
    Button updateMobile;
    Button updatePassword;
    TextView changePassword;
    ImageButton imageButton;
    ImageView image;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        image = (ImageView) findViewById(R.id.profile);
        mobile = (EditText) findViewById(R.id.mobileText);
        changePassword = (TextView) findViewById(R.id.changePassword);
        oldPassword = (LinearLayout) findViewById(R.id.oldPassLayout);
        newPassword = (LinearLayout) findViewById(R.id.newPassLayout);
        confirmPassword = (LinearLayout) findViewById(R.id.confirmPassLayout);
        uploadBtn = (Button) findViewById(R.id.upload);
        updateMobile = (Button) findViewById(R.id.update1);
        updatePassword = (Button) findViewById(R.id.update2);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        uploadBtn.setOnClickListener(this);
        updateMobile.setOnClickListener(this);
        updatePassword.setOnClickListener(this);
        imageButton.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fab:
                if (uploadBtn.getVisibility() == Button.VISIBLE) {
                    imageButton.setVisibility(Button.INVISIBLE);
                    uploadBtn.setVisibility(Button.INVISIBLE);
                    updateMobile.setVisibility(Button.INVISIBLE);
                    updatePassword.setVisibility(Button.INVISIBLE);
                    oldPassword.setVisibility(LinearLayout.INVISIBLE);
                    newPassword.setVisibility(LinearLayout.INVISIBLE);
                    confirmPassword.setVisibility(LinearLayout.INVISIBLE);
                    changePassword.setVisibility(TextView.INVISIBLE);

                    mobile.setEnabled(false);
                    mobile.setInputType(InputType.TYPE_NULL);
                    mobile.setFocusableInTouchMode(false);
                    mobile.clearFocus();
                } else {
                    imageButton.setVisibility(Button.VISIBLE);
                    uploadBtn.setVisibility(Button.VISIBLE);
                    updateMobile.setVisibility(Button.VISIBLE);
                    updatePassword.setVisibility(Button.VISIBLE);
                    oldPassword.setVisibility(LinearLayout.VISIBLE);
                    newPassword.setVisibility(LinearLayout.VISIBLE);
                    confirmPassword.setVisibility(LinearLayout.VISIBLE);
                    changePassword.setVisibility(TextView.VISIBLE);

                    mobile.setEnabled(true);
                    mobile.setInputType(InputType.TYPE_CLASS_NUMBER);
                    mobile.setFocusableInTouchMode(true);
                    mobile.requestFocus();
                }

                break;

            case R.id.update1:
                Snackbar.make(v, "Mobile number update Server call", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;

            case R.id.update2:
                Snackbar.make(v, "Password update Server call", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;

            case R.id.upload:
                Snackbar.make(v, "Upload Image Server call", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.imageButton:
                Snackbar.make(v, "Open Gallery to choose", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE );
                break;
            default:
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!= null){
            Uri selectedImage = data.getData();
            image.setImageURI(selectedImage);

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "user_settings Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
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
                "user_settings Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.suryansh.infobits/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

}

