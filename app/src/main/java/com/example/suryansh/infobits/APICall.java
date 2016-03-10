package com.example.suryansh.infobits;

import android.os.AsyncTask;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Abhishek on 2/28/2016.
 */
public class APICall extends AsyncTask {
    private CallbackListener<String> mListener;
    public APICall(CallbackListener<String> listener) {
        mListener = listener;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        String urlString= (String) params[0];
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        JSONObject json;

        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            json = new JSONObject(responseStrBuilder.toString());
        } catch (Exception e ) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return json;
    }

    @Override
    protected void onPostExecute(Object result) {
        if (mListener != null) mListener.onComputingFinished((String) result);
    }
}
