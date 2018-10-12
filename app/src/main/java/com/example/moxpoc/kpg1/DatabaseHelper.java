package com.example.moxpoc.kpg1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "kpg.db";
    private static final int SCHEMA = 1;
    String TABLE;
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FNAME = "firstName";
    public static final String COLUMN_SNAME = "secondName";
    public static final String COLUMN_FNTARGET = "firstNameTarget";
    public static final String COLUMN_SNTARGET = "secondNameTarget";

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS" + TABLE);
        onCreate(db);
    }
}
