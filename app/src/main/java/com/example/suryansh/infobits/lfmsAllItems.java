package com.example.suryansh.infobits;

import android.os.Bundle;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class lfmsAllItems extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> ItemsList;

    // url to get all items list
    private static String url_all_items = "http://172.21.1.31:8080/android_connect/get_all_items.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ITEMS = "items";
    private static final String TAG_ID = "sno";
    private static final String TAG_NAME = "particulars";

    // itemss JSONArray
    JSONArray items = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lfms_all_items);

        // Hashmap for ListView
        ItemsList = new ArrayList<HashMap<String, String>>();
        new LoadAllItems().execute();
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String Item_id = ((TextView) view.findViewById(R.id.sno)).getText().toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(), Claim.class);
                // sending pid to next activity
                in.putExtra(TAG_ID, Item_id);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    class LoadAllItems extends AsyncTask<String, String, String> {

        JSONObject json;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(lfmsAllItems.this);
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            // Building Parameters
            List<Map> params = new ArrayList<Map>();
            // getting JSON string from URL
//            JSONObject json = jParser.makeHttpRequest(url_all_items, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All items: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    items = json.getJSONArray(TAG_ITEMS);

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_ID, id);
                        map.put(TAG_NAME, name);

                        // adding HashList to ArrayList
                        ItemsList.add(map);
                    }
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {

            if(pDialog != null)
                pDialog.dismiss();

            runOnUiThread(new Runnable() {
                public void run() {

                    ListAdapter adapter = new SimpleAdapter(
                            lfmsAllItems.this, ItemsList,
                            R.layout.list_item, new String[] { TAG_ID,
                            TAG_NAME},
                            new int[] { R.id.sno, R.id.name });
                    // updating listview
                    setListAdapter(adapter);
                }
            });
        }
    }
}
