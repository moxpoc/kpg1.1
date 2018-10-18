package com.example.moxpoc.kpg1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    static String table = "test0";

    DatabaseAdapter(Context context, String table){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
        this.table = table;
    }

    DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public List<String> getTables(){
        List<String> tables = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM sqlite_master WHERE type='table' AND name!='android_metadata' AND name!='sqlite_sequence'", null);
        if(cursor.moveToFirst()){
            do{
                tables.add(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
            }
            while (cursor.moveToNext());
        }
        return tables;
    }

    public void addGame(){
        database.execSQL("CREATE TABLE " + table + " (" + DatabaseHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseHelper.COLUMN_FNAME + " TEXT, "
                + DatabaseHelper.COLUMN_SNAME + " TEXT, "
                + DatabaseHelper.COLUMN_FNTARGET + " TEXT, "
                + DatabaseHelper.COLUMN_SNTARGET + " TEXT);");
    }
    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID,
        DatabaseHelper.COLUMN_FNAME,
        DatabaseHelper.COLUMN_SNAME,
        DatabaseHelper.COLUMN_FNTARGET,
        DatabaseHelper.COLUMN_SNTARGET};
        return database.query(table, columns,null, null, null, null, null);
    }

    public List<Player> getPlayers(){
        ArrayList<Player> players = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FNAME));
                String secondName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SNAME));
                players.add(new Player(id, firstName, secondName));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return players;
    }

    public List<Player> getTargets(){
        ArrayList<Player> targets = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String fnTarget = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FNTARGET));
                String snTarget = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SNTARGET));
                targets.add(new Player(id, fnTarget, snTarget));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return targets;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, table);
    }

    public Player getPlayer(long id){
        Player player = null;
        String query = ("SELECT * FROM " + table + " WHERE "
                + DatabaseHelper.COLUMN_ID + "=" + id);
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FNAME));
            String secondName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SNAME));
            player = new Player(id, firstName, secondName);
        }
        cursor.close();
        return player;
    }

    public Player getTarget(long id){
        Player target = null;
        String query = ("SELECT * FROM " + table + " WHERE "
                + DatabaseHelper.COLUMN_ID + "=" + id);
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            String fnTarget = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FNTARGET));
            String snTarget = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SNTARGET));
            target = new Player(id, fnTarget, snTarget);
        }
        cursor.close();
        return target;
    }

    public long insertPlayer(Player player){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_FNAME, player.getFirstName());
        contentValues.put(DatabaseHelper.COLUMN_SNAME, player.getSecondName());

        return database.insert(table,null, contentValues);
    }

    public long insertTarget(Player target, long position){

        String whereClause = DatabaseHelper.COLUMN_ID + "=" + String.valueOf(position);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_FNTARGET, target.getFirstName());
        contentValues.put(DatabaseHelper.COLUMN_SNTARGET, target.getSecondName());

        return database.update(table, contentValues, whereClause,null);
    }

    public long update(Player player){
        String whereClause = DatabaseHelper.COLUMN_ID + "=" + String.valueOf(player.getId());
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_FNAME, player.getFirstName());
        contentValues.put(DatabaseHelper.COLUMN_SNAME, player.getSecondName());
        return database.update(table, contentValues, whereClause,null);
    }
}
