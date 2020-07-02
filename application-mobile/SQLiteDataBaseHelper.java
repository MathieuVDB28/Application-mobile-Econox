package com.example.econox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "collecte.db";
    private static final String DATABASE_TABLE = "donnees_collecte";
    private static final int DATABASE_VERSION = 1;
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Volume";
    public static final String COL_3 = "Telephone";
    public static final String COL_4 = "Batterie";

    public SQLiteDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSQL = "create table donnees_collecte ("
                        + " id integer primary key autoincrement,"
                        + " volume integer not null,"
                        + "telephone integer not null,"
                        + "batterie integer not null"
                        + ")";
        db.execSQL(strSQL);
        Log.i("DATABASE", "onCreate invoked");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strSQL = ("drop table donnees_collecte");
        db.execSQL(strSQL);
        this.onCreate(db);
        Log.i("DATABASE", "onUpgrate invoked");
    }

    public boolean insertData( String volume, String telephone, String batterie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, volume);
        contentValues.put(COL_3, telephone);
        contentValues.put(COL_4, batterie);
        long result = db.insert(DATABASE_TABLE,null,contentValues);
        if(result== -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * FROM donnees_collecte", null);
        return result;
    }
}
