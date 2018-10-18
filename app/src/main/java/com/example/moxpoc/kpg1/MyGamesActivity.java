package com.example.moxpoc.kpg1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyGamesActivity extends AppCompatActivity {

    ListView myList;
    static String game = "game";
    List<String> tables;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_games);
        myList = findViewById(R.id.myGamesList);
        Bundle arguments  = getIntent().getExtras();
        String key = arguments.getString("act");
        if(key.equals(game)){
            tables = showGames();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tables);
            myList.setAdapter(adapter);

        }

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String table = adapter.getItem(position).toString();
                Intent intent = new Intent(MyGamesActivity.this, in_table_players.class);
                intent.putExtra("table", table);
                startActivity(intent);
            }
        });
    }

    public List<String> showGames(){
        DatabaseAdapter dbAdapter = new DatabaseAdapter(this);
        dbAdapter.open();
        return  dbAdapter.getTables();
    }
}
