package com.example.moxpoc.kpg1.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int SCHEMA = 1;
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FNAME = "firstName";
    public static final String COLUMN_SNAME = "secondName";
    public static final String COLUMN_FNTARGET = "firstNameTarget";
    public static final String COLUMN_SNTARGET = "secondNameTarget";
    public static final String COLUMN_CURRENT = "current";
    public static final String COLUMN_NEXT = "next";
    public static final String COLUMN_SCORE = "score";

    public DatabaseHelper(Context context, String database){
        super(context,database,null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP DATABASE " + db);
        onCreate(db);
    }
}
