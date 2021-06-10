package com.psennoi.elron.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.nio.IntBuffer;

public class DatabaseAdapter {
    myDbHelper myHelper;

    public DatabaseAdapter(Context context){
        myHelper = new myDbHelper(context);
    }

    static class myDbHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "history_database"; // database name
        private static final String TABLE_NAME = "history_table"; // table name
        private static final int DATABASE_Version = 1; // database version

        private static final String ID = "_id"; // column 1, primary key
        private static final String LOCATION = "location"; // column 2
        private static final String DESTINATION = "destination"; // column 3

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LOCATION +
                " VARCHAR(50), " + DESTINATION + " VARCHAR(50));";
        // "CREATE TABLE limit_table (_id INTEGER PRIMARY KEY AUTOINCREMENT, imagesViewed INTEGER, imagesMax INTEGER);"
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        private Context context;

        public myDbHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL(CREATE_TABLE);
            } catch (Exception e){
                Log.e("DatabaseAdapter: ", String.valueOf(e));
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try{
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e){
                Log.e("DatabaseAdapter: ", String.valueOf(e));
            }
        }
    }

    public void insertData(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.LOCATION, 0);
        contentValues.put(myDbHelper.DESTINATION, 0);

        SQLiteDatabase db = myHelper.getReadableDatabase();
        db.insert(myDbHelper.TABLE_NAME, null, contentValues);
    }

    public boolean tableExists() {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        boolean exists = false;
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = 'history_table'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                exists = true;
            }
            cursor.close();
        }
        return exists;
    }

    public void addRoute(String location, String destination){
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.LOCATION, location);
        contentValues.put(myDbHelper.DESTINATION, destination);
        db.insert("history_table", null, contentValues);
    }
}
