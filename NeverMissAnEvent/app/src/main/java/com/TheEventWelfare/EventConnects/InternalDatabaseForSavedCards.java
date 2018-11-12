package com.TheEventWelfare.EventConnects;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class InternalDatabaseForSavedCards extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "savedwordshopinfo.db";
    public static final String TABLE_NAME = "savedworkshoptable";
    public static final String COLUMTAGS[] = {"ID","TYPE","TITLE","VENUE","PRICE","DATES","TIMING","DIRECTBOOKINGLINK","COMPANYNAME","CONTACT","EMAIL","WEBLINK","DESCRIPTION","USER_ID"};
    private Context context;


    public InternalDatabaseForSavedCards(Context context) {
        super(context,DATABASE_NAME,null,1);
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,TYPE TEXT,TITLE TEXT,VENUE TEXT,PRICE TEXT,DATES TEXT,TIMING TEXT,DIRECTBOOKINGLINK TEXT,COMPANYNAME TEXT,CONTACT TEXT,EMAIL TEXT,WEBLINK TEXT,DESCRIPTION TEXT,USER_ID TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }



    private String getCurrentUserId()
    {
        final String PREF_NAME = "com.data.workshop.event";

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("EMAIL_ID","");

    }

    public boolean insertdataintointernaldatabase(EventDetailsFromDatabase tabledatatobesaved)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMTAGS[1],tabledatatobesaved.getType());
        contentValues.put(COLUMTAGS[2],tabledatatobesaved.getTitle());
        contentValues.put(COLUMTAGS[3],tabledatatobesaved.getVenue());
        contentValues.put(COLUMTAGS[4],tabledatatobesaved.getPrice());
        contentValues.put(COLUMTAGS[5],tabledatatobesaved.getDates());
        contentValues.put(COLUMTAGS[6],tabledatatobesaved.getTiming());
        contentValues.put(COLUMTAGS[7],tabledatatobesaved.getDirectlink());
        contentValues.put(COLUMTAGS[8],tabledatatobesaved.getOrganizer());
        contentValues.put(COLUMTAGS[9],tabledatatobesaved.getContact());
        contentValues.put(COLUMTAGS[10],tabledatatobesaved.getEmailid());
        contentValues.put(COLUMTAGS[11],tabledatatobesaved.getWeblink());
        contentValues.put(COLUMTAGS[12],tabledatatobesaved.getDescription());
        contentValues.put(COLUMTAGS[13],getCurrentUserId());
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }


    public Cursor getalldata()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String userId = getCurrentUserId();

        Cursor resultforquery = db.rawQuery("select * from "+TABLE_NAME+" where "+COLUMTAGS[13]+" = '"+userId+"'",null);
        return resultforquery;

    }
    public boolean deletedata(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"ID = ?",new String[] {id});


        return (result!=0);
    }
}
