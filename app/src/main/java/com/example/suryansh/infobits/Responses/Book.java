package com.example.suryansh.infobits.Responses;

import org.json.JSONException;
import org.json.JSONObject;

public class Book{
    public static String pic;
    public static String url;
    public static String type;

    private String json;

    public Book(String json){
        this.json = json;
    }

    public void parseJSON(){
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
            pic = jsonObject.getString("pic");
            url = jsonObject.getString("url");
            type = jsonObject.getString("type");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
