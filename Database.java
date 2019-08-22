package com.example.kalala.exerciseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Carla Holcomb
 */

public class Database extends SQLiteOpenHelper {

    private static final String TAG = "Database";

    private static final String TABLE_NAME = "exercise_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "name";
    private static final String COL3 = "ActivityTypeGoal";
    private static final String COL4 = "ActivityDate";
    private static final String COL5 = "ActivityLengthGoal";
    private static final String COL6 = "ActivityType";
    private static final String COL7 = "ActivityLength";


    public Database(Context context) {
        super(context, TABLE_NAME, null, 16);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT," + COL3 + " INTEGER, " + COL4 + " TEXT, " + COL5 + " INTEGER, " + COL6 + " INTEGER, " + COL7 + " INTEGER) ";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item, int item2, String item3, int item4) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        contentValues.put(COL3, item2);
        contentValues.put(COL4, item3);
        contentValues.put(COL5, item4);
        contentValues.put(COL6, 10);
        contentValues.put(COL7, 10);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     *
     * @return
     */
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    /**
     * Returns only the Name that matches the ID passed in

     */
    public Cursor getName(int ID){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + COL2 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + ID + "'";

        Cursor value = db.rawQuery(query, null);
        return value;
    }



    /**
     * Returns only the ID that matches the date passed in

     */
    public Cursor getID(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL4 + " = '" + date + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }



    /**
     * Gets type of exercise goal from ID
     *
     */
    public Cursor getType(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + COL3 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + id + "'";

        Cursor value = db.rawQuery(query, null);
        return value;
    }

    /**
     * Gets exercise type per ID
     *
     */
    public Cursor getType2(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + COL6 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + id + "'";

        Cursor value = db.rawQuery(query, null);
        return value;
    }

    /**
     * Retrieves activity length goal per ID
     *
     */
    public Cursor getLength(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + COL5 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + id + "'";

        Cursor value = db.rawQuery(query, null);
        return value;
    }

    /**
     * Retrieves activity length per ID
     *
     */
    public Cursor getLength2(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + COL7 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + id + "'";

        Cursor value = db.rawQuery(query, null);
        return value;
    }



    /**
     * Checks if name and date combo already exist
     *
     */


    public Cursor checkDoubles(String Date){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + COL2 + " FROM " + TABLE_NAME +
                " WHERE " + COL4 + " = '" + Date + "'";

        Cursor value = db.rawQuery(query, null);
        return value;
    }

    /**
     * Updates the type field
     *
     */
    public void updateType(int ID, int type){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COL6 +
                " = '" + type + "' WHERE " + COL1 + " = '" + ID + "'";
        Log.d(TAG, "updateType: query: " + query);
        Log.d(TAG, "updateName: Setting type to " + type);
        db.execSQL(query);
    }

    /**
     * Updates the length field
     *
     */
    public void updateLength(int ID, int length){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COL7 +
                " = '" + length + "' WHERE " + COL1 + " = '" + ID + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting length to " + length);
        db.execSQL(query);
    }


}