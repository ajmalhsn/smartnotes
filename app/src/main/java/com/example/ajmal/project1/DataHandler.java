package com.example.ajmal.project1;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajmal on 15-08-2016.
 */
public class DataHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static DataHandler sInstance;

    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "termManager.db";
    // Terms table name
    private static final String TABLE_TERMS = "terms";
    private static final String COL_ID = "_id";
    private static final String COL_TERM = "term";
    private static final String COL_DEFN = "defn";
    private static final String COL_SET = "studyset";




    public static synchronized void setInstance(DataHandler db1) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance=db1;
        }
     }
    public static synchronized DataHandler getsInstance(){

        return sInstance;
    }

    public DataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_TERMS + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TERM + " TEXT," + COL_DEFN + " TEXT," + COL_SET + " TEXT )";
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }
    

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMS);

        onCreate(sqLiteDatabase);
    }

    public void AddTerm(Definition defn) {
        Log.i("TAG", "add term initiated");
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            // Contact Name
            Log.i("TAG", String.valueOf(defn.get_id()));
            Log.i("TAG",String.valueOf(defn.getTerm()));
            Log.i("TAG",String.valueOf((defn.getDefn())));
            //values.put(COL_ID, defn.get_id());
            values.put(COL_TERM, String.valueOf(defn.getTerm()));
            values.put(COL_DEFN, String.valueOf(defn.getDefn()));
            values.put(COL_SET, String.valueOf(defn.getSet()));                                    // Contact Phone Number
            // Inserting Row
            sqLiteDatabase.insert(TABLE_TERMS,null,values);
            sqLiteDatabase.close(); // Closing database connection
        } catch (Exception e) {
            Log.i("TAG", e.toString());
        }
    }

    public void DeleteTerm() {
        SQLiteDatabase db = this.getWritableDatabase();
         db.delete(TABLE_TERMS,null,null);
        //db.execSQL("TRUNCATE table " + TABLE_TERMS);
        db.close();
    }
    public void deleteone(String termname){
        SQLiteDatabase db=this.getWritableDatabase();
        String whereClause = COL_TERM+"= ?";
        String[] whereArgs = new String[] {termname};
        db.delete(TABLE_TERMS,whereClause,whereArgs);

    }

    public Cursor getDefn(String termname) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] tableColumns = new String[] {COL_ID,COL_TERM,COL_DEFN,COL_SET};
        String whereClause = COL_TERM+"= ?";
        String[] whereArgs = new String[] {termname};
        //String orderBy = "column1";
        Cursor c = db.query(TABLE_TERMS, tableColumns, whereClause, whereArgs,null, null, null);

        return c;
    }
    public Cursor Defn_set(String set_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TERMS+" WHERE "+COL_SET+"="+"set_name",null);
        cursor.moveToFirst();
        if (cursor.moveToFirst())
            Log.i("TAG","cursor in list activity is not empty");

        return cursor;

    }
    public Cursor getDefnBySet(String setname){

      SQLiteDatabase db = this.getReadableDatabase();
        String[] tableColumns = new String[] {COL_ID,COL_TERM,COL_DEFN,COL_SET};
        String whereClause = COL_SET+"= ?";
        String[] whereArgs = new String[] {setname};
        //String orderBy = "column1";
        Cursor c = db.query(TABLE_TERMS, tableColumns, whereClause, whereArgs,null, null, null);

// since we have a named column we can do

// since we have a named column we can do
//        Cursor cursor = db.query("SELECT * FROM "+TABLE_TERMS+" WHERE "="+setname.toLowerCase(), null);
        //cursor.moveToFirst();

        return c;
    }

    public Cursor getAllDefn() {
        List<Definition> defnList = new ArrayList<Definition>();
        // Select All Query
        Definition defn = new Definition();
        String selectQuery = "SELECT * FROM " + TABLE_TERMS;
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Log.i("TAG", String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_ID))));
                    Log.i("TAG", cursor.getString(cursor.getColumnIndex(COL_TERM)) + " pls");
                    Log.i("TAG", cursor.getString(cursor.getColumnIndex(COL_DEFN)) + " pls");
                    Log.i("TAG", cursor.getString(cursor.getColumnIndex(COL_SET)) + " pls");
                    /*defn.set_id(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                    defn.setTerm(cursor.getString(cursor.getColumnIndex(COL_TERM)));
                    defn.setDefn(cursor.getString(cursor.getColumnIndex(COL_DEFN)));
                    defn.setSet(cursor.getString(cursor.getColumnIndex(COL_SET)));
                    // Adding definition to list
                    defnList.add(defn);*/
                   // Log.i("TAG", cursor.getString(cursor.getColumnIndex(COL_TERM)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.i("TAG", e.toString() + "  smt ");
        }

        // return definition list
        return cursor;
    }
}





