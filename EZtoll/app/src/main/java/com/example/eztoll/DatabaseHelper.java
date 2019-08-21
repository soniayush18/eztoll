package com.example.eztoll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Registration.db";
    public static final String TABLE_NAME = "registration_table";
    public static final String TABLE_NAME2 = "vehicle_registration_table";
    //public static final String TABLE_NAME3 = "vehicle_registration_table";
    //public static final String TABLE_NAME4 = "vehicle_registration_table";
    public static String DB_PATH = "/data/data/com.example.eztoll/databases/";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "PASSWORD";
    public static final String COL_5 = "VEHICLE_NUMBER";
    public static final String COL_6 = "MOBILE_NUMBER";
    public static final String COL_7 = "DRIVING_LICENSE_NUMBER";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,EMAIL TEXT,PASSWORD TEXT,VEHICLE_NUMBER TEXT,MOBILE_NUMBER TEXT,DRIVING_LICENSE_NUMBER TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        //db.execSQL("create table " + TABLE_NAME2 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,EMAIL TEXT,PASSWORD TEXT,VEHICLE_NUMBER TEXT,MOBILE_NUMBER TEXT,DRIVING_LICENSE_NUMBER TEXT)");
        onCreate(db);
    }

    public boolean insertData(String name,String email,String password,String vehicle_number,String mobile_number,String driving_license_number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,password);
        contentValues.put(COL_5,vehicle_number);
        contentValues.put(COL_6,mobile_number);
        contentValues.put(COL_7,driving_license_number);
        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }



//        public String[] getalldata() {
//            String Table_Name="registration_table";
//
//            String selectQuery = "SELECT  * FROM " + Table_Name;
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery(selectQuery, null);
//            String[] data = null;
//            if (cursor.moveToFirst()) {
//                do {
//                    // get  the  data into array,or class variable
//                } while (cursor.moveToNext());
//            }
//            db.close();
//            return data;
//        }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

}
