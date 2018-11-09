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

    DatabaseAdapter(Context context, String table, String dBase){
        dbHelper = new DatabaseHelper(context.getApplicationContext(),dBase);
        this.table = table;
    }

    DatabaseAdapter(Context context, String dBase){
        dbHelper = new DatabaseHelper(context.getApplicationContext(),dBase);
    }

  /*  DatabaseAdapter(Context context, boolean check){
        dbHelper = new DatabaseHelper(context.getApplicationContext(),"myScan.db");
    }

    DatabaseAdapter(Context context, String table, boolean check){
        dbHelper = new DatabaseHelper(context.getApplicationContext(),"myScan.db");
        this.table = table;
    }*/

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public ArrayList<String> getTables(){
        ArrayList<String> tables = new ArrayList<>();
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
        database.execSQL("CREATE TABLE IF NOT EXISTS " + table + " (" + DatabaseHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseHelper.COLUMN_FNAME + " TEXT, "
                + DatabaseHelper.COLUMN_SNAME + " TEXT, "
                + DatabaseHelper.COLUMN_FNTARGET + " TEXT, "
                + DatabaseHelper.COLUMN_SNTARGET + " TEXT);");
    }

    public void addScanGame(){
        database.execSQL("CREATE TABLE IF NOT EXISTS " + table + " (" + DatabaseHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DatabaseHelper.COLUMN_CURRENT+ " TEXT, "
                + DatabaseHelper.COLUMN_NEXT+ " TEXT, "
                + DatabaseHelper.COLUMN_SCORE + " LONG);");
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

    public ArrayList<Player> getTargets(){
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

    public JsonTargetHelper getJsonTarget(long id){
        JsonTargetHelper target = null;
        String query = ("SELECT * FROM " + table + " WHERE "
                + DatabaseHelper.COLUMN_ID + "=" + id);
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            String current = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CURRENT));
            String next = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NEXT));
            target = new JsonTargetHelper(current, next, table);
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

    public long insertScanTarget(String current, String next){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_CURRENT, current);
        contentValues.put(DatabaseHelper.COLUMN_NEXT, next);
        contentValues.put(DatabaseHelper.COLUMN_SCORE, 0);

        return database.insert(table,null, contentValues);
    }

    public String getNext(long id){
        String next = "Err";
        String query = ("SELECT * FROM " + table + " WHERE "
                + DatabaseHelper.COLUMN_ID + "=" + id);
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            next = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NEXT));
        }
        cursor.close();
        return next;
    }

    public long update(Player player){
        String whereClause = DatabaseHelper.COLUMN_ID + "=" + String.valueOf(player.getId());
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_FNAME, player.getFirstName());
        contentValues.put(DatabaseHelper.COLUMN_SNAME, player.getSecondName());
        return database.update(table, contentValues, whereClause,null);
    }

    public long updateScan(String current, String next){
        Long score = getScore();
        String whereClause = DatabaseHelper.COLUMN_NEXT + "=" + " '" + current + "'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_NEXT, next);
        contentValues.put(DatabaseHelper.COLUMN_SCORE, (score + 1));
        return database.update(table, contentValues, whereClause,null);
    }

    public long getScore(){
        Long next;
        String query = ("SELECT * FROM " + table + " WHERE "
                + DatabaseHelper.COLUMN_ID + "=" + 1);
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        next = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_SCORE));
        cursor.close();
        return next;
    }
}
