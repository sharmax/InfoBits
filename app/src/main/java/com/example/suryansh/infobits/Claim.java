package com.example.suryansh.infobits;

import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Claim extends homepage {

    EditText txtName;
    Button btnSave;
    String sno;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String url_item_detials = "http://172.21.1.31:8080/android_connect/get_item_details.php";
    private static final String url_update_item = "http://172.21.1.31:8080/android_connect/update_item.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ITEM = "item";
    private static final String TAG_ID = "sno";
    private static final String TAG_NAME = "particulars";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        btnSave = (Button) findViewById(R.id.btnSave);
        txtName = (EditText) findViewById(R.id.inputName);
        Intent i = getIntent();
        sno = i.getStringExtra(TAG_ID);
        new GetItemDetails().execute();
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new SaveItemDetails().execute();
            }
        });
    }

    class GetItemDetails extends AsyncTask<String, String, String> {

        JSONObject json;
        int success;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Claim.this);
            pDialog.setMessage("Loading Item details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... params) {
            try {
                List<Map> params1 = new ArrayList<Map>();
                Map kv = null;
                kv.put("sno", sno);
                params1.add(kv);
                //JSONObject json = jsonParser.makeHttpRequest(url_item_detials, "GET", params1);
                Log.d("Single Item Details", json.toString());
                success = json.getInt(TAG_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            if (success == 1) {
                try {
                    JSONArray itemObj = json.getJSONArray(TAG_ITEM); // JSON Array
                    JSONObject item = itemObj.getJSONObject(0);
                    txtName = (EditText) findViewById(R.id.inputName);
                    txtName.setText(item.getString(TAG_NAME));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            pDialog.dismiss();

        }
    }


    class SaveItemDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        JSONObject json;
        String name1;
        List<Map> params = new ArrayList<Map>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            name1 = txtName.getText().toString();
            Map kv = null;
            kv.put(TAG_ID, sno);
            kv.put(TAG_NAME, name1);
            params.add(kv);
            pDialog = new ProgressDialog(Claim.this);
            pDialog.setMessage("Saving product ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {
            // getting updated data from EditTexts
            //String name = args[0];
            //String price = args[1];
            //String description = args[2];
            // Building Parameters


            // sending modified data through http request
            // Notice that update product url accepts POST method
//            json = jsonParser.makeHttpRequest(url_update_item,"POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    // failed to update product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product updated
            pDialog.dismiss();
        }
    }
}
