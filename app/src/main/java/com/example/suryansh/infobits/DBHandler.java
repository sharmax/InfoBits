package com.example.suryansh.infobits;

/**
 * Created by Abhishek on 1/24/2016.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bitslib.db";
    private static final String[] TABLES = {"communications"};
    private static final String[][] COLUMNS = {{"topic","date","time","admins","talk","cat","status"}};
    private static final String[][] TYPES = {{"text","varchar(10)","varchar(10)","text","text","varchar(10)","varchar(10)"}};

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "";
        for (int i = 0; i < TABLES.length; i++){
            for(int j = 0; j < TABLES.length; j++){
                query += COLUMNS[i][j] + " " + TYPES[i][j];
                if(j != TABLES.length - 1){
                    query += ", ";
                }
            }
            query = "CREATE TABLE "+ TABLES[i] + "(id integer primary_key, " +
                    query + ");";
            db.execSQL(query);
            query = "";
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = 0; i < TABLES.length; i++) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLES[i]);
        }
        onCreate(db);
    }

    public void addData(int table,String[] data){
        ContentValues values = new ContentValues();
        for(int i = 0; i < TABLES.length; i++){
            values.put(COLUMNS[table][i], data[i]);
        }
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLES[table], null, values);
        db.close();
    }

    public void deleteData(int table, int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLES[table] + " WHERE " + id + " = '" + id + "';");
        db.close();
    }

    public JSONObject selectData(int table, String sql){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLES[table] + " WHERE " + sql;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        JSONObject json = new JSONObject();
        JSONObject data = new JSONObject();
        while(!c.isAfterLast()){
            for(int i = 0; i < TABLES.length; i++){
                try {
                    data.put(COLUMNS[table][i], c.getString(c.getColumnIndex(COLUMNS[table][i])));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try {
                json.put(c.getString(c.getColumnIndex("id")), data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            c.moveToNext();
            data = new JSONObject();
        }
        db.close();
        return json;
    }
}
