package com.example.suryansh.infobits.Responses;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by SowmyaY on 28/08/16.
 */
public class BulletinResponse {

    public Subject CHEMICAL;
    public Subject CIVIL;
    public Subject EEE;
    public Subject CS;
    public Subject MECH;
    public Subject PHARMA;
    public Subject BIO;
    public Subject CHEM;
    public Subject ECO;
    public Subject MATH;
    public Subject PHY;
    public Subject HUM;
    public Subject MAN;

    public String[] tabTitles = {"CHEMICAL", "CIVIL", "EEE", "CS", "MECH", "PHARMA", "BIO", "CHEM", "ECO", "MATHS", "PHY", "HUM", "MAN"};

    private String json;

    public BulletinResponse(String json){
        this.json = json;
    }

    public void parseJSON(){
        JSONObject jsonObject;


        try {
            jsonObject = new JSONObject(json);
            System.out.println("arr: " + jsonObject.keys());
            Iterator<String> iter = jsonObject.keys();

            for (int i = 0; i < tabTitles.length; i++) {
                Subject subject = new Subject(jsonObject.getString(tabTitles[i]));

                subject.parseJSON();

                switch (tabTitles[i]){
                    case "CHEMICAL":
                        CHEMICAL = subject;
                        break;
                    case "CIVIL":
                        CIVIL = subject;
                        break;
                    case "EEE":
                        EEE = subject;
                        break;
                    case "CS":
                        CS = subject;
                        break;
                    case "MECH":
                        MECH = subject;
                        break;
                    case "PHARMA":
                        PHARMA = subject;
                        break;
                    case "BIO":
                        BIO = subject;
                        break;
                    case "CHEM":
                        CHEM = subject;
                        break;
                    case "ECO":
                        ECO = subject;
                        break;
                    case "MATHS":
                        MATH = subject;
                        break;
                    case "PHY":
                        PHY = subject;
                        break;
                    case "HUM":
                        HUM = subject;
                        break;
                    case "MAN":
                        MAN = subject;
                        break;

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

