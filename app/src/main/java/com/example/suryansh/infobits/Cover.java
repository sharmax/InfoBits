package com.example.suryansh.infobits;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class Cover extends AppCompatActivity {

    public final static String apiURL = "http://172.21.1.15/apis/";
    public final static String imageApiURL = "http://172.21.1.15/uploads/";
    String actString = "notices";
    int imgs;
    ProgressBar spinner;
    DBHandler dbhandler;
    JSONObject internal;
    File dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbhandler = new DBHandler(this,null,null);
        internal = dbhandler.selectData(2,"1 ORDER BY id ASC");
        setContentView(R.layout.activity_cover);
        dir = getFilesDir();
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        dbhandler = new DBHandler(this,null,null);
        internal = dbhandler.selectData(2,"1 ORDER BY id ASC");
        updateNotice();
    }

    private class APICall extends AsyncTask<String,Integer,String> {

        String err;
        @Override
        protected String doInBackground(String[] params) {
            String urlString= params[0];
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;

            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);
            } catch (Exception e ) {
                err = "Network Error! Ensure you're connected to BITS Intranet";
            }
            return responseStrBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if(!result.isEmpty()) {
                try {
                    JSONObject json = new JSONObject(result);
                    //msg.append(json.toString() + "\n");
                    updateImageData(json);
                } catch (Exception e) {
                    //Toast.makeText(homepage.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }else{
                if(!err.isEmpty()){
                    Toast.makeText(Cover.this, err, Toast.LENGTH_LONG).show();
                }
                launchHome();
            }
        }
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {

        Bitmap bitmap;
        String filename, link, key;
        Integer tot;
        protected Bitmap doInBackground(String[] args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            filename = args[0].substring(args[0].lastIndexOf("/") + 1);
            tot = Integer.parseInt(args[1].substring(0, args[1].indexOf("-")));
            key = args[1].substring(args[1].indexOf("-") + 1, args[1].indexOf("%"));
            link = args[1].substring(args[1].indexOf("%") + 1);
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            imgs++;
            if(image != null){
                String ext = filename.substring(filename.lastIndexOf("."));
                //filename = filename.substring(0, filename.lastIndexOf("."));
                File file = new File(dir, filename);
                FileOutputStream fileOut;
                try {
                    fileOut = new FileOutputStream(file);
                    //msg.append(ext);
                    if(ext.equals(".jpg") || ext.equals(".jpeg")){
                        image.compress(Bitmap.CompressFormat.JPEG, 50, fileOut);
                    }
                    else{
                        image.compress(Bitmap.CompressFormat.PNG, 50, fileOut);
                    }
                    fileOut.close();
                    //msg.append(filename + "\n");
                    updateImage(filename, link, key);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(imgs == tot){
                launchHome();
                imgs = 0;
            }
        }
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void updateImageData(JSONObject json){
        try {
            if(!json.get("data").toString().equals("[]")){
                JSONObject data = (JSONObject) json.get("data");
                Iterator<String> iter = data.keys();
                Iterator<String> intiter = internal.keys();
                String intkey;
                while(iter.hasNext()) {
                    String key = iter.next();
                    JSONObject dataval = (JSONObject) data.get(key);
                    if(intiter.hasNext()) {
                        intkey = intiter.next();
                        JSONObject intval = (JSONObject) internal.get(intkey);
                        if (!data.has(intkey)){
                            File file = new File(dir, intval.get("image").toString());
                            if(file.delete()) {
                                dbhandler.deleteData(2, Integer.parseInt(intkey));
                            }
                        }
                    }
                    new LoadImage().execute(imageApiURL + "notices/images/" + dataval.get("image").toString(), String.valueOf(data.length()) + "-" + key + "%" + dataval.get("link").toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateImage(String filename, String link, String key) throws UnsupportedEncodingException {
        File file = new File(dir, filename);
        if(file.exists()){
            String[] addvalues = {key, filename, URLEncoder.encode(link, "UTF-8")};
            dbhandler.addData(2, addvalues);
        }
    }

    public void updateNotice(){
        String urlString = apiURL + actString + ".php";
        spinner.setVisibility(View.VISIBLE);
        if(isConnected()) {
            new APICall().execute(urlString);
        }
        else{
            Toast.makeText(Cover.this,"Not Connected to BITS Intranet!",Toast.LENGTH_LONG).show();
            launchHome();
        }
    }

    public void launchHome(){
        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    Intent i = new Intent(Cover.this, homepage.class);
                    startActivity(i);
                    finish();
                }
            }, 3000
        );
    }
}
