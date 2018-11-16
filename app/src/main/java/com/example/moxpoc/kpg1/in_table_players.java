package com.example.moxpoc.kpg1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.moxpoc.kpg1.Adapters.DatabaseAdapter;
import com.example.moxpoc.kpg1.Adapters.PlayerAdapter;

import java.util.List;

public class in_table_players extends AppCompatActivity {

    public static final String ACTION = "com.moxpoc.action.START_TARGET_ACTIVITY";

    RecyclerView playerList;
    List<Player> players;
    PlayerAdapter adapter;
    DatabaseAdapter dbAdapter;
    String table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_table_players);

        Bundle arguments  = getIntent().getExtras();
        table = arguments.getString("table");
        dbAdapter = new DatabaseAdapter(this, table, "kpg.db");
        dbAdapter.open();
        playerList = findViewById(R.id.myPlayersList);
        players = dbAdapter.getPlayers();

        adapter = new PlayerAdapter(this, players, table);

        playerList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        dbAdapter.close();
/*
        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ACTION);
                intent.putExtra("position", position);
                intent.putExtra("table", table);
                startActivity(intent);
            }
        });*/
    }
}
