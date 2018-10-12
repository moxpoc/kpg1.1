package com.example.moxpoc.kpg1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class in_table_players extends AppCompatActivity {

    public static final String ACTION = "com.moxpoc.START_TARGET_ACTIVITY";

    ListView playerList;
    List<Player> players;
    ArrayAdapter adapter;
    DatabaseAdapter dbAdapter;
    String table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_table_players);

        Bundle arguments  = getIntent().getExtras();
        table = arguments.getString("table");
        dbAdapter = new DatabaseAdapter(this, table);
        dbAdapter.open();
        playerList = findViewById(R.id.myPlayersList);
        players = dbAdapter.getPlayers();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, players);

        playerList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        dbAdapter.close();

        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ACTION);
                intent.putExtra("position", position);
                intent.putExtra("table", table);
                startActivity(intent);
            }
        });
    }
}
