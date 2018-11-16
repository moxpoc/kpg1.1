package com.example.moxpoc.kpg1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.moxpoc.kpg1.Adapters.DatabaseAdapter;
import com.example.moxpoc.kpg1.Adapters.TablesAdapter;
import com.example.moxpoc.kpg1.Helpers.SwipeHelper;

import java.util.ArrayList;

public class MyGamesActivity extends AppCompatActivity {

    RecyclerView myList;
    static String game = "game";
    ArrayList<String> tables;
    TablesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_games);
        myList = findViewById(R.id.myGamesList);
        Bundle arguments  = getIntent().getExtras();
        String key = arguments.getString("act");
        if(key.equals(game)){
            tables = showGames();
            adapter = new TablesAdapter(this, tables);
            myList.setAdapter(adapter);

            SwipeHelper callback = new SwipeHelper(adapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(myList);

        }
    }

    public ArrayList<String> showGames(){
        DatabaseAdapter dbAdapter = new DatabaseAdapter(this, "kpg.db");
        dbAdapter.open();
        return  dbAdapter.getTables();
    }
}
